package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

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
    private Map<String, Long> frequencyMap;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     */
    public Conversation() {

    }

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * A new instance of the frequency map is created.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, List<Message> messages) {
        this(name, messages, new HashMap<>());
    }

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     * @param frequencyMap The number of messages sent by each sender
     */
    public Conversation(String name, List<Message> messages, Map<String, Long> frequencyMap) {
        this.name = name;
        this.messages = messages;
        this.frequencyMap = frequencyMap;
    }

    /**
     * Retrieves message at given index from the message list.
     * @param index position of message to retrieve
     * @throws IndexOutOfBoundsException if index is out of bounds
     * @return message at given index
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

    public Map<String, Long> getFrequencyMap() {
        return frequencyMap;
    }

    public void setFrequencyMap(Map<String, Long> frequencyMap) {
        this.frequencyMap = frequencyMap;
    }
}
