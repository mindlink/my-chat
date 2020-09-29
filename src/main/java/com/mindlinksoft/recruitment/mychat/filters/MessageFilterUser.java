package com.mindlinksoft.recruitment.mychat.filters;

import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;

public class MessageFilterUser implements MessageFilter {

	/**
	 * Filters messages by a specific user set in the configuration. Only messages
	 * sent by the specific user will be stored in the messages field.
	 */
	@Override
	public List<Message> filterMessages(List<Message> messages, ConversationExporterConfiguration config) {
		List<Message> postFilterMessages = new ArrayList<Message>();
		String userFilter = config.getUserFilter();
		if (userFilter.equals(ConversationExporterConfiguration.NO_FILTER)) {
			postFilterMessages = messages; // This function does nothing if the user has not requested this filter.
		} else {
			for (Message message : messages) {
				if (message.senderId.equals(userFilter)) {
					postFilterMessages.add(message);
				}
			}
		}		
		return postFilterMessages;
	}

}
