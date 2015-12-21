package com.mindlinksoft.recruitment.mychat;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
	//variables
	/**
	 * The name of the conversation.
	 */
	private String name;

	/**
	 * The messages in the conversation.
	 */
	private Collection<Message> messages;

	//constructor
	/**
	 * Initializes a new instance of the {@link Conversation} class.
	 * @param name The name of the conversation.
	 * @param messages The messages in the conversation.
	 */
	public Conversation(String name, Collection<Message> messages) {
		this.name = name;
		this.messages = messages;
	}
	
	//getters and setters
	/**
	 * Gets name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets messages.
	 */
	public Collection<Message> getMessages() {
		return messages;
	}

	/**
	 * Sets messages.
	 */
	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}
	
	//methods
	/**
     * Keeps messages from the conversation that have been sent by a specific sender ID. Removes all others.
     * @param senderId The sender ID that will be filtered.
     */
	public void filterSenderId(String senderId){
		
		//list to keep track of Messages
		List<Message> notEqual = new ArrayList<Message>();
		
		//go through each message and add to "notEqual" if not matching the Sender ID
		for(Message e : this.messages){
			
		    if(!(e.getSenderId().equals(senderId))){
		        notEqual.add(e);
		    }
		}
		
		//logging
		System.out.println("Filtering messages from sender ID '" + senderId + "'...");
		//remove all non-matching messages from list
		this.messages.removeAll(notEqual);
		
	}

	
	/**
     * Keeps messages from the conversation that contain a certain keyword (case sensitive). Removes all others.
     * @param senderId The sender ID that will be filtered.
     */
	public void filterKeyword(String keyword) {
		
		//list to keep track of Messages
		List<Message> noMatch = new ArrayList<Message>();
				
		String regEx = ".*" + keyword + ".*";
		
		//go through each message and add to "noMatch" if not containing the keyword
		for(Message e : this.messages){
					
			if(!(e.getContent().matches(regEx))){
				noMatch.add(e);
			}

		}
		
		//remove all non-matching messages from list
		this.messages.removeAll(noMatch);
		
		//logging
		System.out.println(this.messages.size() + " message(s) containing '" + keyword + "' found!");
	}
	
	
	/**
     * Replaces the blacklisted words with "*redacted*" in the content of the messages.
     * @param blackList The words that will be blacklisted.
     */
	public void blacklist(List<String> blacklist) {
		
		for(String word :blacklist){
			
			//go through each message and replace blacklisted word in content
			for(Message e : this.messages){
					
				Pattern replace = Pattern.compile(word);
				Matcher matcher = replace.matcher(e.getContent());
				e.setContent(matcher.replaceAll("*redacted*"));
			}	
			
			System.out.println("Redacting occurences of '" + word + "'...");
		}
		

	}
	
	
}
