package com.mindlinksoft.recruitment.mychat.models;

import java.util.Collection;
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
    private Collection<Message> messages;

    /**
     * The user details where the activity reports of the conversations are stored
     */
    private List<User> activity;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * 
     * @param conversationBuilder builds new Conversation to avoid mutability
     */
    public Conversation(ConversationBuilder conversationBuilder) {
        this.name = conversationBuilder.name;
        this.messages = conversationBuilder.messages;
        this.activity = conversationBuilder.activity;
    }

    /**
     * Getter method for retrieving the name of the conversation.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method for retrieving the messages in the conversation.
     */
    public Collection<Message> getMessages() {
        return this.messages;
    }

    /**
     * Getter method for retrieving the users' activity.
     */
    public List<User> getActivity() {
        return this.activity;
    }

    public static class ConversationBuilder {
        private String name;
        private Collection<Message> messages;
        private List<User> activity;

        public Conversation buildNewConversation(String name, Collection<Message> messages, List<User> activity) {
            return new ConversationBuilder().name(name).messages(messages).activity(activity).build();
        }

        public Conversation replaceMessages(Collection<Message> messages) {
            return new ConversationBuilder().name(this.name).messages(messages).activity(this.activity).build();
        }

        public ConversationBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ConversationBuilder messages(Collection<Message> messages) {
            this.messages = messages;
            return this;
        }

        public ConversationBuilder activity(List<User> activity) {
            this.activity = activity;
            return this;
        }

        public Conversation build() {
            return new Conversation(this);
        }
    }
}
