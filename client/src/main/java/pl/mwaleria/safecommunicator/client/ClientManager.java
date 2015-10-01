package pl.mwaleria.safecommunicator.client;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.mwaleria.safecommunicator.client.net.NetworkManager;
import pl.mwaleria.safecommunicator.core.net.OutputStreamHandler;
import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.ServerRequestType;
import pl.mwaleria.safecommunicator.core.User;
import pl.mwaleria.safecommunicator.core.cipher.CipherManager;
import pl.mwaleria.safecommunicator.core.cipher.SafeCommunicatorKeyGenerator;

/**
 *
 * @author mwaleria
 */
public class ClientManager {
    
    private final NetworkManager networkManager;
    private CipherManager cipherManager;
    private KeyPair myKeyPair;
   
    
    public ClientManager() {
        networkManager = new NetworkManager();
        cipherManager = new CipherManager();
    }
    
    public ConnectResponse connectToServer(String address, String port, String userName, String randomString) {
        if (networkManager.connect(address, Integer.valueOf(port))) {
            this.generateKeysAndRegisterUser(userName, randomString);            
            return ConnectResponse.SUCCESS;
        } else {
            return ConnectResponse.SERVER_NOT_FOUND;
        }
        
    }

    private void generateKeysAndRegisterUser(String userName, String randomString) {
        User user = new User();
        user.setUserName(userName);
        SafeCommunicatorKeyGenerator generator = new SafeCommunicatorKeyGenerator();
        myKeyPair = generator.generateKeyPair(randomString);
        user.setPublicKey(myKeyPair.getPublic());
        ServerRequest req  = new ServerRequest();
        req.setRequestType(ServerRequestType.REGISTER);
        req.setValue(user);
        networkManager.sendServerRequest(req);
    }
    
  
}
