package pl.mwaleria.safecommunicator.client.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.SerializationUtils;

import pl.mwaleria.safecommunicator.core.Constants;
import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.ServerResponse;
import pl.mwaleria.safecommunicator.core.net.InputStreamHandler;
import pl.mwaleria.safecommunicator.core.net.OutputStreamHandler;
import pl.mwaleria.safecommunicator.core.utils.CommunicationUtils;

/**
 *
 * @author mwaleria
 */
public class NetworkManager {

	private OutputStreamHandler<ServerRequest> outputStreamHandler;
	private InputStreamHandler<ServerResponse> inputStreamHandler;
	private Socket socket;
	private KeyPair keyPair;
	private final ClientEventDispatcher dispatcher;

	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	public NetworkManager(ClientEventDispatcher dispatcher) {
		super();
		this.dispatcher = dispatcher;
	}

	public boolean connect(String address, int port) {
		try {
			socket = new Socket(address, port);
			this.handleConnectedSocket(socket);
		} catch (Exception ex) {
			Logger.getLogger(NetworkManager.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	private void handleConnectedSocket(Socket socket) throws IOException {
		PublicKey serverPublicKey = getServerPublicKey(socket);
		outputStreamHandler = new OutputStreamHandler<>(socket.getOutputStream(), serverPublicKey);
		Thread outputThread = new Thread(outputStreamHandler);

		outputThread.start();
	}

	private PublicKey getServerPublicKey(Socket s) throws IOException {
		InputStream is = s.getInputStream();
		byte[] serverPublicKeySizeBytes = new byte[Constants.NETWORK_MESSAGE_SIZE];
		is.read(serverPublicKeySizeBytes);
		byte[] publicKeyBytes = new byte[CommunicationUtils.bytesToint(serverPublicKeySizeBytes)];
		is.read(publicKeyBytes);
		return (PublicKey) SerializationUtils.deserialize(publicKeyBytes);
	}

	public void sendServerRequest(ServerRequest request) {
		if (outputStreamHandler != null) {
			outputStreamHandler.addTask(request);
		}
	}

	public OutputStreamHandler<ServerRequest> getOutputStreamHandler() {
		return outputStreamHandler;
	}

	public void openInputStreamAndSetKeys(KeyPair myKeyPair) {
		keyPair = myKeyPair;
		try {
			inputStreamHandler = new InputStreamHandler<>(socket.getInputStream(), keyPair.getPrivate(), dispatcher,
					Constants.SERVER_SENDER_ID);
			Thread t = new Thread(inputStreamHandler);
			t.start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
