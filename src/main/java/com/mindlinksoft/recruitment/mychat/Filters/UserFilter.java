package com.mindlinksoft.recruitment.mychat.Filters;

import java.util.ArrayList;
import java.util.List;
import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Filters the list of messages of a conversation based on chosen user.
 * Extends abstract class Filter.
 */
public class UserFilter extends Filter{
	
	/**
	 * The senderId whose messages will not be filtered
	 */
	public String senderId;
	
    /**
     * Initialises a new instance of the UserFilter class
     * @param option The array containing the filter option and the senderId
     */
    public UserFilter(String[] option) {
    	super(option);
    	this.senderId = option[1];
    }

    /**
     * Filters the messages in a given Conversation object to remove messages 
     * not sent by the senderId
     * @param convo Conversation object to be filtered
     * @return New Conversation object with filtered messages
     */
    @Override
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
