package pl.mwaleria.safecommunicator.client.net;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.mwaleria.safecommunicator.core.Constants;
import pl.mwaleria.safecommunicator.core.utils.CommunicationUtils;

/**
 *
 * @author mwaleria
 */
public class InputStreamHandler implements Runnable{

    private InputStream inputStream;
    private PrivateKey myPrivateKey;

    public InputStreamHandler(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
   

    @Override
    public void run() {
        while(true) {
            byte[] responseSize = new byte[Constants.NETWORK_MESSAGE_SIZE];
            try {
                inputStream.read(responseSize);
                int responseSizeInBytes = CommunicationUtils.bytesToint(responseSize);
            } catch (IOException ex) {
                Logger.getLogger(InputStreamHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            int responseSizeInBytes = CommunicationUtils.bytesToint(responseSize);
            
        }
    }
}
