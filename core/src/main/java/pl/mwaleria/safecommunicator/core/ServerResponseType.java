package pl.mwaleria.safecommunicator.core;

import java.security.PublicKey;
import java.util.List;

/**
 *
 * @author mwaleria
 */
public enum ServerResponseType {
    SERVER_PUBLIC_KEY(PublicKey.class),
    ALL_USERS(List.class),
    NEW_USER(User.class),
    USER_LEFT(User.class),
    MESSAGE(Message.class),
    CHECK_USER_NAME_RESPONSE(Boolean.class);

    private ServerResponseType(Class<?> type) {
        this.type = type;
    }
    
    private final transient Class<?> type;

    public Class<?> getType() {
        return type;
    }
}
