package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class ConversationEditor {
	/**
     * Edits the given conversation using the given preferences
     * @param conversation The conversation to be Edited
     * @param preferences The {@link Preferences} object that determines what edits are to be made to the conversation
     * @return
     */
    public static Conversation editConversation(Conversation conversation, Preferences preferences) {
    	//Creates a new conversation with a report
        //Generate report first so it isn't affected by any filters or blacklists
        //Can easily be moved later to generate the report after filtering if needed
        if(preferences.report) {
        	conversation = new Conversation(conversation.name,conversation.messages,ConversationEditor.createReport(conversation));
        	System.out.println("Report Generated");
        }
        
        
        //Filter by user
                
        if(preferences.userFilter != null) {
        	System.out.println("Filtered by User: " + preferences.userFilter);
        	ConversationFilter filter = new UserFilter();
        	conversation = filter.filter(conversation, preferences.userFilter);
        }
        
        //Filter by keyword before writing to JSON
        
        if(preferences.keywordFilter != null) {
        	System.out.println("Filtered by Keyword: " + preferences.keywordFilter);
        	ConversationFilter filter = new KeywordFilter();
        	conversation = filter.filter(conversation, preferences.keywordFilter);
        }
        
        
        //Hide BlackListed Words
        if(preferences.blacklist != null) {
        	ConversationRedacter redacter = new ConversationRedacter();
        	System.out.print("BlackListed words: ");
        	preferences.blacklist.forEach((word) -> {
        		System.out.print(word + "/");
        	});
        	System.out.println();
        	conversation = redacter.blacklistConversation(conversation, preferences.blacklist);
        }
        
    	return conversation;
    }
    
    /**
     * Generates a report about the given {@code conversation}
     * @param conversation The conversation to be reported
     * @return The collection of {@link Report} objects that make up the report
     */
    public static Collection<Report> createReport(Conversation conversation){
    	
    	
    	System.out.println("******************** CREATING REPORT ********************");

    	
    	ArrayList<Report> activity = new ArrayList<Report>();
    	
    	
    	Iterator<Message> messageIterator = conversation.messages.iterator();
    
    	

    	while(messageIterator.hasNext()) {
    		Message message = messageIterator.next();
    		
    		System.out.print("User: " + message.senderId);
    		
    		Iterator<Report> reportIterator =  activity.iterator();
    		
        	Boolean found = false;
    		
    		while(reportIterator.hasNext()) {
    			Report report = reportIterator.next();
    			
    			if(message.senderId.contentEquals(report.sender)) {
    				report.count++;
    				found = true;
    				break;
    			}
    		}
    		
    		System.out.println(" found: " + found);
    		
    		if(!found) {
    			Report newSender = new Report(message.senderId,1);
    			activity.add(newSender);
    		}
    	}
    	
    	System.out.println();

    	
    	//Sorting the Report in Descending order
    	
    	Collections.sort(activity);
    	Collections.reverse(activity);

    	return activity;
    }
}
