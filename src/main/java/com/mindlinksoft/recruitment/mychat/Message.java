package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.Map;

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
     * @param senderId The ID of the sender.
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

    public Instant getTimestamp() {
        return timestamp;
    }

    public Sender getSender() {
        return sender;
    }

    public static Message parseLine(String line, Map<String, Sender> senderMap) {
        // TODO: finish implementing
        String[] data = line.split(" ", 3);
        
        Sender sender = senderMap.getOrDefault(data[0], new Sender(data[0]));
        senderMap.putIfAbsent(data[0], sender);

        return null;
    }
}
