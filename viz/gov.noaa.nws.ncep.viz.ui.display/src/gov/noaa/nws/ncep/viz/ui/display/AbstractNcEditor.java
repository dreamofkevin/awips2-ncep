package gov.noaa.nws.ncep.viz.ui.display;

import gov.noaa.nws.ncep.viz.common.display.NcDisplayType;
import gov.noaa.nws.ncep.viz.common.preferences.NcepGeneralPreferencesPage;
import gov.noaa.nws.ncep.viz.common.ui.NmapCommon;

import java.util.Collections;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.CommandException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

import com.raytheon.uf.viz.core.drawables.IRenderableDisplay;
import com.raytheon.viz.ui.editor.EditorInput;
import com.raytheon.viz.ui.editor.VizMultiPaneEditor;

//import gov.noaa.nws.ncep.viz.resources.time_match;

/**
 * Defines the GL-based map editor
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 *      
 * Date            Ticket#     Engineer    Description
 * ------------    ----------  ----------- --------------------------
 * 7/1/06                      chammack    Initial Creation.
 * 12/3/07         461         bphillip    Added Time Display to Status bar
 * Oct 21, 2008     #1450      randerso    Moved multipanel support down into GLMapEditor
 * 04/09/09        2228        rjpeter     Removed recursive listener adding.
 * Aug 31, 2009    2920        rjpeter     Moved MapContext Activation/Deactivation to include when window loses focus.
 * Dec 16, 2009                ghull       add PaneListener
 * Feb 18, 2010    #226        ghull       add PaneLayout
 * April 1, 2010  #238,#239    archana     Modified the method refreshGuiElements()
 *                                         to update the contribution items
 *                                         in the status bar.
 *                                         Added methods addFrameChangedListener(IFrameChangedListener)
 *                                         and removeFrameChangedListener(IFrameChangedListener)
 * 05/27/10                    ghull       get/setEditorInput()
 * 10/21/10		  #314		   Q. Zhou     added get/set for Hide/Show status
 * 10/29/10       #307         ghull       added get/setAutoUpdate()
 * 11/04/10       migration    ghull       override isDirty() and return false.
 * 14/01/11      #289         archana     moved the logic to activate contexts from NCMapEditor
 *                                                    to the NCPerspectiveManager (plugin.xml)
 *  02/10/2011                 Chin Chen   handle multiple editor copies dispose issue         
 *  
 * 03/07/11    migration       ghull       extend from AbstractMultiPaneEditor ; remove displayPaneMap  
 * 04/19/11      #434          ghull       on dispose(), don't let the user close the last editor.              
 * 04/26/11       #416         M. Gao      fix a potential bug in on dealing with parsing String to Int in the method setDisplayName(...) method
 * 06/22/11    migration       ghull       add back @Override of isDirty to prevent dirty editors.
 * 07/15/11                    C Chen      add implements AbstractNcEditor. fix looping buttons not coordinated issue
 * 11/11/11                    ghull       remove frameChangeListener from all descriptors
 * 12/02/11       #571         ghull       check for activePage in refreshGUIElements and in dispose()
 * 07/12/12       ###          ghull       call refreshGUIElements on paneChange. Select all panes at once instead of selecting/deselecting.
 * 07/31/12       #631         ghull       check promptOnClose preference to set isDirty.
 * 12/12/12       #630         ghull       refreshGUIElementsForSelectedPanes()
 * 01/18/12       #972         ghull       changed NCMapEditor to AbstractNcEditor
 * 01/20/12       #972         ghull       moved methods that simply delegated to NcPaneManager and 
 *                                         call them through NcEditorUtils.
 * 05/12/2016     5645         bsteffen    Eclipse 4: Use HandlerService for command execution.
 * 06/03/2016     5678         bsteffen    Eclipse 4: Do not open new editor when closing NCP.
 * 
 * 
 * </pre>
 * 
 * @author chammack
 * 
 */
public abstract class AbstractNcEditor extends VizMultiPaneEditor {

	
//	public abstract void refreshGUIElements();	
//	public abstract NcDisplayType getNcDisplayType(); 
	
//	@Override
//	public void init(IEditorSite site, IEditorInput input)
//	throws PartInitException {
//		super.init(site, input);
//
//		EditorInput edInput = (EditorInput)input;
//		
//		AbstractNcPaneManager pm = (AbstractNcPaneManager)edInput.getPaneManager();
//
//		// 
//		synchronized( this ) {
//			pm.createDisplayName( edInput.getName() );			
//		}
//		
//		edInput.setName( pm.getDisplayName().toString() );
//	}
	
	// Nc Editors have to have an AbstractNcPaneManager
	//
    @Override
    protected void validateEditorInput( EditorInput input )
            throws PartInitException {
        super.validateEditorInput(input);
        
        // TODO : implement this to validate the 
        if( input.getPaneManager() == null || 
        	!(input.getPaneManager() instanceof AbstractNcPaneManager) ) {
        	
            throw new PartInitException("Pane manager for Editor is null or doesn't extend AbstractNcPaneManager");
        }
        AbstractNcPaneManager ncpm = (AbstractNcPaneManager)input.getPaneManager();
        
        NcDisplayType ncDispType = ncpm.getDisplayType();
//// validate the renderable display is of the correct type.        
//        String rendDispType = ncDispType.getRederableDisplay( );   
//        
//        for( IRenderableDisplay display : input.getRenderableDisplays()) {            
//        	if( !display.getClass().getName().equals( rendDispType ) ) {
//                throw new PartInitException("Expected renderable display of type: "
//                        + rendDispType + ", instead of: "
//                        + display.getClass() );
//            } 
//        }
        
        setTabTitle( editorInput.getName() );
    }


	// move these to AbstractNcEditor
    // override to not include # panels in title
    @Override
    public void setTabTitle( String title ) {
        editorInput.setName( title );
        setPartName( title );
    }

	// don't do anything but need to override VizMultiPane's method 
	// which adds the panel # to the title.
    @Override
    protected void updateTitle() {
    }

    //
    // // Note: this will not get called unless the editor is dirty and
    // // Also Note that this will bypass raytheon's disableClose so that if we
    // // implement
    // // isDirty and still want to allow some of our Editors to not be closed
    // // (nsharp?), then
    // // we will need to override disableClose.
    @Override
    public int promptToSaveOnClose() {
    	return super.promptToSaveOnClose();
    }
    
    // @Override
    // public int promptToSaveOnClose() {
    // if (PlatformUI.getWorkbench().isClosing()) {
    // return ISaveablePart2.NO;
    // }
    // Shell shell = getSite().getShell();
    //
    // boolean close = MessageDialog.openQuestion(shell, "Close Editor?",
    // "Are you sure you want to close this Display?");
    // return close ? ISaveablePart2.NO : ISaveablePart2.CANCEL;
    // }
    //
    // // We could implement an isDirty method in AbstractNatlCntrsResource
    // // except currently there can't be a dependency from the display project
    // to
    // // the
    // // resources project. If we did this then the pgen resource (others?)
    // could
    // // implement isDirty to allow the user to cancel an editor close (above).
    // //
     @Override
     public boolean isDirty() {
    	 
//    	 for( IDisplayPane pane : getDisplayPanes() ) {
//    		 IRenderableDisplay display = pane.getRenderableDisplay();
//    		 if (display != null) {
//    			 for (ResourcePair rp : display.getDescriptor()
//    					 .getResourceList()) {
//    				 if( rp.getResource() instanceof AbstractNatlCntrsResource ) {
//    					 if( ((AbstractNatlCntrsResource)rp.getResource()).isDirty() ) {
//    						 return true;
//    					 }
//    				 }
//    				 // raytheons test...
//    				 ResourceProperties props = rp.getProperties();
//    				 if (!props.isSystemResource() && !props.isMapLayer()) {
//    					 return true;
//    				 }
//    			 }
//    		 }
//    	 }
 		return NmapCommon.getNcepPreferenceStore().getBoolean( NcepGeneralPreferencesPage.PromptOnDisplayClose );
     }
     
     // Override AbstractEditor implementation which will loop thru the
     // renderable displays and call addPane.
     // Since we already know the paneLayout, we can create the gridLayout
     // here.
//     @Override
//     public void createPartControl(Composite parent) {
//
//         editorInput.getPaneManager().initializeComponents(this, parent);
//
//         for (IRenderableDisplay display : displaysToLoad) {
//             addPane(display);
//
//             if( getNumberofPanes() == 1) {
//            	 EditorInput edin = (EditorInput)getEditorInput();
//            	
//            	if( edin.getPaneManager() instanceof NCPaneManager ) {
//            		((NCPaneManager)edin.getPaneManager()).selectPane( getDisplayPanes()[0] );
//            	}
//             }
//         }
//
//         contributePerspectiveActions();
//     }


     //
 	@Override
 	public void dispose() { 		
 		super.dispose();

        final IWorkbenchPage page = this.getSite().getPage();

        IWorkbenchWindow window = page.getWorkbenchWindow();
        
        if (window.getWorkbench().isClosing()) {
            return;
        }

 		if( page.getEditorReferences().length == 0 ) {
            
            ICommandService commandService = window
                    .getService(ICommandService.class);
            Command cmd = commandService
                    .getCommand("gov.noaa.nws.ncep.viz.ui.newMapEditor");
 			if( cmd == null ) {
 				System.out.println("Can't find Command to create a new Display");
 				return;
 			}

            Map<String, Object> parameters = Collections
                    .<String, Object> singletonMap("promptForName", "false");

            final ParameterizedCommand pcmd = ParameterizedCommand
                    .generateCommand(cmd, parameters);
            final IHandlerService handlerService = window
                    .getService(IHandlerService.class);

            /*
             * When the command is run later, double check the current
             * perspective and if it has changed than assume the editor was
             * closed as part of a perspective switch and do not run the
             * command.
             */
            final IPerspectiveDescriptor perspective = page.getPerspective();

            /*
             * Dispose is called while in the process of removing the editor.
             * Adding a new editor while still in the process of removing causes
             * errors in eclipse. Run the new editor command async so it will
             * occur after the removal is done.
             */
            window.getShell().getDisplay().asyncExec(new Runnable() {

                @Override
                public void run() {
                    if (page.getPerspective() != perspective) {
                        return;
                    }
                    try {
                        handlerService.executeCommand(pcmd, null);
                    } catch (CommandException e) {
                        e.printStackTrace();
                    }
                }
            });

 		}
 	}
    
    public String getDefaultTool() {
    	AbstractNcPaneManager pm = getNcPaneManager();
        return (pm == null ? null : pm.getDefaultTool() );
    }

    public AbstractNcPaneManager getNcPaneManager() {
    	IEditorInput edin = getEditorInput();
    	
    	if( edin instanceof EditorInput &&
    		((EditorInput)edin).getPaneManager() instanceof AbstractNcPaneManager ) {
    		
            return (AbstractNcPaneManager)((EditorInput)edin).getPaneManager();    		
    	}
    	else {
    		return null;
    	}
    }
}
