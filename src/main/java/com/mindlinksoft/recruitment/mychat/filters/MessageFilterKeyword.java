package com.mindlinksoft.recruitment.mychat.filters;

import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;

public class MessageFilterKeyword implements MessageFilter {

	/**
	 * Filters messages by a specific keyword set in the configuration. Only
	 * messages containing the specific keyword will be stored in the messages
	 * field.
	 */
	@Override
	public List<Message> filterMessages(List<Message> messages, ConversationExporterConfiguration config) {
		List<Message> postFilterMessages = new ArrayList<Message>();
		String keywordFilter = config.getKeywordFilter();
		if (keywordFilter.equals(ConversationExporterConfiguration.NO_FILTER)) {
			postFilterMessages = messages;
		}else {
			for (Message message : messages) {
				if (message.content.contains(keywordFilter)) {
					postFilterMessages.add(message);
				}
			}
		}
		return postFilterMessages;
	}

}
