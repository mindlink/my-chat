package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConversationFilter {
	
	/**
	 * Initialise as null
	 */
	private String user = null;
	private String keyword = null;
	private String[] blacklist = null;
	
	/**
	 * Our replacement string for blacklisted words
	 */
	private final String replacement = "*redacted*";
	
	/**
	 * @param parsedUser Parsed user string from command line
	 * @param parsedKeyword Parsed keyword string from command line
	 * @param parsedBlacklist Parsed blacklist string array from command line
	 */
	public ConversationFilter(String parsedUser, String parsedKeyword,
			String[] parsedBlacklist) {
		this.user = parsedUser;
		this.keyword = parsedKeyword;
		this.blacklist = parsedBlacklist;
	}
	
	/**
	 * Filter the selected conversation
	 * @param conv The conversation to filter
	 * @return	A filtered conversation
	 */
	public Conversation filterConversation(Conversation conv) {

	    List<Message> messages = new ArrayList<Message>();
	    
	    /**
	     * Check for keywords and/or users
	     */
	    if (user != null || keyword != null) {
	    	System.out.println("FILTERS --- User: " + user + "  Keyword: " + keyword);
	    	System.out.print("Filtering conversation...");
			for (Message m : conv.messages) {
				if ((user != null && keyword != null) && (m.senderId.equalsIgnoreCase(user) && m.content.contains(keyword)))
					messages.add(m);
				if ((user != null && keyword == null) && m.senderId.contentEquals(user))
					messages.add(m);
				if ((user == null && keyword != null) && m.content.contains(keyword))
					messages.add(m);
			}
			System.out.println("DONE");
	    } else {
	    	messages = (List<Message>) conv.messages;
	    }
	    
	    /**
	     * Check for blacklisted words
	     */
	    if (blacklist != null) {
	    	System.out.print("Removing blacklisted words...");
	    	for (Message m : messages) 
	    		m.content = redactBlacklisted(m.content, blacklist);
	    	System.out.println("DONE");
	    }
		Conversation filteredConv = new Conversation(conv.name, messages);
		return filteredConv;
	}
	
	private String redactBlacklisted(String message, String[] blacklist) {
		for (String blacklisted : blacklist) {
			message = message.replaceAll("\\b"+blacklisted+"\\b", replacement);
			}
		return message;
	}
	
	/**
	 * toString override for testing
	 */
	public String toString() {
		return user + " " + keyword + " " + Arrays.toString(blacklist);  
	}  
	
}
