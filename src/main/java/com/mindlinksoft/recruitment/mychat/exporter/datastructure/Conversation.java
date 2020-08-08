package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

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
     * Add given message to messages list.
     * @param message the message object you wish to add
     */
    /*
    public void addMessage(Message message) {

    }
    */

    /**
     * Retrieves message at given index from the message list.
     * @param index position of message to retrieve
     * @throws IndexOutOfBoundsException if index is out of bounds
     * @return message at given index
     */
    public Message getMessage(int index) {
        return messages.get(index);
    }

    /**
     * Removes message at given index from the message list.
     * @param index position of message to retrieve
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    /*
    public void deleteMessage(int index) {

    }
    */

    /**
     * Returns the corresponding Sender object from the given senderString.
     * @param senderString name of Sender as it appears in text
     * @throws NoSuchElementException if given senderString not in map
     * @return sender object mapped to given senderString
     */
    /*
     public Sender getSender(String senderString) {
        return null;
    }
    */

    /**
     * Checks if given senderString has been encountered before.
     * @param senderString name of Sender as it appears in text
     * @return true if in senderMap, else false
     */
    /*
     public boolean hasSender(String senderString) {
        return false;
    }
    */

    /**
     * Puts given senderString and its Sender object into senderMap.
     * @param senderString name of Sender as it appears in text
     * @param sender object of Sender, formed from senderString
     * @throws IllegalStateException if given senderString already exists
     */
    /*
     public void putSender(String senderString, Sender sender) {

    }
    */

    /**
     * Returns its Sender object if given senderString is in map.
     * Otherwise, creates new Sender, puts it in map and returns
     * the newly created Sender.
     * @param senderString name of Sender as it appears in text
     * @return Sender object of given senderString
     */
    /*
    public Sender getSenderOrPut(String senderString) {
        return null;
    }
    */

    /**
     * Removes the given senderString and its object from senderMap.
     * @param senderString name of Sender as it appears in text
     * @throws NoSuchElementException if given senderString not in map
     */
    /*
    public void removeSender(String senderString) {

    }
    */

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
