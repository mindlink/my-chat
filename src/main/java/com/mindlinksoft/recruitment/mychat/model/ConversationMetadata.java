package com.mindlinksoft.recruitment.mychat.model;

import java.util.Map;

public final class ConversationMetadata 
{
	public final Map<String, Integer> userActivity;
	
	public ConversationMetadata(Map<String, Integer> userActivity)
	{
		this.userActivity = userActivity;
	}

}
