package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {

    /**
     * The name of the conversation.
     */
    protected String name;

    /**
     * The messages in the conversation.
     */
    protected Collection<Message> messages;

    /**
     * Analyzed metrics of messages report
     */
    protected Collection<UserActivity> activity;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

}
