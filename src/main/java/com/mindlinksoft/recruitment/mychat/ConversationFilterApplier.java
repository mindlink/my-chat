package com.mindlinksoft.recruitment.mychat;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Minimal class applying filters from a list to a conversation.*/
class ConversationFilterApplier {

	private final static Logger LOGGER = Logger.getLogger("com.mindlinksoft.recruitment.mychat");

	/**Modifies the parameter conversation based on the list of filter parameter
	 * @param filters a List of ConversationFilter objects
	 * @param conversation the conversation to be modified according to the
	 * list of filters*/
	static void applyFilters(List<ConversationFilter> filters, 
													Conversation conversation) {
		LOGGER.log(Level.INFO, "Applying filters ...");
		for(ConversationFilter filter : filters)
			filter.apply(conversation);
	}
}
