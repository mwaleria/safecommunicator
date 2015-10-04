package pl.mwaleria.safecommunicator.client;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.SerializationUtils;

import pl.mwaleria.safecommunicator.client.net.ClientEventDispatcher;
import pl.mwaleria.safecommunicator.client.net.NetworkManager;
import pl.mwaleria.safecommunicator.core.EncryptedMessage;
import pl.mwaleria.safecommunicator.core.Message;
import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.ServerRequestType;
import pl.mwaleria.safecommunicator.core.User;
import pl.mwaleria.safecommunicator.core.cipher.CipherManager;
import pl.mwaleria.safecommunicator.core.cipher.CryptoException;
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
	private Long currentUserId;

	private Map<Long, User> users;

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
		users.clear();
		for (User user : userList.getUsers()) {
			users.put(user.getId(), user);
		}
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

	public void sendMessage(String content, Long... recipients) {
		if (recipients.length > 0) {
			for (Long id : recipients) {
				if (id != currentUserId) {
					try {
						ServerRequest req = new ServerRequest();
						req.setRequestType(ServerRequestType.SEND_MESSAGE);
						EncryptedMessage secretMessage = new EncryptedMessage();
						secretMessage.setContent(content);
						secretMessage.setUsersInConversation(recipients);
						byte[] plainSecretMessage = SerializationUtils.serialize(secretMessage);
						byte[] encryptedSecretMessage = cipherManager.encrypt(plainSecretMessage,
								this.getUserPublicKey(id));
						Message m = new Message();
						m.setEncryptedMessage(encryptedSecretMessage);
						m.setUsersTo(id);
						networkManager.sendServerRequest(req);
					} catch (CryptoException ex) {
						Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}

		}
	}

	private PublicKey getUserPublicKey(Long userId) {
		return users.get(userId).getPublicKey();
	}

	public void updateUserId(Long userId) {
		this.currentUserId = userId;
	}

	public Long getCurrentUserId() {
		return currentUserId;
	}

}
