package pl.mwaleria.safecommunicator.server.dispatcher;

import pl.mwaleria.safecommunicator.core.Message;
import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.ServerResponse;
import pl.mwaleria.safecommunicator.core.ServerResponseType;
import pl.mwaleria.safecommunicator.core.User;
import pl.mwaleria.safecommunicator.core.net.EventDispatcher;
import pl.mwaleria.safecommunicator.core.net.OutputStreamHandler;
import pl.mwaleria.safecommunicator.core.net.SafeCommunicatorRunnable;
import pl.mwaleria.safecommunicator.core.wrapper.UserList;
import pl.mwaleria.safecommunicator.server.ServerManager;

public class ServerEventDispatcher extends EventDispatcher<ServerResponse, ServerRequest> {

	private final ServerManager serverManager;

	public ServerEventDispatcher(ServerManager serverManager) {
		this.serverManager = serverManager;
	}

	@Override
	public SafeCommunicatorRunnable<ServerResponse, ServerRequest> createTask(ServerRequest t, Long senderId) {
		OutputStreamHandler<ServerResponse> outputStreamHandler = serverManager.getOutputStreamForUser(senderId);
		switch (t.getRequestType()) {
		case GET_ALL_USERS:
			return getAllUsers(t, outputStreamHandler);
		case REGISTER:
			return register(t, senderId, outputStreamHandler);
		case SEND_MESSAGE:
			return sendMessage(t, senderId, outputStreamHandler);
		default:
			break;
		}
		return null;
	}

	private SafeCommunicatorRunnable<ServerResponse, ServerRequest> register(ServerRequest t, final Long senderId,
			OutputStreamHandler<ServerResponse> outputStreamHandler) {
		SafeCommunicatorRunnable<ServerResponse, ServerRequest> output = new SafeCommunicatorRunnable<ServerResponse, ServerRequest>(
				t, outputStreamHandler) {
			@Override
			protected ServerResponse doTask(ServerRequest request) {
				User user = (User) request.getValue();
				user.setId(senderId);
				serverManager.registerUser(user);
				return null;
			}
		};
		return output;
	}

	private SafeCommunicatorRunnable<ServerResponse, ServerRequest> sendMessage(final ServerRequest t,
			final Long senderId, OutputStreamHandler<ServerResponse> outputStreamHandler) {
		SafeCommunicatorRunnable<ServerResponse, ServerRequest> output = new SafeCommunicatorRunnable<ServerResponse, ServerRequest>(
				t, outputStreamHandler) {
			@Override
			protected ServerResponse doTask(ServerRequest request) {
				ServerResponse response = new ServerResponse();
				Message message = (Message) response.getValue();
				message.setUserFrom(senderId);
				serverManager.sendMessage(message);
				return response;
			}
		};
		return output;
	}

	private SafeCommunicatorRunnable<ServerResponse, ServerRequest> getAllUsers(ServerRequest t,
			OutputStreamHandler<ServerResponse> outputStreamHandler) {
		SafeCommunicatorRunnable<ServerResponse, ServerRequest> output = new SafeCommunicatorRunnable<ServerResponse, ServerRequest>(
				t, outputStreamHandler) {
			@Override
			protected ServerResponse doTask(ServerRequest request) {
				ServerResponse response = new ServerResponse();
				response.setResponseType(ServerResponseType.ALL_USERS);
				UserList userList = new UserList(serverManager.getAllUsers());
				response.setValue(userList);
				return response;
			}
		};
		return output;
	}

}
