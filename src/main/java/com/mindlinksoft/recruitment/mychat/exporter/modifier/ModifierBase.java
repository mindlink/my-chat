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
     * Abstract method which will call the specific modification method,
     * depending on the class of the instantiated object.
     *
     * @return modified conversation
     */
    protected abstract Conversation modify();
}