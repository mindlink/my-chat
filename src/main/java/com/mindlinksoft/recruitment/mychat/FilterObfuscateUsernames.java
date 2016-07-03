package com.mindlinksoft.recruitment.mychat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class FilterObfuscateUsernames implements ConversationFilter {

	@Override
	public void apply(Conversation conversation) {
		Set<String> userSet = UserSetPopulator.populateUserSet(conversation);
		Map<String, String> hashedUsernames = populateMap(userSet);
		Set<Map.Entry<String, String>> entrySet = hashedUsernames.entrySet();
		
		for(Map.Entry<String, String> entry : entrySet) {
			Obfuscator.obfuscateWord(entry.getKey(), entry.getValue(), 
					conversation.messages);
			
			obfuscateSender(entry, conversation.messages);
		}
	}
	
	private Map<String, String> populateMap(Set<String> userSet) {
		
		Map<String, String> hashedUsernames = 
				new HashMap<String, String>(userSet.size() + 1, 1.0f);
		
		for(String username : userSet) {
			username = username.toLowerCase();
			hashedUsernames.put(username, "'user" + username.hashCode() + "'");
		}
		
		return hashedUsernames;
	}

	private void obfuscateSender(Map.Entry<String, String> entry, 
			List<Message> messages) {
		
		for(Message message : messages) {
			if(message.senderId.compareTo(entry.getKey()) == 0)
				message.senderId = entry.getValue();
		}
	}
}
