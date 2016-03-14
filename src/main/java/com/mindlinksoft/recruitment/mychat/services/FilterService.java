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
     * @param user Will return only this users messages.
     */
	public Conversation filterUser(Conversation conversation, String user) {
		List<Message> messages = new ArrayList<Message>();
		
		for (Message message : conversation.getMessages()) {
			if (message.getSenderId().equals(user)) {
				messages.add(message);
			}
		}
		
		return new Conversation(conversation.getName(), messages);
	}
}
