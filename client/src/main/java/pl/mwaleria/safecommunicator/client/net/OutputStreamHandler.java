package pl.mwaleria.safecommunicator.client.net;

import java.io.IOException;
import java.io.OutputStream;
import java.security.PublicKey;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;
import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.cipher.CipherManager;
import pl.mwaleria.safecommunicator.core.utils.CommunicationUtils;

/**
 *
 * @author mwaleria
 */
public class OutputStreamHandler implements Runnable{
    
    private final OutputStream outputStream;
    
    private final ConcurrentLinkedQueue<ServerRequest> queue;
    
    private final PublicKey consumerPublicKey;
    
    private final CipherManager cipherManager;

    public OutputStreamHandler(OutputStream outputStream, PublicKey consumerPublicKey) {
        this.outputStream = outputStream;
        queue = new ConcurrentLinkedQueue<>();
        cipherManager = new CipherManager();
        this.consumerPublicKey = consumerPublicKey;
    }

    @Override
    public void run() {
        while(true) {
            ServerRequest request = queue.poll();
            if(request != null) {
                try {
                    byte[] obj = cipherManager.encrypt(SerializationUtils.serialize(request),consumerPublicKey);
                    outputStream.write(CommunicationUtils.intToBytes(obj.length));
                    outputStream.write(obj);
                } catch (IOException ex) {
                    Logger.getLogger(OutputStreamHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void addTask(ServerRequest req) {
        queue.offer(req);
    }

}
