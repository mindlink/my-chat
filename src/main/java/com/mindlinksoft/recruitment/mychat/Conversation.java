package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
	
    /**
     * The name of the conversation.
     */
    public String name;

    /**
     * The messages in the conversation.
     */
    public Collection<Message> messages;
    
    /**
     * The report containing the occurance of each value
     */

    public List<UserCount> userFrequency;
    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages, ArrayList<String> users) {

    	ReportGenerator report = new ReportGenerator();
    	
        this.name = name;
        this.messages = messages;
        
        for (String user : users) {
        	report.addUser(user);
        }
        
        this.userFrequency = report.sortValues();
    }
}
