package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
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
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    /**
     * Filter messages by senderId
     * @param username User's name to filter by (case-sensitive)
     */
    public void applyUserFilter(String username) {
        ArrayList<Message> newMessages = new ArrayList<>();

        for (Message message : messages) {
            if (message.senderId.equals(username)) {
                newMessages.add(message);
            }
        }

        messages = newMessages;
    }
}
