package com.mindlinksoft.recruitment.mychat.models;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message {
    /**
     * The message content.
     */
    public String content;

    /**
     * The message timestamp.
     */
    public Instant timestamp;

    /**
     * The message sender.
     */
    public String senderId;

    /**
     * Initializes a new instance of the {@link Message} class.
     * 
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId  The ID of the sender.
     * @param content   The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }

    /**
     * Getter method for retrieving the timestamp of the message
     */
    public Instant getTimestamp() {
        return this.timestamp;
    }

    /**
     * Getter method for retrieving the sender ID of the message
     */
    public String getSenderId() {
        return this.senderId;
    }

    /**
     * Getter method for retrieving the content of the message
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Setter method for updating the content of the message
     */
    public void setContent(String content) {
        this.content = content;
    }
}
