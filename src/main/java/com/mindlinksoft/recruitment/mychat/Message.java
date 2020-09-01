package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message {
    /**
     * The content of a message.
     */
    public String content;

    /**
     * The timestamp for the message.
     */
    public Instant timestamp;

    /**
     * The ID of the user that sent the message.
     */
    public String userID;

    /**
     * Initializes a new instance of the {@link Message} class.
     * @param content The content of a message.
     * @param timestamp The timestamp for the message.
     * @param userID The ID of the user that sent the message.
     */
    public Message(Instant timestamp, String userID, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.userID = userID;
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
}
