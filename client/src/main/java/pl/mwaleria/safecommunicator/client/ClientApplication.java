package pl.mwaleria.safecommunicator.client;

import pl.mwaleria.safecommunicator.client.gui.LoginFrame;

/**
 *
 * @author mwaleria
 */
public class ClientApplication implements Runnable{

    private ClientManager clientManager;
    
    public static void main(String[] args) {
        new ClientApplication().run();
    }

    @Override
    public void run() {
        clientManager = new ClientManager();
        LoginFrame loginFrame = new LoginFrame(clientManager);
        loginFrame.setVisible(true);
    }
    
    
}
