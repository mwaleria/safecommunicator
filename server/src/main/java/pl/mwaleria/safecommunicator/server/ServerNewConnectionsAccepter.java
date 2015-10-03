package pl.mwaleria.safecommunicator.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mwaleria
 */
public class ServerNewConnectionsAccepter implements  Runnable{

    private ServerSocket serverSocket;
    
    private ServerManager serverManager;

    public ServerNewConnectionsAccepter(ServerSocket serverSocket,ServerManager serverManager) {
        this.serverSocket = serverSocket;
        this.serverManager = serverManager;
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                serverManager.handleIncomingSocket(socket);
            } catch (IOException ex) {
                Logger.getLogger(ServerNewConnectionsAccepter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
