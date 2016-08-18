package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message {
   
    /**
     * The message timestamp.
     */
    private Instant timestamp;

    /**
     * The message sender.
     */
    private String senderId;

    /**
     * The message content.
     */
    private String content;

    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.content = content;
    }
    
    public Instant getTimestamp() {
		return timestamp;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getContent() {
		return content;
	}


	@Override
    public String toString() {
        return(timestamp.getEpochSecond() + " " + senderId + " " + content + "\n");
    }
}
