/**
 * 
 */
package com.mindlinksoft.recruitment.mychat.features;

import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Chat Feature to filter messages in a {@link Conversation} by a specified keyword
 *
 */
public class KeywordFilterFeature implements ChatFeature 
{

	private String keyword = "";
	
	/**
	 * Not applicable
	 */
	@Override
	public void applyMessageFeature(Message msg) 
	{
		//Do Nothing
	}

	/**
	 * Recreate conversation with only messages containing specified keyword
	 * @return {@link Conversation} with only messages containing specified keyword
	 */
	@Override
	public void applyConversationFeature(Conversation convo) 
	{
		ArrayList<Message> filteredMessages = new ArrayList<Message>();
		for(Message m : convo.messages)
		{
			if(m.content.contains(keyword))
			{
				filteredMessages.add(m);
			}
		}
		convo.messages = filteredMessages;
	}

	/**
	 * Set the keyword to filter the messages using
	 */
	@Override
	public void setArgument(String argument) 
	{
		keyword = argument;
	}

	/**
	 * Return true as a keyword is required as an argument for this feature
	 */
	@Override
	public boolean argumentRequired() 
	{
		return true;
	}

}
