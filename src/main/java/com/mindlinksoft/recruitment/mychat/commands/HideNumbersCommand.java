package com.mindlinksoft.recruitment.mychat.commands;

import java.util.ArrayList;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.conversation.Message;

public class HideNumbersCommand implements IConversationExportCommand{

	private String cardMatchRegex = "\\d{4}[ -]?\\d{4}[ -]?\\d{4}[ -]?\\d{4}";
	
	private String phoneMatchRegex = "(((\\+\\d\\d)?[ -]?(\\(0\\))?[ -]?)|(0))([ -]??[0-9]{3,4}){3}";
	private final String redactedStr = "*redacted*";
			
	@Override
	public Conversation doCommand(Conversation conversation) {
		Collection<Message> allMessages = conversation.getMessages();
		Collection<Message> filteredMessages = new ArrayList<Message>();
		
		for(Message msg : allMessages) {
			String filteredMsg = msg.getContent()
						.replaceAll(cardMatchRegex, redactedStr)
						.replaceAll(phoneMatchRegex, redactedStr);
			filteredMessages.add(new Message(msg.getTimestamp(), msg.getSenderId(), filteredMsg));
		}
		
		return new Conversation(conversation.getName(), filteredMessages);
	}

}
