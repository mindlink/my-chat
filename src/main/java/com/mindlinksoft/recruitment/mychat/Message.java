package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message.
 */
class Message {
    /**
     * The message content.
     */
    String content;

    /**
     * The message timestamp.
     */
    Instant timestamp;

    /**
     * The message sender.
     */
    String senderId;

    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    Message(long timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = Instant.ofEpochSecond(timestamp);
        this.senderId = senderId;
    }
    
    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    Message(Instant timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }

}
 