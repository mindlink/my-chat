package com.mindlinksoft.recruitment.mychat.conversation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the model of a conversation.
 */
public final class Conversation{
    /**
     * The name of the conversation.
     */
    private String name;

    /**
     * The messages in the conversation.
     */
    private Collection<Message> messages;
    
    private List<UserActivity> report;

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
     * Gets the messages in the conversation.
     */
	public Collection<Message> getMessages() {
		return messages;
	}


	/**
	 * Gets the name of the conversation.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * generates the conversation report
	 */
	public void generateReport() {
		HashMap<String, Integer> userMessages = new HashMap<String, Integer>();
		for (Message m : messages){
			String sender = m.getSenderId();
			if (userMessages.containsKey(sender)) {
				int msgs = userMessages.get(sender);
				userMessages.put(sender, msgs + 1);
			}
			else userMessages.put(sender, 1);
		}
		report = new ArrayList<UserActivity>();
		for (String user : userMessages.keySet()) {
			report.add(new UserActivity(user, userMessages.get(user)));
		}
		
		// sort by highest message count
		Collections.sort(report);
		Collections.reverse(report);
	}

	/**
	 * gets the conversation report
	 */
	public List<UserActivity> getReport() {
		return report;
	}
	
}
