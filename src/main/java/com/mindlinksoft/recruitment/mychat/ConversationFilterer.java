/**
 * 
 */
package com.mindlinksoft.recruitment.mychat;

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
     * @return The {@link Conversation} after filtering
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation filterConversationByUser(Conversation conversation, String userFilter) throws Exception {
    	
    	Iterator<Message> messageIterator = conversation.messages.iterator();
    	
    	while(messageIterator.hasNext()) {
    		Message message = messageIterator.next();

    		
    		//If not the user then remove from the conversation
    		if(!message.senderId .equals(userFilter)) {
//    			System.out.println(message.senderId);
    			messageIterator.remove();
    		}
    	}
    	
		return conversation;
    	
    }
    
    
    /**
     * Filters the given {@code conversation} by the given {@code keyword}.
     * @param conversation The conversation to be filtered.
     * @param keyword The word to filter by.
     * @return The {@link Conversation} after filtering
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation filterConversationByKeyword(Conversation conversation, String keyword) throws Exception {
    	
    	Iterator<Message> messageIterator = conversation.messages.iterator();
    	
    	while(messageIterator.hasNext()) {
    		Message message = messageIterator.next();

    		
    		//If not the user then remove from the conversation
    		if(!message.content.contains(keyword)) {
//    			System.out.println(message.senderId);
    			messageIterator.remove();
    		}
    	}
    	
		return conversation;
    	
    }
}
