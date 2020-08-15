package com.mindlinksoft.recruitment.mychat.model;

import java.util.ArrayList;
import java.util.Map;
/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    public final String name;

    /**
     * The messages in the conversation.
     */
    public final ArrayList<Message> messages;
    
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
