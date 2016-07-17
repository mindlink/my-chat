package com.mindlinksoft.recruitment.mychat.bean;

import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.model.ConversationReportBuilder;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {

	private final String name_;
	private final Collection<Message> messages_;
	private final Report report_;

	public Conversation(String name, Collection<Message> messages) {
		name_ = name;
		messages_ = messages;
		report_ = new ConversationReportBuilder(messages).build();
	}

	public String getName() {
		return name_;
	}

	public Collection<Message> getMessages() {
		return messages_;
	}

	public Report getReport() {
		return report_;
	}
}
