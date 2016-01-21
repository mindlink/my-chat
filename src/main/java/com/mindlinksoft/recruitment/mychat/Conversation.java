package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    //Attributes ----------------------------------------------------------------
    /**
     * The name of the conversation.
     */
    private String name;

    /**
     * The messages in the conversation.
     */
    private Collection<Message> messages;
    
    //Constructors -------------------------------------------------------------
    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
    
    //Accessors ------------------------------------------------------
    /**
     * Method to access name attribute
     * @return name
     */
    public String getName(){
        return this.name;
    }
    /**
     * Method to access messages Collection
     * @return messages
     */
    public Collection<Message> getMessages(){
        return this.messages;
    }
    
    /**
     * Method to convert Conversation to a String
     * @return String representation of a conversation
     */
    @Override
    public String toString(){
        String convo = this.name+"\n";
        for(Message message : messages)
        {
            convo+=message.getTimestamp()+" "+message.getSenderId()+" "+message.getContent()+"\n";
        }
        return convo;
    }
}//end class
