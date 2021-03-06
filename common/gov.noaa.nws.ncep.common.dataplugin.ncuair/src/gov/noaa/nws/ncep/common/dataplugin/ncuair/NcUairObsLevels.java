/**
 * NcUairObsLevels
 * 
 * This java class defines the getters and setters for the upper air
 * parameters in TTAA of an upper air sounding.
 * 
 * HISTORY
 *
 * Date         Ticket#         Engineer    Description
 * ------------ ----------      ----------- --------------------------
 * 03/2010      210				L. Lin  	Initial coding
 * 04/2011		210				T. Lee		Removed table entity for H5
 * 09/2011      457             S. Gurung   Renamed H5 to Nc and h5 to nc
 * 09/2011                   	Chin Chen   support batch decoding methods for better performance and
 * 											remove xml serialization as well
 * </pre>
 *
 * This code has been developed by the SIB for use in the AWIPS2 system. 
 * @author L. Lin
 * @version 1.0
 */

package gov.noaa.nws.ncep.common.dataplugin.ncuair;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import com.raytheon.uf.common.serialization.ISerializableObject;
import com.raytheon.uf.common.serialization.annotations.DynamicSerialize;
import com.raytheon.uf.common.serialization.annotations.DynamicSerializeElement;
import gov.noaa.nws.ncep.common.tools.IDecoderConstantsN;

@XmlAccessorType(XmlAccessType.NONE)
@DynamicSerialize
public class NcUairObsLevels implements ISerializableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 100852718286926645L;

	// Observation pressure in Pascals
	@DynamicSerializeElement
	private float pres;

	// Observation geopotential height in meters.
	
	@DynamicSerializeElement
	private float hght;

	// Observation dry air temperature in degrees Kelvin.
	
	@DynamicSerializeElement
	private float temp;

	// Observation dewpoint temperature in degrees Kelvin.
	
	@DynamicSerializeElement
	private float dwpt;

	// Observation wind direction in angular degrees.
	
	@DynamicSerializeElement
	private float drct;

	// Observation wind speed in meters per second.
	// Decimal(5,2)
	
	@DynamicSerializeElement
	private float Sped;

	/**
	 * No-Arg Constructor
	 */
	public NcUairObsLevels() {
		Sped = IDecoderConstantsN.UAIR_FLOAT_MISSING;
		drct = IDecoderConstantsN.UAIR_FLOAT_MISSING;
		dwpt = IDecoderConstantsN.UAIR_FLOAT_MISSING;
		temp = IDecoderConstantsN.UAIR_FLOAT_MISSING;
		hght = IDecoderConstantsN.UAIR_FLOAT_MISSING;
		pres = IDecoderConstantsN.UAIR_FLOAT_MISSING;
	}

	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	/**
     * Get the level pressure in Pascals.
     * 
     * @return the pres
     */	
	public float getPres() {
		return pres;
	}

	/**
     * Set the level pressure in Pascals.
     * 
     * @param pres
     *            the pres to set
     */
	public void setPres(float pres) {
		this.pres = pres;
	}

	/**
     * Get the level geopotential height in meters.
     * 
     * @return the hght
     */
	public float getHght() {
		return hght;
	}

	/**
     * Set the level geopotential height in meters.
     * 
     * @param hght
     *            the hght to set
     */
	public void setHght(float hght) {
		this.hght = hght;
	}

	/**
     * Get the level dry air temperature in degrees Kelvin.
     * 
     * @return the temp
     */
	public float getTemp() {
		return temp;
	}

	 /**
     * Set the level dry air temperature in degrees Kelvin.
     * 
     * @param temp
     *            the temp to set
     */
	public void setTemp(float temp) {
		this.temp = temp;
	}

	/**
     * Get the level dewpoint temperature in degrees Kelvin.
     * 
     * @return the dwpt
     */
	public float getDwpt() {
		return dwpt;
	}

	/**
     * Set the level dewpoint temperature in degrees Kelvin.
     * 
     * @param dwpt
     *            the dwpt to set
     */
	public void setDwpt(float dwpt) {
		this.dwpt = dwpt;
	}

	/**
     * Get the level wind direction in angular degrees.
     * 
     * @return the drct
     */
	public float getDrct() {
		return drct;
	}

	/**
     * Set the level wind direction in angular degrees.
     * 
     * @param drct
     *            the drct to set
     */
	public void setDrct(float drct) {
		this.drct = drct;
	}

	/**
     * Get the level wind speed in meters per second.
     * 
     * @return the Sped
     */
	public float getSped() {
		return Sped;
	}

	/**
     * Set the level wind speed in meters per second.
     * 
     * @param Sped
     *            the Sped to set
     */
	public void setSped(float Sped) {
		this.Sped = Sped;
	}

}
