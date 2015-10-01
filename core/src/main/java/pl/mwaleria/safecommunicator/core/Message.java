package pl.mwaleria.safecommunicator.core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author mwaleria
 */
public class Message implements Serializable {

    private Long userFrom;

    private Long usersTo;

    private String content;

    private LocalDateTime time;

    private Long[] usersInConversation;

    public Long getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(Long userFrom) {
        this.userFrom = userFrom;
    }

    public Long getUsersTo() {
        return usersTo;
    }

    public void setUsersTo(Long usersTo) {
        this.usersTo = usersTo;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Long[] getUsersInConversation() {
        return usersInConversation;
    }

    public void setUsersInConversation(Long[] usersInConversation) {
        this.usersInConversation = usersInConversation;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.userFrom);
        hash = 71 * hash + Objects.hashCode(this.usersTo);
        hash = 71 * hash + Objects.hashCode(this.content);
        hash = 71 * hash + Objects.hashCode(this.time);
        hash = 71 * hash + Arrays.deepHashCode(this.usersInConversation);
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
        final Message other = (Message) obj;
        if (!Objects.equals(this.userFrom, other.userFrom)) {
            return false;
        }
        if (!Objects.equals(this.usersTo, other.usersTo)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        if (!Arrays.deepEquals(this.usersInConversation, other.usersInConversation)) {
            return false;
        }
        return true;
    }

}
