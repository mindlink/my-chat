package com.mindlinksoft.recruitment.mychat.models;

import java.time.Instant;

/**
 * Represents the model of a chat message.
 */
public final class Message {

    private final Instant timestamp;
    private final String senderId;
    private final String content;

    /**
     * Initializes a new instance of the {@link Message} class.
     * 
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
     * Gets the timestamp for the message.
     * 
     * @return The message timestamp.
     */
    public Instant getTimestamp() {
    	return timestamp;
    }
    
    /**
     * Gets the sender Id for the message.
     * 
     * @return The message sender Id.
     */
    public String getSenderId() {
    	return senderId;
    }
    
    /**
     * Gets the content for the message.
     * 
     * @return The message content.
     */
    public String getContent() {
    	return content;
    }
    
    /**
     * Create a readable string for the message.
     * 
     * @return The message as a string
     */
    @Override
    public String toString() {
    	// TODO: Implement a to string method...
    	return "";
    }
}
