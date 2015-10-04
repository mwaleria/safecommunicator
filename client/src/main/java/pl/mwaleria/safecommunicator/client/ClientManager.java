package pl.mwaleria.safecommunicator.client;

import java.security.KeyPair;
import java.util.List;

import pl.mwaleria.safecommunicator.client.net.ClientEventDispatcher;
import pl.mwaleria.safecommunicator.client.net.NetworkManager;
import pl.mwaleria.safecommunicator.core.Message;
import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.ServerRequestType;
import pl.mwaleria.safecommunicator.core.User;
import pl.mwaleria.safecommunicator.core.cipher.CipherManager;
import pl.mwaleria.safecommunicator.core.cipher.SafeCommunicatorKeyGenerator;
import pl.mwaleria.safecommunicator.core.net.OutputStreamHandler;
import pl.mwaleria.safecommunicator.core.wrapper.UserList;

/**
 *
 * @author mwaleria
 */
public class ClientManager {

	private final NetworkManager networkManager;
	private CipherManager cipherManager;
	private KeyPair myKeyPair;

	private List<User> users;

	public ClientManager() {
		ClientEventDispatcher dispatcher = new ClientEventDispatcher(this);
		networkManager = new NetworkManager(dispatcher);
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
		ServerRequest req = new ServerRequest();
		req.setRequestType(ServerRequestType.REGISTER);
		req.setValue(user);
		networkManager.openInputStreamAndSetKeys(myKeyPair);
		networkManager.sendServerRequest(req);
	}

	public OutputStreamHandler<ServerRequest> getOutputStreamHandler() {
		return networkManager.getOutputStreamHandler();
	}

	public void updateUserList(UserList userList) {
		users = userList.getUsers();

	}

	public void addNewUser(User user) {
		// TODO Auto-generated method stub

	}

	public void deleteUser(User user) {
		// TODO Auto-generated method stub

	}

	public void handleNewMessage(Message m) {
		// TODO Auto-generated method stub

	}

}
