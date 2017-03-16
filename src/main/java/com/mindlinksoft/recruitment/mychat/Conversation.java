package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * Name of the conversation declaration.
     */
    private String name;

    /**
     * Messages in the conversation declaration.
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

    /**
     * Name getter method
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter method
     * @param name Sets the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Messages getter method
     * @return messages 
     */
    public Collection<Message> getMessages() {
        return messages;
    }

    /**
     * Messages setter method
     * @param messages Sets the messages
     */
    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }
    
}
