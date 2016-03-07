package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

/**
 * Represents the model of a conversation.
 */
public final class Conversation
{
    private String name;
    private ArrayList<Message> messages;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, ArrayList<Message> messages)
    {
        this.name = name;
        this.messages = messages;
    }
    
    public String GetConvoName() { return this.name; }
    public ArrayList<Message> GetMessages() { return this.messages; }
}
