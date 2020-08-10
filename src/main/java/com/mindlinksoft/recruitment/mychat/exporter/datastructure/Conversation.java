package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

import java.util.List;

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
     * Map of sender names to their Sender object.
     */
    // private Map<String, Sender> senderMap;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     */
    public Conversation() {

    }

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, List<Message> messages) {
        this.name = name;
        this.messages = messages;
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

    /*
    public Map<String, Sender> getSenderMap() {
        return senderMap;
    }

    public void setSenderMap(Map<String, Sender> senderMap) {
        this.senderMap = senderMap;
    }
    */
}
