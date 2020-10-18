package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;

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
     * Filters collection of messages by userName {@code inputFilePath}.
     * @param userName the name of the message sender
     */
    public void filterByUserName(String userName) {
        this.messages.removeIf((Message msg) -> !msg.isSentBy(userName));
    }

    /**
     * Filters collection of messages by userName {@code inputFilePath}.
     * @param keyWord the word to filter for
     */
    public void filterByKeyWord(String keyWord) {
        this.messages.removeIf((Message msg) -> !msg.contentContains(keyWord));
    }

    public void redactByKeyWords(String[] redactedWords) {
        this.messages.forEach((Message msg) -> msg.redact(redactedWords));
    }

}
