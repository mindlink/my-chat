package com.mindlinksoft.recruitment.mychat.filters;

import java.util.List;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;

public class MessageFilterClient {
	public List<Message> applyFilters(List<Message> messages, ConversationExporterConfiguration config) {
		List<Message> filteredMessages = messages;
		
		MessageFilterFactory filterFactory = new MessageFilterFactory();		
		
		MessageFilter userFilter = filterFactory.getFilter(MessageFilterFactory.USER_FILTER);
		filteredMessages = userFilter.filterMessages(filteredMessages, config);
		
		MessageFilter keywordFilter = filterFactory.getFilter(MessageFilterFactory.KEYWORD_FILTER);
		filteredMessages = keywordFilter.filterMessages(filteredMessages, config);
		
		MessageFilter blacklistFilter = filterFactory.getFilter(MessageFilterFactory.BLACKLIST_FILTER);
		filteredMessages = blacklistFilter.filterMessages(filteredMessages, config);
		
		MessageFilter hideDetailsFilter = filterFactory.getFilter(MessageFilterFactory.HIDE_DETAILS_FILTER);
		filteredMessages = hideDetailsFilter.filterMessages(filteredMessages, config);
		
		MessageFilter obfuscateUsersFilter = filterFactory.getFilter(MessageFilterFactory.OBFUSCATE_USERS_FILTER);
		filteredMessages = obfuscateUsersFilter.filterMessages(filteredMessages, config);
		
		return filteredMessages;
	}
}
