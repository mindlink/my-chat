package com.mindlinksoft.recruitment.mychat;

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

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getContent() {
        return content;
    }
}
