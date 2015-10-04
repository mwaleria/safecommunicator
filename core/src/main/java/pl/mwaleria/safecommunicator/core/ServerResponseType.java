package pl.mwaleria.safecommunicator.core;

import pl.mwaleria.safecommunicator.core.wrapper.UserList;

/**
 *
 * @author mwaleria
 */
public enum ServerResponseType {
	ALL_USERS(UserList.class), NEW_USER(User.class), USER_LEFT(User.class), MESSAGE(Message.class);

	private ServerResponseType(Class<?> type) {
		this.type = type;
	}

	private final transient Class<?> type;

	public Class<?> getType() {
		return type;
	}
}
