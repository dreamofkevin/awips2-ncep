package gov.noaa.nws.ncep.viz.common.area;

import gov.noaa.nws.ncep.edex.util.McidasCRSBuilder;

import org.geotools.coverage.grid.GeneralGridEnvelope;
import org.geotools.coverage.grid.GeneralGridGeometry;
import org.geotools.coverage.grid.GridEnvelope2D;
import org.geotools.geometry.GeneralEnvelope;
import org.geotools.referencing.CRS;
import org.opengis.coverage.grid.GridEnvelope;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.raytheon.uf.common.geospatial.adapter.GridGeometryAdapter;
import com.raytheon.uf.common.serialization.IDeserializationContext;
import com.raytheon.uf.common.serialization.ISerializationContext;
import com.raytheon.uf.common.serialization.SerializationException;
import com.raytheon.uf.common.serialization.adapters.GridGeometrySerialized;

/**
 * Serialization adapter for grid geometries unique to NCEP.
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY 
 * Date         Ticket#     Engineer    Description
 * ------------ ----------  ----------- --------------------------
 * 11/2013      1066        G. Hull     Created.
 * 03/2014      TTR957      B. Yin      Handle native satellite navigation.
 * Nov 05, 2015 10436       njensen     Updated import, cleaned up
 * 
 * </pre>
 * 
 * @author ghull
 * @version 1
 */
public class NcGridGeometryAdapter extends GridGeometryAdapter {

    @Override
    public GridGeometrySerialized marshal(GeneralGridGeometry v)
            throws Exception {
        return super.marshal(v);
    }

    @Override
    public GeneralGridGeometry unmarshal(GridGeometrySerialized v)
            throws Exception {
        CoordinateReferenceSystem crs = null;
        try {
            crs = CRS.parseWKT(v.CRS);
        } catch (Exception e) {
            // TODO: there's probably a better way to do this
            crs = McidasCRSBuilder.constructCRSfromWKT(v.CRS);
        }

        if (crs == null) {
            throw new Exception("Unable to parse CRS from WKS: " + v.CRS);
        }

        GeneralEnvelope env = new GeneralEnvelope(crs);
        env.setRange(0, v.envelopeMinX, v.envelopeMaxX);
        env.setRange(1, v.envelopeMinY, v.envelopeMaxY);

        GeneralGridGeometry ggg = null;
        final int gridX = v.rangeX[1];
        final int gridY = v.rangeY[1];

        if (v.envelopeMinZ == null && v.envelopeMaxZ == null
                && v.rangeZ == null) {
            GridEnvelope2D ge = new GridEnvelope2D(v.rangeX[0], v.rangeY[0],
                    v.rangeX[1] - v.rangeX[0] + 1, v.rangeY[1] - v.rangeY[0]
                            + 1);
            ggg = new GeneralGridGeometry(ge, env);
        } else {
            final int gridZ = v.rangeZ[1];

            env.setRange(2, v.envelopeMinZ, v.envelopeMaxZ);
            GeneralGridEnvelope gge = new GeneralGridEnvelope(new int[] {
                    gridX / -2, gridY / -2, gridZ / -2 }, new int[] {
                    gridX / 2, gridY / 2, gridZ / 2 }, false);
            ggg = new GeneralGridGeometry(gge, env);
        }

        return ggg;
    }

    @Override
    public void serialize(ISerializationContext serializer,
            GeneralGridGeometry object) throws SerializationException {
        int numDims = object.getDimension();
        GridEnvelope range = object.getGridRange();
        Envelope env = object.getEnvelope();

        serializer.writeString(object.getCoordinateReferenceSystem().toWKT());
        serializer.writeI32(numDims);
        for (int i = 0; i < numDims; ++i) {
            serializer.writeI32(range.getLow(i));
            serializer.writeI32(range.getHigh(i));
            serializer.writeDouble(env.getMinimum(i));
            serializer.writeDouble(env.getMaximum(i));
        }
    }

    @Override
    public GeneralGridGeometry deserialize(IDeserializationContext deserializer)
            throws SerializationException {
        try {
            CoordinateReferenceSystem crs = CRS.parseWKT(deserializer
                    .readString());
            int numDims = deserializer.readI32();
            GeneralEnvelope env = new GeneralEnvelope(crs);
            int[] lowRange = new int[numDims];
            int[] highRange = new int[numDims];
            for (int i = 0; i < numDims; ++i) {
                lowRange[i] = deserializer.readI32();
                highRange[i] = deserializer.readI32();
                env.setRange(i, deserializer.readDouble(),
                        deserializer.readDouble());
            }

            return new GeneralGridGeometry(new GeneralGridEnvelope(lowRange,
                    highRange, false), env);
        } catch (FactoryException e) {
            throw new SerializationException(
                    "Error deserializing GeneralGridGeometry, could not read CRS",
                    e);
        }
    }
}
