package com.mindlinksoft.recruitment.mychat.features;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;

/**
 * Chat Feature to redact specific words in a message based on a blacklist
 *
 */
public class BlacklistFeature implements ChatFeature 
{
	/**
	 * Holds list of words to be redacted
	 */
	public String[] blacklist;
	
	public BlacklistFeature(String argument)
	{
		blacklist = argument.split(",");
		//Get rid of spaces
		for(String s : blacklist)
		{
			if(s.equals(" "))
			{
				s = "";
			}
		}
	}
	
	/**
	 * Recreate message with redacted words replaced with "\*redacted\*"
	 * @return Redacted {@link Message}
	 */
	@Override
	public Message applyMessageFeature(Message msg) 
	{
		String content = msg.content;
		for(int i = 0; i < blacklist.length; i++)
		{
			if(msg.content.contains(blacklist[i]))
			{
				content = content.replace(blacklist[i], "*redacted*");
			}
		}
		
		return new Message(msg.timestamp, msg.senderId, content);
	}

	/**
	 * Not applicable
	 */
	@Override
	public Conversation applyConversationFeature(Conversation convo) 
	{
		return convo;
	}
}
