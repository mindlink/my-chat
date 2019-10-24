package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Filters the list of messages of a conversation based on chosen keyword.
 */
public class KeywordFilter {
	
	/**
	 * The filter option.
	 */
	public String option;
	
	/**
	 * The keyword included in the messages that will not be filtered
	 */
	public String keyword;
	
    /**
     * Initialises a new instance of the keywordFilter class
     * @param option The array containing the filter option and the keyword
     */
    public KeywordFilter(String[] option) {
    	this.option = option[0];
    	this.keyword = option[1];
    }
	
    /**
     * Filters the messages in a given Conversation object to remove messages 
     * not containing the keyword
     * @param convo Conversation object to be filtered
     * @return New Conversation object with filtered messages
     */
    public Conversation filterMessages(Conversation convo) {
    	List<Message> filteredMessages = new ArrayList<Message>();
    	String conversationName = convo.name;
    	
    	for (Message m : convo.messages) {
    		if (m.content.contains(keyword)) {
    			filteredMessages.add(m);
    		}
    	}
    	return new Conversation(conversationName, filteredMessages);
    }
}
