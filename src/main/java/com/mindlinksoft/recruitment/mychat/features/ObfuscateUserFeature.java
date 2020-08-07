package com.mindlinksoft.recruitment.mychat.features;

import java.util.HashMap;
import java.util.Map;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Chat feature to obfuscate user names to User 0, User 1 etc
 *
 */
public class ObfuscateUserFeature implements ChatFeature 
{
	/**
	 * Not Applicable
	 */
	@Override
	public void applyMessageFeature(Message msg) 
	{
		//Do Nothing
	}

	/**
	 * Obfuscate user id's with User 0, User 1 ...etc
	 */
	@Override
	public void applyConversationFeature(Conversation convo) 
	{
		int newUser = 0;
		Map<String, String> userMap = new HashMap<String, String>();
		
		for(Message m : convo.messages)
		{
			if(userMap.containsKey(m.senderId))
			{
				m.senderId = userMap.get(m.senderId);
			}
			else
			{
				userMap.put(m.senderId, "User " + newUser);
				m.senderId = "User " + newUser;
				newUser++;
			}
		}
	}

	@Override
	public void setArgument(String argument) 
	{
		//Do Nothing
	}

	/**
	 * Return false as no argument is required
	 */
	@Override
	public boolean argumentRequired() 
	{
		return false;
	}

}
