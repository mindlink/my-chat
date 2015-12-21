package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message {
	//variables
    /**
     * The message content.
     */
    private String content;

    /**
     * The message time stamp.
     */
    private Instant timeStamp;

    /**
     * The message sender.
     */
    private String senderId;

    //constructor
    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timeStamp The time stamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.content = content;
        this.timeStamp = timestamp;
        this.senderId = senderId;
    }

    //getters and setters
    /**
     * Gets content.
     */   
	public String getContent() {
		return content;
	}

    /**
     * Sets content.
     */   
	public void setContent(String content) {
		this.content = content;
	}

    /**
     * Gets time stamp.
     */   
	public Instant getTimeStamp() {
		return timeStamp;
	}

    /**
     * Sets time stamp.
     */   
	public void setTimeStamp(Instant timestamp) {
		this.timeStamp = timestamp;
	}

    /**
     * Gets senderId.
     */   
	public String getSenderId() {
		return senderId;
	}

    /**
     * Sets senderId.
     */   
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
    
}
