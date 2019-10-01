package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
    
    private ArrayList<String[]> sortedUserData;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages, ArrayList<String[]> sortedUserData) {
        this.name = name;
        this.messages = messages;
        this.sortedUserData = sortedUserData;
    }
    
    public String getName() {
    	return name;
    }
    
    public Collection<Message> getMessages() {
    	return messages;
    }
    
    public ArrayList<String[]> getSortedUserData() {
    	return sortedUserData;
    }
}
