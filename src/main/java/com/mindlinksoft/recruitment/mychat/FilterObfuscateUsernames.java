package com.mindlinksoft.recruitment.mychat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Concrete filter removing all plain usernames from the conversation and 
 * replacing them with a hashed version. */
class FilterObfuscateUsernames implements ConversationFilter {

	Set<String> userSet;
	Set<Map.Entry<String, String>> entrySet;
	
	@Override
	public void apply(Conversation conversation) {
		userSet = UserSetPopulator.populateUserSet(conversation);
		entrySet = populateMap(userSet).entrySet();
		
		obfuscateAllReferences(conversation);
	}
	
	/**
	 * Populates a map of usernames -> hashed username*/
	private Map<String, String> populateMap(Set<String> userSet) {
		
		Map<String, String> hashedUsernames = 
				new HashMap<String, String>(userSet.size() + 1, 1.0f);
		
		for(String username : userSet) {
			username = username.toLowerCase();
			hashedUsernames.put(username, "'user" + username.hashCode() + "'");
		}
		
		return hashedUsernames;
	}
	
	/**
	 * Loops through set of map entries and asks {@link Obfuscator} to replace
	 * all references in the chat messages.*/
	private void obfuscateAllReferences(Conversation conversation) {
		for(Map.Entry<String, String> entry : entrySet) {
			
			Obfuscator.obfuscateWord(entry.getKey(), entry.getValue(), 
					conversation.messages);
			
			Obfuscator.obfuscateSender(entry.getKey(), entry.getValue(),
					conversation.messages);
		}
	}
}
