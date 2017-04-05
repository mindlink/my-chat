package com.mindlinksoft.recruitment.mychat.conversation;

import java.util.Collection;
import java.util.Map;

import com.mindlinksoft.recruitment.mychat.message.IMessage;

/**
 * Interface for a conversation.
 *
 */
public interface IConversation {

	/**
	 * Gets the name of the conversation.
	 * @return Name of the conversation.
	 */
	String getName();
	
	/**
	 * Gets all messages from the conversation.
	 * @return A collection of messages.
	 */
	Collection<IMessage> getMessages();
	
	/**
	 * Generates a report of messages sent per user.
	 * @return Map of messages sent per user.
	 */
	Map<String, Long> getUserActivity();
}