package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.UUID;

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
    private Sender sender;

    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param sender the {@Link Sender} who sent the message
     * @param content The message content.
     */
    public Message(Instant timestamp, Sender sender, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Sender getSender() {
        return sender;
    }
}
