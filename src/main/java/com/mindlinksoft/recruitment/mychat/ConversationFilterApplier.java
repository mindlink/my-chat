package com.mindlinksoft.recruitment.mychat;

import java.util.List;

class ConversationFilterApplier {

	static void applyFilters(List<ConversationFilter> filters, 
													Conversation conversation) {
		for(ConversationFilter filter : filters)
			filter.apply(conversation);
	}
}
