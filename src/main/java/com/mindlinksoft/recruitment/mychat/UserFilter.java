package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Filters the list of messages of a conversation based on chosen user.
 */
public class UserFilter {
	
	
	/**
	 * The filter option.
	 */
	public String option;
	
	/**
	 * The senderId whose messages will not be filtered
	 */
	public String senderId;
	
    /**
     * Initialises a new instance of the UserFilter class
     * @param option The array containing the filter option and the senderId
     */
    public UserFilter(String[] option) {
    	this.option = option[0];
    	this.senderId = option[1];
    }

    /**
     * Filters the messages in a given Conversation object to remove messages 
     * not sent by the senderId
     * @param Conversation object to be filtered
     * @return New Conversation object with filtered messages
     */
    public Conversation filterMessages(Conversation convo) {
    	List<Message> filteredMessages = new ArrayList<Message>();
    	String conversationName = convo.name;
    	
    	for (Message m : convo.messages) {
    		if (m.senderId.contains(senderId)) {
    			filteredMessages.add(m);
    		}
    	}
    	return new Conversation(conversationName, filteredMessages);
    }
    
}
