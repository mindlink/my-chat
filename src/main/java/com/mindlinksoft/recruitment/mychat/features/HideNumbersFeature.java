package com.mindlinksoft.recruitment.mychat.features;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Feature to hide phone numbers and credit card numbers with *redacted*
 *
 */
public class HideNumbersFeature implements ChatFeature {
	
	//Will have to add more regex to cover different types of card numbers and phone numbers
	public String patterns = "\\b(?:\\d[ -]*?){13,16}\\b|\\d{11}|\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}";

	/**
	 * Replace Phone numbers and Credit card numbers with '*redacted*'
	 */
	@Override
	public void applyMessageFeature(Message msg) 
	{
		msg.content = msg.content.replaceAll(patterns, "*redacted*");
	}

	/**
	 * Not Applicable
	 */
	@Override
	public void applyConversationFeature(Conversation convo) 
	{
		//Do Nothing

	}

	@Override
	public void setArgument(String argument) 
	{
		//Do Nothing

	}

	/**
	 * Returns false as no argument is required
	 */
	@Override
	public boolean argumentRequired() 
	{
		return false;
	}

}
