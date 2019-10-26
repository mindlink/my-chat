package com.mindlinksoft.recruitment.mychat.Filters;

import java.util.ArrayList;
import java.util.List;
import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Filters the list of messages of a conversation based on chosen keyword.
 * Extends abstract class Filter.
 */
public class KeywordFilter extends Filter {
	
	/**
	 * The keyword included in the messages that will not be filtered
	 */
	public String keyword;
	
    /**
     * Initialises a new instance of the keywordFilter class
     * @param option The array containing the filter option and the keyword
     */
    public KeywordFilter(String[] option) {
    	super(option);
    	this.keyword = option[1];
    }
	
    /**
     * Filters the messages in a given Conversation object to remove messages 
     * not containing the keyword
     * @param convo Conversation object to be filtered
     * @return New Conversation object with filtered messages
     */
    @Override
    public Conversation filterMessages(Conversation convo) {
    	List<Message> filteredMessages = new ArrayList<Message>();
    	String conversationName = convo.name;
    	
    	for (Message m : convo.messages) {
    		if (m.content.toLowerCase().contains(keyword.toLowerCase())) {
    			filteredMessages.add(m);
    		}
    	}
    	return new Conversation(conversationName, filteredMessages);
    }
}
