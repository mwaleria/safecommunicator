package pl.mwaleria.safecommunicator.server;

import pl.mwaleria.safecommunicator.server.gui.ServerForm;
import pl.mwaleria.safecommunicator.server.model.ServerUser;

/**
 *
 * @author mwaleria
 */
public class GuiServerManager {

    private final ServerManager serverManager;
    
    private final ServerForm serverForm;

    public GuiServerManager(ServerManager serverManager, ServerForm serverForm) {
        this.serverManager = serverManager;
        this.serverForm = serverForm;
    }
    
    
    
    public void guiChangeServerStatus(String status) {
        serverForm.changeServerStatus(status);
    }
    
    public void addServerUser(ServerUser serverUser) {
        
    }
    
     
}
