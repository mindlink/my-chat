package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.List;

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
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }

    /**
     * replace words in blacklist with '*redacted*'
     * @param blacklist words to replace
     */
    public void hideWords(List<String> blacklist){
        for(String b: blacklist){
            content = content.replaceAll(b,"*redacted*");
        }
    }
}
