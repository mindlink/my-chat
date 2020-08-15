/**
 * 
 */
package com.mindlinksoft.recruitment.mychat.features;

import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;

/**
 * Chat Feature to filter messages in a {@link Conversation} by a specified keyword
 *
 */
public class KeywordFilterFeature implements ChatFeature 
{

	public String keyword = "";
	
	public KeywordFilterFeature(String argument)
	{
		keyword = argument;
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
}
