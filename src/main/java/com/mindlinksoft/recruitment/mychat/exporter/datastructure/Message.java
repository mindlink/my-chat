package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

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
     * @param sender The object of the sender.
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

    /**
     * Returns a new Message object, called by BufferedReader
     * while loading the files.
     *
     * @param line line of text from the input file
     * @param senderMap the conversation's map of previously encountered senders
     * @return Message object with relevant sender, content and timestamp
     */
    public static Message parseLine(String line) {
        String[] data = line.split(" ", 3);

        Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(data[0]));
        Sender sender = new Sender(data[1]);
        String content = data[2];

        return new Message(timestamp, sender, content);
    }
}
