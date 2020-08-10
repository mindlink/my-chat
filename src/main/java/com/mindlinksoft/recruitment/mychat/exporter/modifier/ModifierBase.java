package com.mindlinksoft.recruitment.mychat.exporter.modifier;

import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;

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
     * @param conversation the conversation to be modified
     */
    protected ModifierBase(Conversation conversation) {
        this.conversation = conversation;
    }

    /**
     * Creates a conversation of the same name and no messages.
     * @return an empty conversation
     */
    protected Conversation createConversation() {
        return new Conversation(conversation.getName(), new ArrayList<Message>());
    }

    protected abstract Conversation modify();
}