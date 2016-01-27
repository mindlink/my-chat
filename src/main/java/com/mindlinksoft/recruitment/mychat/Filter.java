package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Represents a helper class to filter conversations
 * @author Carl Visser
 */
public class Filter {
    /**
     * Default Constructor
     */
    public Filter(){
        
    }
    
    /**
     * Represents a helper to retract a word from a (@link Conversation).
     * @param retractedWord The word to retract.
     * @param conversation The conversation to be retracted from.
     * @return 
     */
    public Conversation retractWords(String [] retractedWords, Conversation conversation)
    {
        for(Message message : conversation.getMessages())
        {
            for(int i=0;i<retractedWords.length;i++)
                message.setContent(message.getContent().replaceAll(retractedWords[i], "*redacted*"));
            
        }
        return conversation;
    }
    /**
     * Represents a helper to filter by keyWord removing the ones that do not contain it
     * @param keyWord The key word to filter by
     * @param conversation The conversation to filter
     * @return The amended conversation
     */
    public Conversation filterByKeyWords(String []keyWords, Conversation conversation)
    {
        LinkedHashSet <Message> messages = new LinkedHashSet<Message>();
        for(Message message : conversation.getMessages())
        {
            for(int i=0;i<keyWords.length;i++)
            {
                if(message.getContent().contains(keyWords[i]))
                    messages.add(message);
            }
        }
        
        return new Conversation(conversation.getName(),messages);
    }
    
    /**
     * Represents a helper to filter messages by a username removing the ones that were not sent by the specified user
     * @param usernames The usernames to filter by.
     * @param conversation The conversation to filter.
     * @return The amended conversation.
     */
    public Conversation filterByUsername(String [] usernames, Conversation conversation){
        List<Message> messages = new ArrayList<>();
        for(Message message : conversation.getMessages())
        {
           for(int i=0;i<usernames.length;i++)
            {
                if(message.getSenderId().equalsIgnoreCase(usernames[i]))
                    messages.add(message);
            }
        }
        Collections.sort(messages);
        return new Conversation(conversation.getName(),messages);
    } 
    
    

    
}
