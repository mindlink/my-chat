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
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    public Conversation(String name){
        this.name = name;
        this.messages = new ArrayList<>();
    }

    public Conversation(Collection<Message> messages) {
        this.name = new String();
        this.messages = messages;
    }
}
