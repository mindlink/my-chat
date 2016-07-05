package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message. Being an application specific data modelling class
 * (like {@link Conversation}, its fields are liberally accessible to the 
 * entirety of the package.
 */
class Message {
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
    Message(long timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = Instant.ofEpochSecond(timestamp);
        this.senderId = senderId;
    }
    
    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    Message(Instant timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }

    String getContent() {
    	return content;
    }
    
    Instant getTimestamp() {
    	return timestamp;
    }
    
    String getSenderId() {
    	return senderId;
    }
    
    void setContent(String content) {
    	this.content = content;
    }
    
    void setTimestamp(Instant timestamp) {
    	this.timestamp = timestamp;
    }
    
    /**
     * Will set the timestamp interpreting the parameter as the number of 
     * seconds*/
    void setTimestamp(long timestamp) {
    	this.timestamp = Instant.ofEpochSecond(timestamp);
    }
    
    void setSenderId(String senderId) {
    	this.senderId = senderId;
    }
}
 