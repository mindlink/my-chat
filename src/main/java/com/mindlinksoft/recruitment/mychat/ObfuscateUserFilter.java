package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Obfuscation filter masks senderId with anonymous handle.
 */
public class ObfuscateUserFilter implements Filter {

	/**
	 * Apply obfuscation filter to conversation.
	 * @param conversation.
	 * @return conversation.
	 */
	public Conversation apply(Conversation c) {
		List<Message> filteredMessages = new ArrayList<Message>();
		Map<String, String> idMap = new HashMap<String, String>();
		
		for(Message m: c.getMessages()) {	
			String id = m.getSenderId().toLowerCase();
			String newId;
			
			if(idMap.containsKey(id)) {
				newId = idMap.get(id);
			}
			else {
				int userIndex = idMap.size() + 1;
				newId = Const.USER_MASK + " " + userIndex;
				
				idMap.put(id, newId);
			}
			
			Message obfuscatedMessage = new Message(m.getTimestamp(),idMap.get(id), m.getContent());
			filteredMessages.add(obfuscatedMessage);
		}
		
		return new Conversation(c.getName(), filteredMessages);
	}
}
