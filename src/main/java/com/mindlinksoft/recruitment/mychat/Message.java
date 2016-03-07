package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message
{
    private final String content, senderId;
    private final Instant timestamp;

    /**
     * Initialises a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content)
    {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }
    
    public String GetContent()  { return this.content; }
    public String GetSenderId() { return this.senderId; }
    public Instant GetTimeStamp() { return this.timestamp; }
}
