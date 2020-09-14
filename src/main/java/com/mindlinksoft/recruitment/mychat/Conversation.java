package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;

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
     * Initializes a new instance of the {@link Conversation} class. Expanded to include popularity field, as appending the ArrayList<UserNode> to the 
     * JSONified Conversation directly makes for a more awkward time testing the popularity functionality than is needed. #
     * It is defaultly null so when not desired nothing further is written to the JSON output. 
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     * @param popularity Field of UserNodes added to record the users who sent the most messages in descending order. 
     */
    
    public ArrayList<UserNode> activity;
    
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
        // init as null, popularity reports are on-demand
        this.activity = null;
    }
}
