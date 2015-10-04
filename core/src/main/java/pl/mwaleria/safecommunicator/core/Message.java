package pl.mwaleria.safecommunicator.core;

import java.io.Serializable;

import java.util.Arrays;
import org.joda.time.LocalDateTime;

/**
 *
 * @author mwaleria
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long usersTo;

	private Long userFrom;

	private LocalDateTime time;

	private byte[] encryptedMessage;

	public Long getUsersTo() {
		return usersTo;
	}

	public void setUsersTo(Long usersTo) {
		this.usersTo = usersTo;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public byte[] getEncryptedMessage() {
		return encryptedMessage;
	}

	public void setEncryptedMessage(byte[] encryptedMessage) {
		this.encryptedMessage = encryptedMessage;
	}

	public Long getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(Long userFrom) {
		this.userFrom = userFrom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(encryptedMessage);
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((userFrom == null) ? 0 : userFrom.hashCode());
		result = prime * result + ((usersTo == null) ? 0 : usersTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (!Arrays.equals(encryptedMessage, other.encryptedMessage))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (userFrom == null) {
			if (other.userFrom != null)
				return false;
		} else if (!userFrom.equals(other.userFrom))
			return false;
		if (usersTo == null) {
			if (other.usersTo != null)
				return false;
		} else if (!usersTo.equals(other.usersTo))
			return false;
		return true;
	}
}
