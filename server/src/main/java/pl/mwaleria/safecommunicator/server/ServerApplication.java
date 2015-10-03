package pl.mwaleria.safecommunicator.server;

import com.sun.java.swing.SwingUtilities3;
import javax.swing.SwingUtilities;
import pl.mwaleria.safecommunicator.server.gui.ServerForm;

/**
 *
 * @author mwaleria
 */
public class ServerApplication {

    
    public static void main(String[] args) {
        ServerManager serverManager = new ServerManager();
        ServerForm serverForm = new ServerForm();
        serverForm.setServerManager(serverManager);
        serverManager.setServerForm(serverForm);
        serverForm.setVisible(true);
    }
    
}
