package com.mindlinksoft.recruitment.mychat.features;

import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;

/**
 * Chat Feature to filter messages in a {@link Conversation} by a specified user
 *
 */
public class UserFilterFeature implements ChatFeature
{
	public String user = "";
	
	public UserFilterFeature(String argument)
	{
		this.user = argument;
	}

	/**
	 * Not applicable
	 */
	@Override
	public void applyMessageFeature(Message msg) 
	{
		//Do Nothing
	}

	/**
	 * Recreate conversation with only messages from specified user
	 * @return {@link Conversation} with only messages from specified user
	 */
	@Override
	public void applyConversationFeature(Conversation convo) 
	{
		ArrayList<Message> filteredMessages = new ArrayList<Message>();
		for(Message m : convo.messages)
		{
			if(m.senderId.equals(user))
			{
				filteredMessages.add(m);
			}
		}
		convo.messages = filteredMessages;
	}
}
