package com.mindlinksoft.recruitment.mychat.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;

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
	public Message applyMessageFeature(Message msg) 
	{
		return msg;
	}

	/**
	 * Obfuscate user id's with User 0, User 1 ...etc
	 */
	@Override
	public Conversation applyConversationFeature(Conversation convo) 
	{
		int newUser = 0;
		ArrayList<Message> changed_messages = new ArrayList<Message>();
		Map<String, String> userMap = new HashMap<String, String>();
		
		for(Message m : convo.messages)
		{
			String changedId = "";
			
			if(userMap.containsKey(m.senderId))
			{
				changedId = userMap.get(m.senderId);
			}
			else
			{
				userMap.put(m.senderId, "User " + newUser);
				changedId = "User " + newUser;
				newUser++;
			}
			changed_messages.add(new Message(m.timestamp, changedId, m.content));
		}
		
		return new Conversation(convo.name, changed_messages);
	}
}
