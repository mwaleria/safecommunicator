package pl.mwaleria.safecommunicator.client.msg;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author mwaleria
 */
public class ChatThread {

    private final Set<Long> otherUsersIn;

    public ChatThread(Long[] users) {
        otherUsersIn = new HashSet<>();
        otherUsersIn.addAll(Arrays.asList(users));
    }

    public Set<Long> getOtherUsersIn() {
        return Collections.unmodifiableSet(otherUsersIn);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.otherUsersIn);
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
        final ChatThread other = (ChatThread) obj;
        if (!Objects.equals(this.otherUsersIn, other.otherUsersIn)) {
            return false;
        }
        return true;
    }

    

}
