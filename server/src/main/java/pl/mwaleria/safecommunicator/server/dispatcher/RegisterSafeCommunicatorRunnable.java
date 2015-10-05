package pl.mwaleria.safecommunicator.server.dispatcher;

import java.util.logging.Logger;

import pl.mwaleria.safecommunicator.core.ServerRequest;
import pl.mwaleria.safecommunicator.core.ServerResponse;
import pl.mwaleria.safecommunicator.core.ServerResponseType;
import pl.mwaleria.safecommunicator.core.User;
import pl.mwaleria.safecommunicator.core.net.SafeCommunicatorRunnable;
import pl.mwaleria.safecommunicator.server.ServerManager;

public class RegisterSafeCommunicatorRunnable extends SafeCommunicatorRunnable<ServerResponse, ServerRequest> {

	private final ServerManager serverManager;

	private final Long senderId;

	public RegisterSafeCommunicatorRunnable(ServerRequest request, final ServerManager serverManager,
			final Long senderId) {
		super(request, null);
		this.serverManager = serverManager;
		this.senderId = senderId;

	}

	@Override
	public void run() {
		User user = (User) request.getValue();
		this.replaceInvalidUserName(user);
		user.setId(senderId);
		serverManager.registerUser(user);
		ServerResponse serverResponse = new ServerResponse();
		serverResponse.setResponseType(ServerResponseType.YOUR_USER_ID);
		serverResponse.setValue(senderId);
		Logger.getLogger(this.getClass().getName()).info("User Registered :" + user.getUserName());
		serverManager.getOutputStreamForUser(senderId).addTask(serverResponse);
	}

	@Override
	protected ServerResponse doTask(ServerRequest request) {
		return null;
	}

	/**
	 * :-)
	 * 
	 * @param user
	 */
	private void replaceInvalidUserName(User user) {
		if (user.getUserName().toUpperCase().contains("DAMIAN")) {
			user.setUserName("DANIEL");
		}
	}

}
