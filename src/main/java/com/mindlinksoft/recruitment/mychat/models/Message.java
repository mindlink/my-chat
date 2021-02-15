package com.mindlinksoft.recruitment.mychat.models;

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
    private String senderID;

    /**
     * The message content.
     */
    private String content;

    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderID  The ID of the sender.
     * @param content   The message content.
     */
    public Message(Instant timestamp, String senderID, String content) {
        this.timestamp = timestamp;
        this.senderID = senderID;
        this.content = content;
    }

    /**
     * Getter method for access to the {@code timestamp} of a {@code message}
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Getter method for access to the {@code senderID} of a {@code message}
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * Getter method for access to the {@code content} of a {@code message}
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter method for changing the {@code content} of a {@code message}
     */
    public void setContent(String content) {
        this.content = content;
    }
}
