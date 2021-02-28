/**
 * 
 */
package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a Conversation Filterer that can filter any given Conversation.
 *
 */
public class ConversationFilterer {
	
	
    /**
     * Filters the given {@code conversation} by the given {@code userFilter}.
     * @param conversation The conversation to be filtered.
     * @param userFilter The user to filter by.
     * @return A new {@link Conversation} with filtered messages
     */
    public Conversation filterConversationByUser(Conversation conversation, String userFilter) {
    	
    	Collection<Message> filteredMessages = conversation.messages;
    	Iterator<Message> messageIterator = filteredMessages.iterator();
    	
    	while(messageIterator.hasNext()) {
    		Message message = messageIterator.next();

    		
    		//If not the user then remove from the conversation
    		if(!message.senderId .equals(userFilter)) {
//    			System.out.println(message.senderId);
    			messageIterator.remove();
    		}
    	}
    	
		return new Conversation(conversation.name, filteredMessages, conversation.activity);
    	
    }
    
    
    /**
     * Filters the given {@code conversation} by the given {@code keyword}.
     * @param conversation The conversation to be filtered.
     * @param keyword The word to filter by.
     * @return A new {@link Conversation} with filtered messages
     */
    public Conversation filterConversationByKeyword(Conversation conversation, String keyword) {
    	
    	Collection<Message> filteredMessages = conversation.messages;
    	Iterator<Message> messageIterator = filteredMessages.iterator();
    	
    	while(messageIterator.hasNext()) {
    		Message message = messageIterator.next();

    		
    		//If not the user then remove from the conversation
    		if(!message.content.contains(keyword)) {
//    			System.out.println(message.senderId);
    			messageIterator.remove();
    		}
    	}
    	
		return new Conversation(conversation.name, filteredMessages, conversation.activity);
    	
    }
}
