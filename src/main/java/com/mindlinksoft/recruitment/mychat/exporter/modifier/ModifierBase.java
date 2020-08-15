package com.mindlinksoft.recruitment.mychat.exporter.modifier;

import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

/**
 * Represents any of the modification methods including filters, hide and obfuscate.
 */
public abstract class ModifierBase {

    /**
     * The conversation to be modified.
     */
    protected final Conversation conversation;

    /**
     * Called by deriving classes when instantiating them.
     *
     * @param conversation the conversation to be modified
     */
    protected ModifierBase(Conversation conversation) {
        this.conversation = conversation;
    }

    /**
     * Creates a conversation of the same name and no messages.
     *
     * @return an empty conversation
     */
    protected Conversation createConversation() {
        return new Conversation(conversation.getName(), new ArrayList<>(), conversation.getActiveUsers());
    }

    /**
     * Abstract method which will call the specific modification method,
     * depending on the class of the instantiated object.
     *
     * @return modified conversation
     */
    protected abstract Conversation modify();
}