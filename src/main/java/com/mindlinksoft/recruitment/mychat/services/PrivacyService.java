package com.mindlinksoft.recruitment.mychat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

/**
 * Service to apply any privacy to the messages that get exported.
 */
public final class PrivacyService {

	/**
     * Redact a set of words or phrases from the {@link Conversation}.
     * 
     * @param conversation The {@link Conversation} to have privacy applied.
     * @param blacklist The words or phrases to be Redacted.
     */
	public Conversation redactWords(Conversation conversation, List<String> blacklist) {
		List<Message> messages = new ArrayList<Message>();
		
		for (Message message : conversation.getMessages()) {		
			for (String word : blacklist) {
				message.setContent(message.getContent().replaceAll("(?i)\\b" + Pattern.quote(word) + "\\b", "*redacted*"));
			}
			messages.add(message);
		}		
		return new Conversation(conversation.getName(), messages);
	}
}
