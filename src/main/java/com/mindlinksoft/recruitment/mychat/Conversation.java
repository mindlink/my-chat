package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    private String name;

    /**
     * The messages in the conversation.
     */
    private Collection<Message> messages;

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
     * Getter for the name of the conversation.
     * @return The string name of the conversation.
     */
    public String getName() {
    	return this.name;
    }
    
    /**
     * Setter for the name of the conversation.
     * @param name The string name of the conversation.
     */
    public void setName(String name) {
    	this.name = name;
    }
    
    /**
     * Getter for the messages of the conversation.
     * @return The collection of messages of the conversation.
     */
    public Collection<Message> getMessages() {
    	return this.messages;
    }
    
    /**
     * Setter for the content of the message.
     * @param The string content of the message.
     */
    public void setMessages(Collection<Message> messages) {
    	this.messages = messages;
    }
}
