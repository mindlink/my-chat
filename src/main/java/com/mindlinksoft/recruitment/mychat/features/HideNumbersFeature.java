package com.mindlinksoft.recruitment.mychat.features;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;

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
	public Message applyMessageFeature(Message msg) 
	{
		String content = msg.content.replaceAll(patterns, "*redacted*");
		return new Message(msg.timestamp, msg.senderId, content);
	}

	/**
	 * Not Applicable
	 */
	@Override
	public Conversation applyConversationFeature(Conversation convo) 
	{
		return convo;
	}
}
