package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

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
    private ArrayList<Message> messages;
    
    /**
     * The activity in number of messages of each user in the conversation.
     */
    private UserActivity[] activeUsers;

    /**
     * Initialises a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, ArrayList<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
    
    /**
     * Initialises a new instance of the {@link Conversation} class with user activity included.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     * @param activeUsers An array of UserActivity objects
     */
    
    public Conversation(String name, ArrayList<Message> messages, UserActivity[] activeUsers) {
        this.name = name;
        this.messages = messages;
        this.activeUsers = activeUsers;
    }
    
    
    public String getName() {
		return name;
    }
    
    public ArrayList<Message> getMessages() {
		return messages;
	}

	public UserActivity[] getActiveUsers() {
		return activeUsers;
	}
    
}
