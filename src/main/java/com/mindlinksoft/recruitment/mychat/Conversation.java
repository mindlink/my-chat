package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.List;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    public String conversation_name;

    /**
     * The messages in the conversation.
     */
    public Collection<Message> messages;

    /**
     * The list of the most active users in the conversation.
     */

    public List<String> user_activity_report;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, List<Message> messages) {
        this.conversation_name = name;
        this.messages = messages;
    }

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages, List<String> userActivityReport) {
        this.conversation_name = name;
        this.messages = messages;
        this.user_activity_report = userActivityReport;
    }

}
