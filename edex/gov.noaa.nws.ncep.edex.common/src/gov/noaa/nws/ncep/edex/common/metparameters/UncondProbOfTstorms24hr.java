/**
 * 
 */
package gov.noaa.nws.ncep.edex.common.metparameters;


import javax.measure.quantity.Dimensionless;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.raytheon.uf.common.serialization.ISerializableObject;
import com.raytheon.uf.common.serialization.annotations.DynamicSerialize;
import com.raytheon.uf.common.units.UnitAdapter;

/**
 * Maps to the parameter TS24
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@DynamicSerialize

 public class UncondProbOfTstorms24hr extends AbstractMetParameter implements
		Dimensionless, ISerializableObject {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -941540151994722478L;

	public UncondProbOfTstorms24hr() throws Exception {
		 super( new UnitAdapter().marshal(UNIT) );
	}
	 
 }