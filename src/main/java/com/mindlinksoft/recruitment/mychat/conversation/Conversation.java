package com.mindlinksoft.recruitment.mychat.conversation;

import java.util.Collection;
import java.util.Map;

import com.mindlinksoft.recruitment.mychat.messages.Message;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {

	private String name;
	private Collection<Message> messages;
	private Map<String, Integer> usersActivityReport;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Message> getMessages() {
		return messages;
	}

	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}

	public Map<String, Integer> getUsersActivityReport() {
		return usersActivityReport;
	}

	public void setUsersActivityReport(Map<String, Integer> usersActivityReport) {
		this.usersActivityReport = usersActivityReport;
	}

	/**
	 * Initializes a new instance of the {@link Conversation} class.
	 * 
	 * @param name
	 * @param messages
	 */

	public Conversation(String name, Collection<Message> messages) {
		this.name = name;
		this.messages = messages;
	}
}
