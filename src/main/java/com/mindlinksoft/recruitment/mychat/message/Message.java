package com.mindlinksoft.recruitment.mychat.message;

import java.time.Instant;

import org.apache.commons.lang3.Validate;

/**
 * Represents a chat message.
 */
public final class Message implements IMessage {

    private final Instant timestamp;
    private String senderId;
    private String content;
    
    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.content = Validate.notEmpty(content);
        this.timestamp = Validate.notNull(timestamp);
        this.senderId = Validate.notEmpty(senderId);
    }

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public Instant getTimestamp() {
		return timestamp;
	}

	@Override
	public String getSenderId() {
		return senderId;
	}

	@Override
	public void setContent(String content) {
		this.content = Validate.notEmpty(content);
	}

	@Override
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
}
