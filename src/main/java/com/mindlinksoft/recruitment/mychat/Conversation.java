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

    public void followInstruction(String instruction) {
        InstructionParser p = new InstructionParser(instruction);

        switch (p.command.toLowerCase()) {
            case "filterbyusers":
                FilterByUsers(p.arguments);
                break;
            case "filterbywords":
                FilterByWords(p.arguments);
                break;
            case "blacklistwords":
                BlacklistWords(p.arguments);
                break;

            default:
                throw new IllegalArgumentException("Unsupported instruction");

        }


    }

    public void FilterByUsers(List<String> usernames) {
        messages.removeIf(m -> !usernames.contains(m.senderId));
    }

    public void FilterByWords(List<String> words) {
        messages.removeIf(m -> !m.containsAnyOf(words));
    }

    public void BlacklistWords(List<String> words) {
        for(Message m : messages) {
            m.redactAllOf(words);
        }
    }
}
