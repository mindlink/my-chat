/**
 * 
 */
package com.mindlinksoft.recruitment.mychat;

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
    	
      	
		return conversation;
    	
    }
    
    
    /**
     * Filters the given {@code conversation} by the given {@code keyword}.
     * @param conversation The conversation to be filtered.
     * @param keyword The word to filter by.
     * @return A new {@link Conversation} with filtered messages
     */
    public Conversation filterConversationByKeyword(Conversation conversation, String keyword) {
    	
		return conversation;
    	
    }
}
