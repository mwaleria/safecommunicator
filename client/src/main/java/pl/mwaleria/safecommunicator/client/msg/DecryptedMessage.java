package pl.mwaleria.safecommunicator.client.msg;

import org.joda.time.LocalDateTime;

public class DecryptedMessage {

	private LocalDateTime time;

	private Long from;

	private Long[] userInConversation;

	private String content;

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

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long[] getUserInConversation() {
		return userInConversation;
	}

	public void setUserInConversation(Long[] userInConversation) {
		this.userInConversation = userInConversation;
	}

}
