package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;

public class FilterBlacklist implements ConversationFilter {
	
	private String[] blacklist;
	
	FilterBlacklist(String[] blacklist) {
		this.blacklist = blacklist;
	}

	@Override
	public void apply(Conversation conversation) {
		for(String word : blacklist) {
			word = word.trim();
			word = word.toLowerCase();
			
			blacklistWord(word, conversation.messages);
		}
	}
	
	private void blacklistWord(String word, Collection<Message> messages) {
		for(Message message : messages) {
			message.content = message.content.replaceAll("(?i)" + word, 
					"*redacted*");
		}
	}
}
