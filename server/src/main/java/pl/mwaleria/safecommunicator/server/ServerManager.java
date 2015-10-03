package pl.mwaleria.safecommunicator.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import org.joda.time.LocalDateTime;
import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.cipher.SafeCommunicatorKeyGenerator;
import pl.mwaleria.safecommunicator.core.net.EventDispatcher;
import pl.mwaleria.safecommunicator.server.gui.ServerForm;
import pl.mwaleria.safecommunicator.server.model.ServerUser;

/**
 *
 * @author mwaleria
 */
public class ServerManager {

    private boolean serverOn = false;

    private ServerSocket serverSocket;

    private AtomicLong userCounter = new AtomicLong(12L);

    private EventDispatcher<ServerRequest> dispatcher;

    private ConcurrentHashMap<Long, UserCommunicationChanel> allUsers = new ConcurrentHashMap<>();
    
    private ServerForm serverForm;
    
    private KeyPair serverKeyPair;

    public void setServerForm(ServerForm serverForm) {
        this.serverForm = serverForm;
    }

    public boolean startServer(int port, String randomText) {
        SafeCommunicatorKeyGenerator keyGen = new SafeCommunicatorKeyGenerator();
        serverKeyPair = keyGen.generateKeyPair(randomText);
        try {
            serverSocket = new ServerSocket(port);
            new Thread(new ServerNewConnectionsAccepter(serverSocket, this), "Thread-New Connection Accepter").start();
            this.setServerStatus(true);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ex1) {
                    Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        return false;
    }
    
    public boolean stopServer() {
        if(serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        this.setServerStatus(false);
        return true;
    }

    void handleIncomingSocket(Socket socket) {
        long newUserId = userCounter.incrementAndGet();
        ServerUser serverUser = new ServerUser(newUserId, socket.getInetAddress().getHostName(), new LocalDateTime());
        UserCommunicationChanel userCommunicationChanel = new UserCommunicationChanel(serverUser, socket, dispatcher, serverKeyPair);
        allUsers.put(newUserId, userCommunicationChanel);
        
    }

    public void setServerStatus(boolean serverOn) {
        this.serverOn = serverOn;
        serverForm.changeServerStatus("SERVER IS NOW " +( serverOn  ? "ONLINE" : "OFFLINE"));
        //update label
    }

}
