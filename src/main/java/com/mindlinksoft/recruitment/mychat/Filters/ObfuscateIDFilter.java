package com.mindlinksoft.recruitment.mychat.Filters;

import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Replaces the senderIds of each user by UserX, where X is replaced by a number
 */
public class ObfuscateIDFilter extends Filter{

    /**
     * Initialises a new instance of the ObfuscateIDFilter class
     * @param option The array containing the filter option
     */
	public ObfuscateIDFilter(String[] option) {
		super(option);
	}

    /**
     * Filters the input conversation by replacing senderIds and returns a new
     * Conversation object with new senderIds for each person who had sent a message
     * @param convo Conversation object to be filtered
     * @return New Conversation object with filtered messages
     */
	@Override
	public Conversation filterMessages(Conversation convo) {
		List<Message> filteredMessages = new ArrayList<Message>();
		String conversationName = convo.name;
    	List<String> senderIds = new ArrayList<String>();
    	
    	for (Message m : convo.messages) {
    		String obfName = "";
    		if (!senderIds.contains(m.senderId)) {
    			senderIds.add(m.senderId);
    			obfName = "User" + senderIds.indexOf(m.senderId);
    		}
    		else {
    			obfName = "User" + senderIds.indexOf(m.senderId);
    		}
    		filteredMessages.add(new Message(m.timestamp, obfName, m.content));
    	}
    	return new Conversation(conversationName, filteredMessages);
	}

}
