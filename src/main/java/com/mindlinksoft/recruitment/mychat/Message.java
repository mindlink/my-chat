package com.mindlinksoft.recruitment.mychat;

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
    public Instant timestamp;

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
     * Getter for the content of the message.
     * @return The string content of the message.
     */
    public String getContent() {
    	return this.content;
    }
    
    /**
     * Setter for the content of the message.
     * @return The string content of the message.
     */
    public void setContent(String content) {
    	this.content = content;
    }
    
    /**
     * Getter for the senderId of the message.
     * @return The string senderId of the message.
     */
    public String getSenderId() {
    	return this.senderId;
    }
    
    /**
     * Setter for the senderId of the message.
     * @return The string senderId of the message.
     */
    public void setSenderId(String senderId) {
    	this.senderId = senderId;
    }
}
