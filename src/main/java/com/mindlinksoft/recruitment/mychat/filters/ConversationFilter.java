package com.mindlinksoft.recruitment.mychat.filters;

import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Represents the generic type of feature or search mechanism the client can use to filter data and obtain a result.
 */

public interface ConversationFilter {
	
	public Collection<Message> useFilter(Collection<Message> conversation) throws Exception;
	
}
