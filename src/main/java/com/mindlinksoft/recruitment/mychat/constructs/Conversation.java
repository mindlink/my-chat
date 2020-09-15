package com.mindlinksoft.recruitment.mychat.constructs;

import java.util.Collection;

/**
 * Represents the model of a conversation.
 */
public final class Conversation
{
    // The name of the conversation.
    private final String name;
    // The messages in the conversation.
    private final Collection<Message> messages;
    // The most active users
    private Collection<User> userReport;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     *
     * @param name     The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages)
    {
        this.name = name;
        this.messages = messages;
        this.userReport = null;
    }

    public String getName()
    {
        return name;
    }

    public Collection<Message> getMessages()
    {
        return messages;
    }

    public Collection<User> getUserReport()
    {
        return userReport;
    }

    public void setUserReport(Collection<User> userReport)
    {
        this.userReport = userReport;
    }
}
