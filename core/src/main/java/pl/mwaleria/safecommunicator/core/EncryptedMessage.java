package pl.mwaleria.safecommunicator.core;

import java.io.Serializable;
import java.util.Arrays;

public class EncryptedMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String content;

	private Long[] usersInConversation;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long[] getUsersInConversation() {
		return usersInConversation;
	}

	public void setUsersInConversation(Long[] usersInConversation) {
		this.usersInConversation = usersInConversation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + Arrays.hashCode(usersInConversation);
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
		EncryptedMessage other = (EncryptedMessage) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (!Arrays.equals(usersInConversation, other.usersInConversation))
			return false;
		return true;
	}

}
