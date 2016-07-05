package com.mindlinksoft.recruitment.mychat;

import java.util.Set;
import java.util.TreeSet;

/**
 * Responsible for knowing the logic to extract a set of usernames from a 
 * {@link Conversation}.*/
class UserSetPopulator {
	
	/**Populates a set of users based on the content of the conversation
	 * @param conversation the conversation from which to extract the set of 
	 * users*/
	static Set<String> populateUserSet(Conversation conversation) {
		Set<String> userSet = new TreeSet<String>();
		
		for(Message message : conversation.messages) {
			userSet.add(message.getSenderId());
		}
		
		return userSet;
	}
}
