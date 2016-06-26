package com.mindlinksoft.recruitment.mychat;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defines acceptable option parameters (other than the mandatory file paths)
 * and operations that help identify the type of option they represent to both
 * the parser and the filtering units.
 * 
 * Instantiable to allow multiple conversations to be filtered at once.
 * */
final class Filterer {

	/**
	 * Instance-invariant variables and methods start here:
	 * */
	final static Logger LOGGER = Logger.getLogger("com.mindlinksoft.recruitment.mychat");
	/**
	 * The array of acceptable option values
	 * */
	final static char [] set = {
			'u',
			'k',
			'b'
	};
	
	/**
	 * @return true Where the option is recognized as a flag, false otherwise
	 * */
	static boolean isFlag(char c) {
		return false;
	}
	
	/**
	 * @return true Where the option can accept more than one parameter value,
	 * false otherwise
	 * */
	static boolean mayBeList(char c) {
		switch(c) {
		case 'b':
			return true;
			default:
				return false;
		}
	}
	
	/**
	 * @return true Where the option must be followed by one parameter value, 
	 * false otherwise
	 * */
	static boolean needsValue(char c) {
		switch(c) {
		case 'u':
		case 'k':
			return true;
			default:
				return false;
		}
	}
	
	/**
	 * Instance variables and methods start here:
	 * */
	
	private Conversation conversation;
	
	/**
	 * Constructor that requires filterer instances to be associated with 
	 * a {@link Conversation} object.
	 * */
	public Filterer(Conversation conversation) {
		this.conversation = conversation;
	}
	
	 /**
     * Applies filter specified as key-value pair.
     * */
    public void apply(char option, String value) {
    	switch(option) {
    	case 'u':
    		filterByUserId(value);
    		break;
    	case 'k':
    		filterBySubstring(value);
    		break;
    	case 'b':
    		String [] words = value.split("\\s+");
    		for(String word : words)
    			blacklist(word);
    		break;
    	default:
    		LOGGER.log(Level.WARNING, "Exporter configuration option not "
    				+ "recognized, ignoring.");
    	}
    }
    
    /**
     * Filters conversation.messages in this conversation by userId (keeps only matched
     * userId)
     * */
	public void filterByUserId(String userId/*, String...moreIds*/) {
    	LOGGER.log(Level.FINE, "Filtering messages by username '" +
    			userId + "'");
    	for(Iterator<Message> itr = conversation.messages.iterator(); 
    												itr.hasNext();) {
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
	 * Filters conversation.messages by matching substrings of message content with input
	 * parameter. Case sensitive.
	 * @param substring The substring representing the search token
	 * */
	public void filterBySubstring(String substring) {
		LOGGER.log(Level.FINE, "Filtering messages by keyword '" +
    			substring + "'");
		for(Iterator<Message> itr = conversation.messages.iterator(); 
													itr.hasNext();) {
    		Message next = itr.next();
    		//remove message if substring does not match content
    		if(!next.content.matches("(.*)" + substring + "(.*)")) 
    			itr.remove();
		}
	}

	/**
	 * Replaces all matches of parameter word with "*redacted*" in the content
	 * of each message in the conversation. Trims leading/trailing blanks, 
	 * case insensitive (assuming when you want to blacklist a word you want to 
	 * blacklist it whether or not it is capitalized in any part).
	 * @param word An string of alphanumerical characters
	 * @throws IllegalArgumentException when the word parameter is not 
	 * entirely made of alphanumeric characters
	 * */
	public void blacklist(String word) throws IllegalArgumentException {
		LOGGER.log(Level.FINE, "Removing occurrences of '" + word + "' from "
				+ "all messages.");
		word = word.trim();
		if(!word.matches("([0-9]|([a-z]|[A-Z]))+"))
				throw new IllegalArgumentException("Expected an alphanumeric " +
													"word but got \'" + word +
													"\'");
		
		//build regex to match any combination of upper or lower case characters
		String regex = "";
		for(char c : word.toCharArray()) {
			regex += "(" + Character.toLowerCase(c) + "|" + 
							Character.toUpperCase(c) + ")";
		}
		
		for(Message message : conversation.messages) {
			message.content = message.content.replaceAll(regex, "*redacted*");
		}
		
	}
}
