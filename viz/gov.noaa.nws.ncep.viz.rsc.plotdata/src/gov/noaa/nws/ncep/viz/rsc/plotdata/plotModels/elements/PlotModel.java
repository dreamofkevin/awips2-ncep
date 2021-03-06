package gov.noaa.nws.ncep.viz.rsc.plotdata.plotModels.elements;

import gov.noaa.nws.ncep.viz.localization.NcPathManager.NcPathConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.raytheon.uf.common.localization.LocalizationFile;

/**
 * 
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * 
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * 10/09  		172    	   	M. Li       Initial Creation
 * 
 * 08/21/11     450         G. Hull     add LocalizationFile                 
 * 05/02/12     778         Q. Zhou     Changed symbol size form int to double       
 * 10/18/2012   431         S. Gurung   Added support for ConditionalParameter and ConditionalColorBar
 * 08/14/2015 R7757         B. Hebbard  Cleanup only
 * 11/17/2015 R9579         B. Hebbard  Add marker type and symbol width fields to PlotModelElement
 * 
 * </pre>
 * 
 * @author mli
 * @version 1
 */

@XmlRootElement(name = "plotModel")
@XmlAccessorType(XmlAccessType.NONE)
public class PlotModel {

    @XmlElement(name = "PlotModelElement", required = true)
    protected List<PlotModelElement> plotModelElements;

    @XmlAttribute
    protected String name;

    @XmlAttribute
    protected String plugin; // the plugin or db table name

    @XmlAttribute
    protected String svgTemplate;

    // This is only set if created from a saved file as opposed
    // to being an 'edited' attribute of a PlotResource.
    protected LocalizationFile lFile;

    /**
     * Gets the value of the plotModelElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the plotModelElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getPlotModelElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlotModelElement }
     * 
     * 
     */
    public List<PlotModelElement> getAllPlotModelElements() {
        if (plotModelElements == null) {
            plotModelElements = new ArrayList<PlotModelElement>();
        }
        return this.plotModelElements;
    }

    public ArrayList<String> getPlotParamNames(boolean includeWindParam) {
        ArrayList<String> retList = new ArrayList<String>();
        for (PlotModelElement pme : getAllPlotModelElements()) {
            if (pme.paramName != null && !pme.paramName.isEmpty()) {
                if (includeWindParam || !pme.getPosition().equals("WD")) {
                    retList.add(pme.paramName);
                }
            }
        }
        return retList;
    }

    public String createLocalizationFilename() {
        return NcPathConstants.PLOT_MODELS_DIR + File.separator + plugin
                + File.separator + name + ".xml";
    }

    public String getName() {
        return name;
    }

    public LocalizationFile getLocalizationFile() {
        return lFile;
    }

    public void setLocalizationFile(LocalizationFile lFile) {
        this.lFile = lFile;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String value) {
        this.plugin = value;
    }

    public String getSvgTemplate() {
        return svgTemplate;
    }

    public void setSvgTemplate(String value) {
        this.svgTemplate = value;
    }

    public PlotModelElement getPlotModelElement(String position) {
        for (PlotModelElement e : plotModelElements) {
            if (e.getPosition().equalsIgnoreCase(position)) {
                return e;
            }
        }
        return null;
    }

    public PlotModelElement getSkyCoverageElement() {
        for (PlotModelElement e : plotModelElements) {
            if (e.getPosition().equalsIgnoreCase("SC")) {
                return e;
            }
        }
        return null;
    }

    public PlotModelElement getWindBarbElement() {
        for (PlotModelElement e : plotModelElements) {
            if (e.getPosition().equalsIgnoreCase("WD")) {
                return e;
            }
        }
        return null;
    }

    public boolean removePlotModelElement(PlotModelElement pme) {
        for (PlotModelElement e : plotModelElements) {
            if (e.getPosition().equalsIgnoreCase(pme.getPosition())) {
                plotModelElements.remove(e);
                return true;
            }
        }
        return false;
    }

    public void putPlotModelElement(PlotModelElement pme) {
        int i = 0;
        for (PlotModelElement e : plotModelElements) {
            if (e.getPosition().equalsIgnoreCase(pme.getPosition())) {
                plotModelElements.set(i, pme);
                return;
            }
            i++;
        }
        plotModelElements.add(pme);
    }

    public PlotModel() {
    }

    public PlotModel(PlotModel pm) {
        plotModelElements = new ArrayList<PlotModelElement>();
        name = new String(pm.name);
        plugin = new String(pm.plugin);
        svgTemplate = new String(pm.svgTemplate);

        for (PlotModelElement pme : pm.getAllPlotModelElements()) {
            PlotModelElement newPlotModelElement = new PlotModelElement();
            newPlotModelElement.setParamName(pme.getParamName());
            newPlotModelElement.setPosition(pme.getPosition());
            if (pme.getSymbolSize() != null) {
                newPlotModelElement.setSymbolSize(pme.getSymbolSize());
            }
            if (pme.getSymbolWidth() != null) {
                newPlotModelElement.setSymbolWidth(pme.getSymbolWidth());
            }
            if (pme.getTextFont() != null) {
                newPlotModelElement.setTextFont(pme.getTextFont());
            }
            if (pme.getTextSize() != null) {
                newPlotModelElement.setTextSize(pme.getTextSize());
            }
            if (pme.getTextStyle() != null) {
                newPlotModelElement.setTextStyle(pme.getTextStyle());
            }
            if (pme.getColor() != null) {
                Color newColor = new Color();
                newColor.setRed(pme.getColor().getRed());
                newColor.setGreen(pme.getColor().getGreen());
                newColor.setBlue(pme.getColor().getBlue());
                newPlotModelElement.setColor(newColor);
            }
            if (pme.getConditionalParameter() != null) {
                newPlotModelElement.setConditionalParameter(pme
                        .getConditionalParameter());
            }
            if (pme.getConditionalColorBar() != null) {
                newPlotModelElement.setConditionalColorBar(pme
                        .getConditionalColorBar());
            }
            if (pme.getMarkerType() != null) {
                newPlotModelElement.setMarkerType(pme.getMarkerType());
            }

            plotModelElements.add(newPlotModelElement);
        }

        lFile = pm.lFile;
    }

    public boolean hasAdvancedSettings() {

        for (PlotModelElement pme : plotModelElements) {
            if (pme.hasAdvancedSettings())
                return true;
        }

        return false;
    }
}
