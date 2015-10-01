package pl.mwaleria.safecommunicator.core;

/**
 *
 * @author mwaleria
 */
public enum ServerRequestType {

    REGISTER(User.class),
    GET_SERVER_PUBLIC_KEY(Void.class),
    GET_ALL_USERS(Void.class),
    CHECK_USER_NAME(String.class),
    SEND_MESSAGE(Message.class);

    private ServerRequestType(Class<?> type) {
        this.type = type;
    }

    private final transient Class<?> type;

    public Class<?> getType() {
        return type;
    }
}
