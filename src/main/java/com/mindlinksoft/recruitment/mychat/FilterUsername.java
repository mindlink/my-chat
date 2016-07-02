package com.mindlinksoft.recruitment.mychat;

import java.util.Iterator;

/**
 * Concrete filter removing all messages that were not sent by a user from a 
 * conversation. Case sensitive.*/
public class FilterUsername implements ConversationFilter {

	private String username;
	
	/**
	 * @param username message sender must correspond to this username (case
	 * sensitively) in order to not be removed from the conversation*/
	FilterUsername(String username) {
		this.username = username;
	}

	@Override
	public void apply(Conversation conversation) {
		Iterator<Message> itr = conversation.messages.iterator();
		
		while(itr.hasNext()) {
			Message next = itr.next();
			if(username.compareTo(next.senderId) != 0) {
				itr.remove();
			}
		}  

	}

}
