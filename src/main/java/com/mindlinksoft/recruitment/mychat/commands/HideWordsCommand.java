package com.mindlinksoft.recruitment.mychat.commands;

import com.mindlinksoft.recruitment.mychat.Conversation;

/**
 * Command for hiding/redacting words from messages
 */
public class HideWordsCommand implements IConversationExportCommand{
	
	private String[] words;
	
	public HideWordsCommand(String[] words) {
		this.words = words;
	}

	@Override
	public Conversation doCommand(Conversation conversation) {
		// TODO Auto-generated method stub
		return null;
	}

}
