package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    public String name;

    /**
     * The messages in the conversation.
     */
    public Collection<Message> messages;
    
    /**
     * The conversation activity report.
     */
    public Collection<ActivityReport> report;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
    
    /**
     * Second constructor, initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     * @param report The activity report of the conversation.
     */
    public Conversation(String name, Collection<Message> messages, Collection<ActivityReport> report) {
        this.name = name;
        this.messages = messages;
        this.report = report;
    }
}
