package pl.mwaleria.safecommunicator.client;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.mwaleria.safecommunicator.client.net.NetworkManager;
import pl.mwaleria.safecommunicator.client.net.OutputStreamHandler;
import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.ServerRequestType;
import pl.mwaleria.safecommunicator.core.cipher.CipherManager;

/**
 *
 * @author mwaleria
 */
public class ClientManager {
    
    private final NetworkManager networkManager;
    private CipherManager cipherManager;
   
    
    public ClientManager() {
        networkManager = new NetworkManager();
        cipherManager = new CipherManager();
    }
    
    public ConnectResponse connectToServer(String address, String port, String userName, String randomString) {
        if (networkManager.connect(address, Integer.valueOf(port))) {
            this.sendRequestForServerPublicKey();            
            return ConnectResponse.SUCCESS;
        } else {
            return ConnectResponse.SERVER_NOT_FOUND;
        }
        
    }
    
    private void sendRequestForServerPublicKey() {
        ServerRequest request = new ServerRequest();
        request.setRequestType(ServerRequestType.GET_SERVER_PUBLIC_KEY);
        networkManager.sendServerRequest(request);
    }
}
