package com.mindlinksoft.recruitment.juliankubelec.mychat;

import java.util.Collection;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {

    public String getName() {
        return name;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    /**
     * The name of the conversation.
     */
    private final String name;

    /**
     * The messages in the conversation.
     */
    private Collection<Message> messages;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
}
