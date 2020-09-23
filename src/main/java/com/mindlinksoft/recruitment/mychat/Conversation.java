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

	public Object[] userActivity;

	/**
	 * Initializes a new instance of the {@link Conversation} class.
	 * 
	 * @param name     The name of the conversation.
	 * @param messages The messages in the conversation.
	 */
	public Conversation(String name, Collection<Message> messages) {
		this.name = name;
		this.messages = messages;
	}

	public Conversation(String name, Collection<Message> messages, Object[] userActivity) {
		this.name = name;
		this.messages = messages;
		this.userActivity = userActivity;
	}

	public Collection<Message> getMessages() {
		return this.messages;
	}

	public String getName() {
		return this.name;
	}
}
