/*
 * gov.noaa.nws.ncep.ui.pgen.tools.PgenAddPoint
 * 
 * 1 June 2010
 *
 * This code has been developed by the NCEP/SIB for use in the AWIPS2 system.
 */

package gov.noaa.nws.ncep.ui.pgen.tools;

import com.raytheon.uf.viz.core.rsc.IInputHandler;

/**
 * Implements a modal map tool for PGEN add point function.
 * 
 * <pre>
 * SOFTWARE HISTORY
 * Date       	Ticket#		Engineer	Description
 * ------------	----------	-----------	--------------------------
 * 06/10		#282			S. Gilbert  Initial Creation.
 * 05/11		#808		J. Wu		Update Gfa vor text
 * 05/12		#610		J. Wu   	Add warning when GFA FROM lines > 3
 * 03/13		#927		B. Yin		Moved out the mouse handler inner class. 
 *
 * </pre>
 * 
 * @author	B. Yin
 */

//public class PgenAddPoint extends PgenSelectingTool {
public class PgenAddPoint extends AbstractPgenTool {

    /**
     * Input handler for mouse events.
     */ 
    protected PgenAddPointHandler addPtHandler = null;

    
    public PgenAddPoint(){
    	
    	super();
    	
    }
    
    /**
     * Returns the current mouse handler.
     * @return
     */   
    public IInputHandler getMouseHandler() {	

    	if ( this.addPtHandler == null || this.mapEditor != ((PgenAddPointHandler)addPtHandler).getMapEditor() 
    			|| this.drawingLayer != ((PgenAddPointHandler)addPtHandler).getPgenrsc() ) {     	        	
    		this.addPtHandler = new PgenAddPointHandler(this);

    	}

        return this.addPtHandler;
        
    }

}
	
