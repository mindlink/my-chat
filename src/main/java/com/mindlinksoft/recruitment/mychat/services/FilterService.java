package com.mindlinksoft.recruitment.mychat.services;

import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

/**
 * Service to filter a conversation based on either a user or keyword.
 */
public final class FilterService {

	/**
     * Filter the conversation based on a specific user.
     * 
     * @param conversation The conversation to be filtered.
     * @param user Will only return messages from this user, ignoring case.
     */
	public Conversation filterByUser(Conversation conversation, String user) {
		List<Message> messages = new ArrayList<Message>();
		
		for (Message message : conversation.getMessages()) {
			if (message.getSenderId().equalsIgnoreCase(user)) {
				messages.add(message);
			}
		}
		
		return new Conversation(conversation.getName(), messages);
	}
	
	/**
     * Filter the conversation based on a specific keyword.
     * 
     * @param conversation The conversation to be filtered.
     * @param keyword Will only return messages with this keyword, ignoring case.
     */
	public Conversation filterByKeyword(Conversation conversation, String keyword) {
		List<Message> messages = new ArrayList<Message>();
		
		for (Message message : conversation.getMessages()) {
			if (message.getContent().toLowerCase().contains(keyword.toLowerCase())) {
				messages.add(message);
			}
		}
		
		return new Conversation(conversation.getName(), messages);
	}
}
