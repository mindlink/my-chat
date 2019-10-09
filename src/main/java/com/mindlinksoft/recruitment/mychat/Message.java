package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;

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

    public boolean containsAnyOf(List<String> words) {
        for(int i = 0; i<words.size(); ++i) {
            if (content.contains(words.get(i))) return true;
        }
        return false;
    }

    public void redactAllOf(List<String> words) {
        for(int i = 0; i<words.size(); ++i) {
            content = content.replaceAll("(?i)" + Pattern.quote(words.get(i)),"*redacted*");
        }
    }
}
