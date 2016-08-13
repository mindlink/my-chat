package main.java.com.mindlinksoft.recruitment.mychat.message;

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
     * @return the senderId
     */
    public String getSenderId() {
    	return senderId;
    }
    
    /**
     * @param senderId the senderId to set
     */
    public void setSenderId(String senderId) {
    	this.senderId = senderId;
    }
    
    /**
     * @return the content
     */
    public String getContent() {
    	return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
    	this.content = content;
    }

    /**
     * @return the timestamp
     */
    public Instant getTimestamp() {
    	return timestamp;
    }
    
    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Instant timestamp) {
    	this.timestamp = timestamp;
    }

}
