package com.mindlinksoft.recruitment.mychat;



import java.util.Collection;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    public final String name;

    /**
     * The messages in the conversation.
     */
    public final Collection<Message> messages;

    /**
     * The Activity of Users in the conversation
     */
    public final Collection<Report> activity;
    
    /**
     * Initializes a new instance of the {@link Conversation} class with no report.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
    	this(name,messages,null);
    }
    /**
     * Initializes a new instance of the {@link Conversation} class with a report added.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     * @param activity The report to add to the conversation.
     */
    public Conversation(String name, Collection<Message> messages, Collection<Report> activity ) {
        this.name = name;
        this.messages = messages;
        this.activity = activity;
    }

}
