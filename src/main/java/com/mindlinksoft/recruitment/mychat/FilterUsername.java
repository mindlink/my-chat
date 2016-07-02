package com.mindlinksoft.recruitment.mychat;

import java.util.Iterator;

public class FilterUsername implements ConversationFilter {

	private String username;

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
