package com.mindlinksoft.recruitment.mychat.model;

import java.util.Map;

/**
 * Class to hold metadata about the conversation
 * Currently just holding the useractivity
 *
 */
public final class ConversationMetadata 
{
	/**
	 * Map to hold the number of messages each user sends
	 */
	public final Map<String, Integer> userActivity;
	
	/**
	 * Constructor for ConversationMetadata - intialised with a Map of username to number of messages sent
	 * @param userActivity
	 */
	public ConversationMetadata(Map<String, Integer> userActivity)
	{
		this.userActivity = userActivity;
	}

}
