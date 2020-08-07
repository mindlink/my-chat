package com.mindlinksoft.recruitment.mychat.features;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

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
	
	/**
	 * Recreate message with redacted words replaced with "\*redacted\*"
	 * @return Redacted {@link Message}
	 */
	@Override
	public void applyMessageFeature(Message msg) 
	{
		for(int i = 0; i < blacklist.length; i++)
		{
			if(msg.content.contains(blacklist[i]))
			{
				msg.content = msg.content.replace(blacklist[i], "*redacted*");
			}
		}
	}

	/**
	 * Not applicable
	 */
	@Override
	public void applyConversationFeature(Conversation convo) 
	{
		//Do Nothing
	}

	/**
	 * Required to create blacklist of words to be redacted
	 * @param comma delimited string of blacklisted words
	 */
	@Override
	public void setArgument(String argument) 
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
	 * Return true as a list of words is required as an argument for this feature
	 */
	@Override
	public boolean argumentRequired() 
	{
		return true;
	}

}
