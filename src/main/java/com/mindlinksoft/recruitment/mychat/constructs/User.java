package com.mindlinksoft.recruitment.mychat.constructs;

/**
 * Represents a user in a chat conversation.
 */
public class User
{
    // The message sender.
    private final String senderId;
    // The message content.
    private final int messageCount;

    /**
     * Initializes a new instance of the {@link Message} class.
     *
     * @param senderId The ID of the sender.
     * @param content  The message content.
     */
    public User(String senderId, int content)
    {
        this.senderId = senderId;
        this.messageCount = content;
    }

    public String getSenderId()
    {
        return senderId;
    }

    public int getMessageCount()
    {
        return messageCount;
    }
}
