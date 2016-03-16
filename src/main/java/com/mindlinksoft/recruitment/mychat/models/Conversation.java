package com.mindlinksoft.recruitment.mychat.models;

import java.util.List;

/**
 * Represents the model for a conversation.
 */
public class Conversation {

    private final String name;
    private final List<Message> messages;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * 
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, List<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
    
    /**
     * Gets the name for the conversation.
     * 
     * @return The conversation name.
     */
    public String getName() {
    	return name;
    }
    
    /**
     * Gets all the messages within the conversation.
     * 
     * @return The list of the conversation's {@link Message}s.
     */
    public List<Message> getMessages() {
    	return messages;
    }
    
    /**
     * Create a readable string for the conversation.
     * 
     * @return The conversation as a string
     */
    @Override
    public String toString() {
    	// TODO: Implement a to string method...
    	return "";
    }
}
