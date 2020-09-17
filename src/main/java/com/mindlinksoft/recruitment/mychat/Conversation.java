package com.mindlinksoft.recruitment.mychat;

import java.util.Set;
import java.util.TreeSet;

/**
 * Represents the model of a conversation.
 * @author Mohamed Yusuf
 * 
 */
public final class Conversation {
	
    private String name;
    private TreeSet<Message> messages;
    private Set<User> report;

    /**
     * Initialises a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, TreeSet<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
    
    /**
     * Initialises a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     * @param report The most active users report.
     */
    public Conversation(String name, TreeSet<Message> messages, Set<User> report) {
        this.name = name;
        this.messages = messages;
        this.report = report;
    }

	public String getName() {
		return name;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public Set<User> getReport() {
		return report;
	}

	public void setReport(Set<User> report) {
		this.report = report;
	}
}
