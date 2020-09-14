package com.mindlinksoft.recruitment.mychat.constructs;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message
{
    // The message timestamp.
    private final Instant timestamp;
    // The message sender.
    private String senderId;
    // The message content.
    private String content;

    /**
     * Initializes a new instance of the {@link Message} class.
     *
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId  The ID of the sender.
     * @param content   The message content.
     */
    public Message(Instant timestamp, String senderId, String content)
    {
        this.content = content;
        // TODO: (?) Validate timestamp, and add unit tests
        this.timestamp = timestamp;
        this.senderId = senderId;
    }

    public Instant getTimestamp()
    {
        return timestamp;
    }

    public String getSenderId()
    {
        return senderId;
    }

    public void setSenderId(String senderId)
    {
        this.senderId = senderId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
