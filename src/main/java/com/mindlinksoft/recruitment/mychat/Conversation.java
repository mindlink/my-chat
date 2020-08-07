package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Map;
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
    public ArrayList<Message> messages;
    
    public Map<String, Integer> userActivity;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, ArrayList<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
}
