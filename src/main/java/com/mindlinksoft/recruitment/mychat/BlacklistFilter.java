package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Filters the words in the messages by replacing those from the blacklist.
 */
public class BlacklistFilter {
	
	/**
	 * The filter option.
	 */
	public String option;
	
	/**
	 * The keyword included in the messages that will not be filtered
	 */
	public String[] blacklist;
	
    /**
     * Initialises a new instance of the BlacklistFilter class
     * @param option The array containing the filter option and the blacklist
     */
    public BlacklistFilter(String[] option) {
    	this.option = option[0];
    	this.blacklist = Arrays.copyOfRange(option, 1, option.length);
    }
	
    /**
     * Filters the messages in a given Conversation object to remove words in
     * the blacklist from messages
     * @param convo Conversation object to be filtered
     * @return New Conversation object with filtered messages
     */
    public Conversation filterMessages(Conversation convo) {
    	List<Message> filteredMessages = new ArrayList<Message>();
    	String conversationName = convo.name;
    	
    	for (Message m : convo.messages) {
    		String newContent = m.content;
    		for (String word : blacklist) {
				if (newContent.toLowerCase().contains(word.toLowerCase())) {
					newContent = newContent.replaceAll("(?i)" + word, "*redacted*");
				}
    		}
			filteredMessages.add(new Message(m.timestamp, m.senderId, newContent));
    	}
    	return new Conversation(conversationName, filteredMessages);
    }
}
