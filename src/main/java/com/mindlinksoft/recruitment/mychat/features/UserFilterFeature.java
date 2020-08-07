package com.mindlinksoft.recruitment.mychat.features;

import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Chat Feature to filter messages in a {@link Conversation} by a specified user
 *
 */
public class UserFilterFeature implements ChatFeature
{
	private String user = "";

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

	/**
	 * Set the user to filter messages using
	 */
	@Override
	public void setArgument(String argument) 
	{
		this.user = argument;
	}

	/**
	 * Returns true as a user is required as an argument for this feature 
	 */
	@Override
	public boolean argumentRequired() 
	{
		return true;
	}

}
