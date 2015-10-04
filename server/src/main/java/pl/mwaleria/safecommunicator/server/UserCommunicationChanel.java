package pl.mwaleria.safecommunicator.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.SerializationUtils;

import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.ServerResponse;
import pl.mwaleria.safecommunicator.core.net.EventDispatcher;
import pl.mwaleria.safecommunicator.core.net.InputStreamHandler;
import pl.mwaleria.safecommunicator.core.net.OutputStreamHandler;
import pl.mwaleria.safecommunicator.core.utils.CommunicationUtils;
import pl.mwaleria.safecommunicator.server.model.ServerUser;

/**
 *
 * @author mwaleria
 */
public class UserCommunicationChanel implements Runnable {

	private final ServerUser serverUser;
	private final Socket socket;
	private final EventDispatcher<?, ServerRequest> dispatcher;
	private final KeyPair serverKeyPair;
	private InputStreamHandler<ServerRequest> inputStreamHandler;
	private OutputStreamHandler<ServerResponse> outputStreamHandler;
	private UserChanelStatus chanelStatus;

	public UserCommunicationChanel(ServerUser serverUser, Socket socket, EventDispatcher<?, ServerRequest> dispatcher,
			KeyPair serverKeyPair) {
		this.chanelStatus = UserChanelStatus.NEW_USER_CHANEL;
		this.serverUser = serverUser;
		this.socket = socket;
		this.dispatcher = dispatcher;
		this.serverKeyPair = serverKeyPair;
	}

	public void run() {
		this.start(serverKeyPair);
	}

	private void start(KeyPair serverKeyPair) {
		try {
			inputStreamHandler = new InputStreamHandler<>(socket.getInputStream(), serverKeyPair.getPrivate(),
					dispatcher, serverUser.getId());
			this.startRunnable(inputStreamHandler, "THREAD - User:" + serverUser.getId() + "-inputStreamHandler");
			OutputStream os = socket.getOutputStream();
			byte[] serverPublicKey = SerializationUtils.serialize(serverKeyPair.getPublic());
			byte[] serverPublicKeySize = CommunicationUtils.intToBytes(serverPublicKey.length);
			os.write(serverPublicKeySize);
			os.write(serverPublicKey);
			this.chanelStatus = UserChanelStatus.SERVER_PUBLIC_KEY_SENT;
		} catch (IOException ex) {
			Logger.getLogger(UserCommunicationChanel.class.getName()).log(Level.SEVERE, null, ex);
			this.chanelStatus = UserChanelStatus.ERROR;
		}
	}

	public void updateUserMissingInfo(PublicKey userPublicKey, String userName) {
		serverUser.setPublicKey(userPublicKey);
		serverUser.setUserName(userName);
		try {
			outputStreamHandler = new OutputStreamHandler<>(socket.getOutputStream(), userPublicKey);
			this.startRunnable(outputStreamHandler, "THREAD - User:" + serverUser.getId() + "-outputStreamHandler");
			this.chanelStatus = UserChanelStatus.READY;
		} catch (IOException ex) {
			Logger.getLogger(UserCommunicationChanel.class.getName()).log(Level.SEVERE, null, ex);
			this.chanelStatus = UserChanelStatus.ERROR;
		}
	}

	private void startRunnable(Runnable r, String threadName) {
		Thread t = new Thread(r, threadName);
		t.start();
	}

	public UserChanelStatus getChanelStatus() {
		return chanelStatus;
	}

	public void setChanelStatus(UserChanelStatus chanelStatus) {
		this.chanelStatus = chanelStatus;
	}

	public ServerUser getServerUser() {
		return serverUser;
	}

	public void addOutputTask(ServerResponse response) {
		this.outputStreamHandler.addTask(response);
	}

	public OutputStreamHandler<ServerResponse> getOutputStreamHandler() {
		return outputStreamHandler;
	}

}
