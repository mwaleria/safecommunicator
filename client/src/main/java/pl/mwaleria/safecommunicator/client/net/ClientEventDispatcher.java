package pl.mwaleria.safecommunicator.client.net;

import pl.mwaleria.safecommunicator.client.ClientManager;
import pl.mwaleria.safecommunicator.core.Message;
import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.ServerResponse;
import pl.mwaleria.safecommunicator.core.User;
import pl.mwaleria.safecommunicator.core.net.EventDispatcher;
import pl.mwaleria.safecommunicator.core.net.SafeCommunicatorRunnable;
import pl.mwaleria.safecommunicator.core.wrapper.UserList;

/**
 *
 * @author mwaleria
 */
public class ClientEventDispatcher extends EventDispatcher<ServerRequest, ServerResponse> {

	private final ClientManager clientManager;

	public ClientEventDispatcher(ClientManager clientManager) {
		super();
		this.clientManager = clientManager;
	}

	@Override
	public SafeCommunicatorRunnable<ServerRequest, ServerResponse> createTask(ServerResponse t, Long senderId) {
		switch (t.getResponseType()) {
		case ALL_USERS:
			return fromServerAllUsers(t);
		case MESSAGE:
			return fromServerMessage(t);
		case NEW_USER:
			return fromServerNewUser(t);
		case USER_LEFT:
			return fromServerUSerLeft(t);
		case YOUR_USER_ID:
			return fromServerMyUserId(t);
		default:
			break;

		}
		return null;
	}

	private SafeCommunicatorRunnable<ServerRequest, ServerResponse> fromServerMyUserId(ServerResponse t) {
		return new SafeCommunicatorRunnable<ServerRequest, ServerResponse>(t, clientManager.getOutputStreamHandler()) {
			@Override
			protected ServerRequest doTask(ServerResponse request) {
				Long userId = (Long) request.getValue();
				clientManager.updateUserId(userId);
				return null;
			}
		};
	}

	private SafeCommunicatorRunnable<ServerRequest, ServerResponse> fromServerUSerLeft(ServerResponse t) {
		return new SafeCommunicatorRunnable<ServerRequest, ServerResponse>(t, clientManager.getOutputStreamHandler()) {
			@Override
			protected ServerRequest doTask(ServerResponse request) {
				UserList userList = (UserList) request.getValue();
				clientManager.updateUserList(userList);
				return null;
			}
		};
	}

	private SafeCommunicatorRunnable<ServerRequest, ServerResponse> fromServerNewUser(ServerResponse t) {
		return new SafeCommunicatorRunnable<ServerRequest, ServerResponse>(t, clientManager.getOutputStreamHandler()) {
			@Override
			protected ServerRequest doTask(ServerResponse request) {
				User user = (User) request.getValue();
				clientManager.addNewUser(user);
				return null;
			}
		};
	}

	private SafeCommunicatorRunnable<ServerRequest, ServerResponse> fromServerMessage(ServerResponse t) {
		return new SafeCommunicatorRunnable<ServerRequest, ServerResponse>(t, clientManager.getOutputStreamHandler()) {
			@Override
			protected ServerRequest doTask(ServerResponse request) {
				Message m = (Message) request.getValue();
				clientManager.handleNewMessage(m);
				return null;
			}
		};
	}

	private SafeCommunicatorRunnable<ServerRequest, ServerResponse> fromServerAllUsers(ServerResponse t) {
		return new SafeCommunicatorRunnable<ServerRequest, ServerResponse>(t, clientManager.getOutputStreamHandler()) {
			@Override
			protected ServerRequest doTask(ServerResponse request) {
				User user = (User) request.getValue();
				clientManager.deleteUser(user);
				return null;
			}
		};
	}

}
