package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.Iterator;


/**
 * Implements the ConversationFilter interface to create a filter for users 
 *
 */
public class UserFilter implements ConversationFilter {
	
	/**
     * Filters the given {@code conversation} by the given {@code userFilter}.
     * @param conversation The conversation to be filtered.
     * @param userFilter The user to filter by.
     * @return A new {@link Conversation} with filtered messages
     */
	@Override
	public Conversation filter(Conversation conversation, String filter) {

    	Collection<Message> filteredMessages = conversation.messages;
    	Iterator<Message> messageIterator = filteredMessages.iterator();
    	
    	while(messageIterator.hasNext()) {
    		Message message = messageIterator.next();

    		
    		//If not the user then remove from the conversation
    		if(!message.senderId .equals(filter)) {
//    			System.out.println(message.senderId);
    			messageIterator.remove();
    		}
    	}
    	
		return new Conversation(conversation.name, filteredMessages, conversation.activity);
    	
	}


}
