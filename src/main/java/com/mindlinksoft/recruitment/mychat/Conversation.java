package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    private final String name;

    /**
     * The messages in the conversation.
     */
    private final Collection<Message> messages;
    
    /**
     * The user report detailing number of messages sent per user. Ordered by value descending.
     */
    private final Map<String, Integer> report;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = Collections.unmodifiableList(new ArrayList<Message>(messages));
        this.report = null;
    }
    
    /**
     * Private constructor adds {@link Report} to conversation {@link Conversation}.
     * @param conversation The conversation acquiring report.
     * @param report The map to append to the {@code conversation}.
     */
    private Conversation(Conversation conversation, Map<String, Integer> report) {
    	this.name = conversation.getName();
    	this.messages = conversation.getMessages();
    	this.report = report;
    }
    
    /**
     * Gets Messages from conversation.
     * @return messages
     */
    public List<Message> getMessages() {
    	return new ArrayList<Message>(messages);
    }
    
    /**
     * Gets conversation name.
     * @return name.
     */
    public String getName() {
    	return name;
    }
    
    /**
     * Gets Report from conversation.
     * @return report.
     */
    public Map<String, Integer> getReport() {
    	return new LinkedHashMap<String, Integer>(report); //Linked preserves order for test
    }
    
    /**
     * Adds Report to conversation.
     * @param conversation Conversation to append with report.
     * @param report The user report.
     * @return Conversation.
     */
    public static Conversation addReport(Conversation conversation, Map<String, Integer> report) {
    	return new Conversation(conversation, report);
    }
    
    /**
     * Helper method to apply filters to conversation.
     * @param conversation Conversation to filter.
     * @param filters List of user-defined filters.
     * @return Conversation.
     */
    public static Conversation applyFilters(Conversation conversation, List<Filter> filters) {
    	System.out.print(Const.APPLYING_FILTERS);
    	
    	for(Filter f: filters) {
    		conversation = f.apply(conversation);
    	}
    	
    	System.out.println(Const.COMPLETE);
    	return conversation;
    }
}
