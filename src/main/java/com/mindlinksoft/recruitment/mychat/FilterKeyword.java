package com.mindlinksoft.recruitment.mychat;

import java.util.Iterator;

/**
 * Concrete filter removing all messages not containing the specified keyword
 * from a conversation (case insensitive, will not trim the keyword before
 * searching for it in the {@link Message} instance)*/
public class FilterKeyword implements ConversationFilter {

	private String keyword;

	/**
	 * @param keyword the keyword that messages must contain to not be removed*/
	FilterKeyword(String keyword) {
		this.keyword = keyword;

	}

	@Override
	public void apply(Conversation conversation) {
		
		Iterator<Message> itr = conversation.messages.iterator(); 
		
		while(itr.hasNext()) {
			Message next = itr.next();
			
			//remove message if substring does not match content
			if(!next.getContent().toLowerCase().matches("(.*)" + 
					keyword.toLowerCase() + "(.*)")) 
				itr.remove();
		}		

	}

}

