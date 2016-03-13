package com.mindlinksoft.recruitment.mychat.message;

import java.time.Instant;
import java.util.Objects;

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
    private String senderId;

    /**
     * The message content.
     */
    private String content;

    /**
     * Initializes a new instance of the {@link Message} class.
     *
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (!Objects.equals(this.timestamp, other.timestamp)) {
            return false;
        }
        if (!Objects.equals(this.senderId, other.senderId)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        return true;
    }

}
