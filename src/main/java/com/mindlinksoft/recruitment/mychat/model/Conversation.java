package com.mindlinksoft.recruitment.mychat.model;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
    public Collection<Message> messages;

    public Map<String, Integer> report;


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

    public Conversation(String name, Collection<Message> messages, Map<String, Integer> report) {
        this.name = name;
        this.messages = messages;
        this.report = report;
    }
}
