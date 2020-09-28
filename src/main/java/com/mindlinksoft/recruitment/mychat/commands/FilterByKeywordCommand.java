package com.mindlinksoft.recruitment.mychat.commands;

import com.mindlinksoft.recruitment.mychat.Conversation;

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
		// TODO Auto-generated method stub
		return null;
	}

}
