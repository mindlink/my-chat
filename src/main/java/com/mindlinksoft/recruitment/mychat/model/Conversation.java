package com.mindlinksoft.recruitment.mychat.model;

import java.util.ArrayList;
import java.util.HashMap;
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
    
    /**
     * Stores the metadata of the conversation
     */
    public final ConversationMetadata metadata;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, ArrayList<Message> messages) 
    {
        this.name = name;
        this.messages = messages;
        this.metadata = new ConversationMetadata(new HashMap<String, Integer>());
    }
    
    /**
     * Initializes new instance of {@link Conversation} class with a useractivity map.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     * @param metadata The useractivity map to create the ConversationMetadata with
     */
    public Conversation(String name, ArrayList<Message> messages, Map<String, Integer> metadata) 
    {
        this.name = name;
        this.messages = messages;
        this.metadata = new ConversationMetadata(metadata);
    }
    
    /**
     * Initializes new instance of {@link Conversation} class with a conversationmetadata object.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     * @param metadata The useractivity map to create the ConversationMetadata with
     */
    public Conversation(String name, ArrayList<Message> messages, ConversationMetadata metadata) 
    {
        this.name = name;
        this.messages = messages;
        this.metadata = metadata;
    }
    
    
}
