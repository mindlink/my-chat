package com.mindlinksoft.recruitment.mychat.models;

import java.time.Instant;

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
    private String senderId;

    /**
     * Initializes a new instance of the {@link Message} class.
     * 
     * @param messageBuilder builds new Conversation to avoid mutability.
     */
    public Message(MessageBuilder messageBuilder) {
        this.content = messageBuilder.content;
        this.timestamp = messageBuilder.timestamp;
        this.senderId = messageBuilder.senderId;
    }

    /**
     * Getter method for retrieving the timestamp of the message
     */
    public Instant getTimestamp() {
        return this.timestamp;
    }

    /**
     * Getter method for retrieving the sender ID of the message
     */
    public String getSenderId() {
        return this.senderId;
    }

    /**
     * Getter method for retrieving the content of the message
     */
    public String getContent() {
        return this.content;
    }

    public static class MessageBuilder {
        private String content;
        private Instant timestamp;
        private String senderId;

        public Message buildNewMessage(String senderId, String timestamp, String content) {
            return new MessageBuilder().senderId(senderId)
                    .timestamp(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp))).content(content).build();
        }

        public Message replaceContent(String senderId, Instant timestamp, String content) {
            return new MessageBuilder().senderId(senderId).timestamp(timestamp).content(content).build();
        }

        public MessageBuilder senderId(String senderId) {
            this.senderId = senderId;
            return this;
        }

        public MessageBuilder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public MessageBuilder content(String content) {
            this.content = content;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
