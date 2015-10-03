
package pl.mwaleria.safecommunicator.server.model;

import java.security.PublicKey;
import java.util.Objects;
import org.joda.time.LocalDateTime;
import pl.mwaleria.safecommunicator.core.User;

/**
 *
 * @author mwaleria
 */
public class ServerUser {

    private User user;
    
    private String host;
    
    private LocalDateTime joinTime;
    
    public ServerUser( Long userId, String host, LocalDateTime joinTime) {
        user = new User();
        user.setId(userId);
        this.host = host;
        this.joinTime = joinTime;
    }

    public PublicKey getPublicKey() {
        return user.getPublicKey();
    }

    public void setPublicKey(PublicKey publicKey) {
        user.setPublicKey(publicKey);
    }

    public String getUserName() {
        return user.getUserName();
    }

    public void setUserName(String userName) {
        user.setUserName(userName);
    }

    public Long getId() {
        return user.getId();
    }

    public void setId(Long id) {
        user.setId(id);
    }

    @Override
    public int hashCode() {
        if(user != null) {
            return user.getId().intValue();
        }
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.host);
        hash = 37 * hash + Objects.hashCode(this.joinTime);
        
        return hash;
        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServerUser other = (ServerUser) obj;
        
        if(this.user != null && other.user != null) {
            return Objects.equals(this.user.getId(), other.user.getId());
        }
        return false;
    }
    
    
}
