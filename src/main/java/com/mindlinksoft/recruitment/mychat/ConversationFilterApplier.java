package com.mindlinksoft.recruitment.mychat;

import java.util.List;

/**
 * Minimal class applying filters from a list to a conversation.*/
class ConversationFilterApplier {

	/**Modifies the parameter conversation based on the list of filter parameter
	 * @param filters a List of ConversationFilter objects
	 * @param conversation the conversation to be modified according to the
	 * list of filters*/
	static void applyFilters(List<ConversationFilter> filters, 
													Conversation conversation) {
		for(ConversationFilter filter : filters)
			filter.apply(conversation);
	}
}
