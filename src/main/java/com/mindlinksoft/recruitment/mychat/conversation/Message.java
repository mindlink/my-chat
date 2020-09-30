package com.mindlinksoft.recruitment.mychat.conversation;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message {
    /**
     * The message content.
     */
    private String content;

    /**
     * The message timestamp.
     */
    private Instant timestamp;

    /**
     * The message sender.
     */
    private String senderId;

    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }

    /**
     * Gets the message content
     */
	public String getContent() {
		return content;
	}
	
	/**
	 * Gets the message timestamp
	 */
	public Instant getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Gets the message sender ID
	 */
	public String getSenderId() {
		return senderId;
	}
}
