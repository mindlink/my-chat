package com.mindlinksoft.recruitment.mychat.message;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message implements MessageInterface {
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
     * gets the message content
     * @return String the content
     */
    public String getContent(){
        return content;
    }

    /**
     * gets the message sender id
     * @return String the sender id
     */
    public String getSenderId(){
        return senderId;
    }

    /**
     * gets the message timestamp
     * @return Instant the timestamp
     */
    public Instant getTimestamp(){
        return timestamp;
    }

    /**
     * sets the message content text
     * @param content String the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * sets the sender id
     * @param senderId String the sender id
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * sets the timestamp
     * @param timestamp Instant the timestamp
     */
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
