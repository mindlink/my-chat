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
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * The message timestamp.
     */
    public Instant timestamp;

    /**
     * The message sender.
     */
    public String senderId;

    public String getSender() {
        return senderId;
    }

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
}
