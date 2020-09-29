package com.mindlinksoft.recruitment.mychat.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Command for filtering messages by user
 */
public class FilterByUserCommand implements IConversationExportCommand{
	
	/**
	 * the user to filter by
	 */
	private String user;
	
	public FilterByUserCommand(String user) {
		this.user = user;
	}

	@Override
	public Conversation doCommand(Conversation conversation) {
		Collection<Message> allMessages = conversation.getMessages();
		Collection<Message> filteredMessages = new ArrayList<Message>();
		
		for(Message msg : allMessages) {
			if(msg.getSenderId().equals(user)) filteredMessages.add(msg);
		}
		
		return new Conversation(conversation.getName(), filteredMessages);
	}
	

	public String getUser() {
		return user;
	}


}
