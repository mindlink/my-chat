package com.mindlinksoft.recruitment.mychat.models;

import java.util.Collection;
import java.util.List;

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
     * The user details where the activity reports of the conversations are stored
     */
    private List<User> activity;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * 
     * @param name     The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    /**
     * Getter method for retrieving the name of the conversation.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method for retrieving the messages in the conversation.
     */
    public Collection<Message> getMessages() {
        return this.messages;
    }

    /**
     * Getter method for retrieving the userDetails.
     */
    public List<User> getActivity() {
        return this.activity;
    }

    /**
     * Method to update the activity
     */
    public Conversation updateActivity(List<User> activity) {
        this.activity = activity;
        return this;
    }
}
