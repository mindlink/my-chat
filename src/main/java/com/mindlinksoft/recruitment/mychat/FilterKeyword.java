package com.mindlinksoft.recruitment.mychat;

import java.util.Iterator;

public class FilterKeyword implements ConversationFilter {

	private String keyword;

	FilterKeyword(String keyword) {
		this.keyword = keyword;

	}

	@Override
	public void apply(Conversation conversation) {
		
		Iterator<Message> itr = conversation.messages.iterator(); 
		
		while(itr.hasNext()) {
			Message next = itr.next();
			
			//remove message if substring does not match content
			if(!next.content.toLowerCase().matches("(.*)" + 
					keyword.toLowerCase() + "(.*)")) 
				itr.remove();
		}

	}

}

