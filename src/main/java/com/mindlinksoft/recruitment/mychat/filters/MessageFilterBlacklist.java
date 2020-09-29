package com.mindlinksoft.recruitment.mychat.filters;

import java.util.List;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;

public class MessageFilterBlacklist implements MessageFilter {

	/**
	 * Redacts a specific word (set in the configuration), if it appears in any
	 * message content.
	 */
	@Override
	public List<Message> filterMessages(List<Message> messages, ConversationExporterConfiguration config) {
		final String redacted = "*redacted*";
		String blacklist = config.getBlacklist();
		if (!blacklist.equals(ConversationExporterConfiguration.NO_FILTER)) {
			String regexBlacklist = "\\b" + blacklist + "\\b";
			for (Message message : messages) {
				message.content = message.content.replaceAll(regexBlacklist, redacted);
			}
		}
		return messages;
	}

}
