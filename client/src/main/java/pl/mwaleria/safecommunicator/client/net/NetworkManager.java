package pl.mwaleria.safecommunicator.client.net;

import pl.mwaleria.safecommunicator.core.net.OutputStreamHandler;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;
import pl.mwaleria.safecommunicator.core.Constants;
import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.utils.CommunicationUtils;

/**
 *
 * @author mwaleria
 */
public class NetworkManager {

    private OutputStreamHandler outputStreamHandler;
    private Socket socket;

    public boolean connect(String address, int port) {
        try {
            socket = new Socket(address, port);
            this.handleConnectedSocket(socket);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    private void handleConnectedSocket(Socket socket) throws IOException {
        PublicKey serverPublicKey = getServerPublicKey(socket);
        outputStreamHandler = new OutputStreamHandler(socket.getOutputStream(), serverPublicKey);
        Thread outputThread = new Thread(outputStreamHandler);
        outputThread.start();
    }

    private PublicKey getServerPublicKey(Socket s) throws IOException {
        InputStream is = s.getInputStream();
        byte[] serverPublicKeySizeBytes = new byte[Constants.NETWORK_MESSAGE_SIZE];
        is.read(serverPublicKeySizeBytes);
        byte[] publicKeyBytes = new byte[CommunicationUtils.bytesToint(serverPublicKeySizeBytes)];
        is.read(publicKeyBytes);
        return  (PublicKey) SerializationUtils.deserialize(publicKeyBytes);
    }

    public void sendServerRequest(ServerRequest request) {
        if (outputStreamHandler != null) {
            outputStreamHandler.addTask(request);
        }
    }

}
