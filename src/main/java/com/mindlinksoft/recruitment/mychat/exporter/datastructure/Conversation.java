package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    private String name;

    /**
     * The messages in the conversation.
     */
    private List<Message> messages;

    /**
     * Maps sender text to count number of messages sent by sender
     */
    private List<Sender> activeUsers;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     */
    public Conversation() {

    }

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * A new instance of the frequency map is created.
     *
     * @param name     The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, List<Message> messages) {
        this(name, messages, new ArrayList<>());
    }

    /**
     * Initializes a new instance of the {@link Conversation} class.
     *
     * @param name         The name of the conversation.
     * @param messages     The messages in the conversation.
     * @param activeUsers The number of messages sent by each sender
     */
    public Conversation(String name, List<Message> messages, List<Sender> activeUsers) {
        this.name = name;
        this.messages = messages;
        this.activeUsers = activeUsers;
    }

    /**
     * Retrieves message at given index from the message list.
     *
     * @param index position of message to retrieve
     * @return message at given index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public Message getMessage(int index) {
        return messages.get(index);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Sender> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(List<Sender> activeUsers) {
        this.activeUsers = activeUsers;
    }
}
