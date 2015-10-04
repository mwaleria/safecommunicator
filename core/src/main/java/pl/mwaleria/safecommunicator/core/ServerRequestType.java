package pl.mwaleria.safecommunicator.core;

/**
 *
 * @author mwaleria
 */
public enum ServerRequestType {

	REGISTER(User.class), GET_ALL_USERS(Void.class), SEND_MESSAGE(Message.class);

	private ServerRequestType(Class<?> type) {
		this.type = type;
	}

	private final transient Class<?> type;

	public Class<?> getType() {
		return type;
	}
}
