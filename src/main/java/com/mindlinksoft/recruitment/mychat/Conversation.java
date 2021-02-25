package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

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


    /*
     * Names and # of messages sent by participants in conversation
     */
    public Map<String, Integer> participants;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages, Map<String, Integer> participants) {
        this.name = name;
        this.messages = messages;
        this.participants = participants;
    }

    /*
     * Redacts blacklisted words from the messages
     */
    public void Redact()
    {
        // TODO: Implement this
    }
}
