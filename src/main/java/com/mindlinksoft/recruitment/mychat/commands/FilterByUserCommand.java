package com.mindlinksoft.recruitment.mychat.commands;

import com.mindlinksoft.recruitment.mychat.Conversation;

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
		// TODO Auto-generated method stub
		return null;
	}

}
