package com.mindlinksoft.recruitment.mychat;


/**
 * Represents a Conversation Filter Interface that allows for more filters to be easily added if needed.
 *
 */
public interface ConversationFilter {
	
	public Conversation filter(Conversation conversation, String filter);
	
}
