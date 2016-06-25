package com.mindlinksoft.recruitment.mychat;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    public String name;

    /**
     * The messages in the conversation.
     */
    public Collection<Message> messages;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
    
    /**
     * Filters messages in this conversation by userId (keeps only matched
     * userId)
     * @throws InstantiationException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * */
	public void filterByUserId(String userId/*, String...moreIds*/) {
    	
    	for(Iterator<Message> itr = messages.iterator(); itr.hasNext();) {
    		Message next = itr.next();
    		//do first argument
    		if(userId.compareTo(next.senderId) != 0) {
    			itr.remove();
//    			continue;
    		}
    		//iterate through next arguments (not asked for by requirements)
//    		for(String nextId : moreIds)
//    			if(nextId.compareTo(next.senderId) != 0) {
//    				itr.remove();
//    				continue;
//    			}
    	}   	 	
    	
    }
	
	/**
	 * Filters messages by matching substrings of message content with input
	 * parameter. Case sensitive.
	 * @param substring The substring representing the search token
	 * */
	public void filterBySubstring(String substring) {
		
		for(Iterator<Message> itr = messages.iterator(); itr.hasNext();) {
    		Message next = itr.next();
    		//remove message if substring does not match content
    		if(!next.content.matches("(.*)" + substring + "(.*)")) 
    			itr.remove();
		}
	}

	/**
	 * Replaces all matches of parameter word with "*redacted*" in the content
	 * of each message in the conversation.
	 * @throws IllegalArgumentException when the word parameter is not 
	 * entirely made of alphanumeric characters
	 * */
	public void blacklist(String word) throws IllegalArgumentException {
		
	}
}
