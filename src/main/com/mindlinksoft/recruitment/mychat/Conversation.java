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
    public List<Message> messages;

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
     * Adds a message
     * @param message The messages in the conversation to be added
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * Adds a message
     * @param position The position of the message in the conversation to be returned
     */
    public String getMessageContent(int position) {
        return this.messages.get(position).content;
    }

    /**
     * Lenght of the coversation
     */
    public int getLenght() {
        return this.messages.size();
    }

    /**
     * Applies the keyword filter
     * @param value the value as filter
     */
    public void applyKeywordFilter(String value) {
        List<Message> filtered = new ArrayList<>();
        for (Message message : messages)
            if (message.content.contains(value))
                filtered.add(message);

        this.messages = filtered;
    }

    /**
     * Applies the user filter
     * @param value the value as filter
     */
    public void applyUserFilter(String value) {
        List<Message> filtered = new ArrayList<>();
        for (Message message : messages)
            if (message.senderId.equals(value))
                filtered.add(message);

        this.messages = filtered;
    }

    /**
     * Blacklist some words
     * @param value the value as filter
     */
    public void applyBlacklistFilter(String[] values) {
        String space = mychatConstants.BLACKLIST_SPACE;
        for (Message message : messages)
            for (String value : values)
                message.content = message.content.replace(space + value + space,
                                                          space + mychatConstants.BLACKLIST_SYMBOL + space);
    }

    /**
     * Hide numbers of the conversation
     * @param value the value as filter
     */
    public void applyHideNumbers() {
        for (Message message : messages) {
            message.content = message.content.replaceAll(mychatConstants.HIDENUMBERS_PATTERS, mychatConstants.HIDENUMBERS_SYMBOL);
        }
    }

    /**
     * Obfuscate ID of the conversation
     * @param value the value as filter
     */
    public void applyObfuscateID() {
        for (Message message : messages)
            message.senderId = (new Integer(message.senderId.hashCode())).toString();
    }

    /**
     * Apply filters
     * @param ConversationExporterConfiguration with configuration
     */
    public void applyFilters(ConversationExporterConfiguration configuration) {
        if (configuration.username != null)
            applyUserFilter(configuration.username);
        if (configuration.blacklist != null)
            applyBlacklistFilter(configuration.blacklist.split(mychatConstants.BLACKLIST_SEP));
        if (configuration.keyword != null)
            applyKeywordFilter(configuration.keyword);
        if (configuration.hideNumbers)
            applyHideNumbers();
        if (configuration.obfuscateID)
            applyObfuscateID();
    }

}
