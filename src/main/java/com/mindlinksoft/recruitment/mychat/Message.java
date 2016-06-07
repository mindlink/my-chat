package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public class Message {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * The message content.
     */
    private String content;

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * The message timestamp.
     */
    private Instant timestamp;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

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
}
