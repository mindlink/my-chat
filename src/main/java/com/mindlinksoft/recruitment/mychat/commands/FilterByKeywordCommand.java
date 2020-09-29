package com.mindlinksoft.recruitment.mychat.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Command for filtering messages by keyword
 */
public class FilterByKeywordCommand implements IConversationExportCommand{

	/**
	 * the keyword to filter by
	 */
	private String keyword;
	
	public FilterByKeywordCommand(String kw) {
		this.keyword = kw;
	}
	
	@Override
	public Conversation doCommand(Conversation conversation) {
		Collection<Message> allMessages = conversation.getMessages();
		Collection<Message> filteredMessages = new ArrayList<Message>();
		
		for(Message msg : allMessages) {
			if(msg.getContent().toLowerCase().contains(keyword.toLowerCase())) 
				filteredMessages.add(msg);
		}
		
		return new Conversation(conversation.getName(), filteredMessages);
	}
	
	public String getKeyword() {
		return keyword;
	}

}
