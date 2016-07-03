package com.mindlinksoft.recruitment.mychat;


/**
 * Concrete filter blacklisting a set of words from a conversation*/
public class FilterBlacklist implements ConversationFilter {
	
	private final String REPLACEMENT = "*redacted*";
	private String[] blacklist;
	
	/**
	 * @param blacklist the array of words to blacklist*/
	FilterBlacklist(String[] blacklist) {
		this.blacklist = blacklist;
	}

	@Override
	public void apply(Conversation conversation) {
		for(String word : blacklist) {

			Obfuscator.obfuscateWord(word, REPLACEMENT, conversation.messages);
		}
	}
}
