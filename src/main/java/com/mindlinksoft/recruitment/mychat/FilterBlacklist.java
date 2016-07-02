package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;

/**
 * Concrete filter blacklisting a set of words from a conversation*/
public class FilterBlacklist implements ConversationFilter {
	
	private String[] blacklist;
	
	/**
	 * @param blacklist the array of words to blacklist*/
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
	
	/**
	 * Sub-procedure blacklisting the specified word from the specified
	 * collection of messages.
	 * 
	 * @param word the word to blacklist
	 * @messages the {@link Collection} object storing the {@link Message} 
	 * instances from which the word is to be redacted*/
	private void blacklistWord(String word, Collection<Message> messages) {
		for(Message message : messages) {
			message.content = message.content.replaceAll("(?i)" + word, 
					"*redacted*");
		}
	}
}
