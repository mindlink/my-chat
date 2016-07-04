package com.mindlinksoft.recruitment.mychat;

import java.util.List;

/**
 * Represents the model of a conversation. Responsible for providing a model for
 * a conversation.
 */
class Conversation {
    /**
     * The name of the conversation.
     */
    String name;

    /**
     * The messages in the conversation.
     */
    List<Message> messages;
    
    ReportEntry[] report;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, List<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
           
}
