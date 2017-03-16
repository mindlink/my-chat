package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message {
    /**
     * Message content declaration.
     */
    private String content;

    /**
     * Message timestamp declaration.
     */
    private Instant timestamp;

    /**
     * Message sender declaration.
     */
    private String senderId;

    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.setContent(content);
        this.setTimestamp(timestamp);
        this.setSenderId(senderId);
    }

    /**
     * Content getter method.
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Content setter method.
     * @param content Sets the content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Timestamp getter method.
     * @return timestamp.
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Timestampl setter method.
     * @param timestamp Sets the timestamp.
     */
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * SenderID getter method.
     * @return senderid.
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * SenderId setter method.
     * @param senderId Sets senderId.
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    
}
