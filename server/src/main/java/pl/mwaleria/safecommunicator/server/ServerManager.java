package pl.mwaleria.safecommunicator.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.LocalDateTime;

import pl.mwaleria.safecommunicator.core.Message;
import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.ServerResponse;
import pl.mwaleria.safecommunicator.core.ServerResponseType;
import pl.mwaleria.safecommunicator.core.User;
import pl.mwaleria.safecommunicator.core.cipher.SafeCommunicatorKeyGenerator;
import pl.mwaleria.safecommunicator.core.net.EventDispatcher;
import pl.mwaleria.safecommunicator.core.net.OutputStreamHandler;
import pl.mwaleria.safecommunicator.server.dispatcher.ServerEventDispatcher;
import pl.mwaleria.safecommunicator.server.gui.ServerForm;
import pl.mwaleria.safecommunicator.server.model.ServerUser;

/**
 *
 * @author mwaleria
 */
public class ServerManager {

	private boolean serverOn = false;

	private ServerSocket serverSocket;

	private final  AtomicLong userCounter = new AtomicLong(12L);

	private EventDispatcher<ServerResponse, ServerRequest> dispatcher;

	private final ConcurrentHashMap<Long, UserCommunicationChanel> allUsers;

	private ServerForm serverForm;

	private KeyPair serverKeyPair;

	private final ExecutorService shortTimeTaskThreadPool;

	public ServerManager() {
		shortTimeTaskThreadPool = Executors.newCachedThreadPool();
		allUsers = new ConcurrentHashMap<>();
		dispatcher = new ServerEventDispatcher(this);
	}

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
			if (serverSocket != null) {
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
		if (serverSocket != null) {
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
		UserCommunicationChanel userCommunicationChanel = new UserCommunicationChanel(serverUser, socket, dispatcher,
				serverKeyPair);
		allUsers.put(newUserId, userCommunicationChanel);
		shortTimeTaskThreadPool.execute(userCommunicationChanel);
	}

	private void broadcastWithout(long userId, ServerResponse responseToAll) {
		for (UserCommunicationChanel chanel : allUsers.values()) {
			if (chanel.getServerUser().getId() != userId && chanel.getChanelStatus() == UserChanelStatus.READY) {
				chanel.addOutputTask(responseToAll);
			}
		}
	}

	public void setServerStatus(boolean serverOn) {
		this.serverOn = serverOn;
		serverForm.changeServerStatus("SERVER IS NOW " + (serverOn ? "ONLINE" : "OFFLINE"));
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		for (UserCommunicationChanel userChanel : allUsers.values()) {
			if (userChanel.getChanelStatus() == UserChanelStatus.READY) {
				users.add(userChanel.getServerUser().getUser());
			}
		}
		return users;
	}

	public void sendMessage(Message message) {
		UserCommunicationChanel userCommunicationChanel = allUsers.get(message.getUsersTo());
		if (userCommunicationChanel.getChanelStatus() == UserChanelStatus.READY) {
			ServerResponse serverResponse = new ServerResponse();
			serverResponse.setResponseType(ServerResponseType.MESSAGE);
			serverResponse.setValue(message);
			userCommunicationChanel.addOutputTask(serverResponse);
		}
	}

	public void registerUser(User value) {
		ServerResponse newUserInfo = new ServerResponse();
		UserCommunicationChanel chanel = allUsers.get(value.getId());
		chanel.updateUserMissingInfo(value.getPublicKey(), value.getUserName());
		newUserInfo.setResponseType(ServerResponseType.NEW_USER);
		newUserInfo.setValue(value);
		this.broadcastWithout(value.getId(), newUserInfo);
	}

	public OutputStreamHandler<ServerResponse> getOutputStreamForUser(Long userId) {
		return allUsers.get(userId).getOutputStreamHandler();
	}

}
