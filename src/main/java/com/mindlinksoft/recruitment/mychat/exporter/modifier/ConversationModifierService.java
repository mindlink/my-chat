package com.mindlinksoft.recruitment.mychat.exporter.modifier;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

/**
 * Represents the modifier service which will modify the conversation object
 */
public interface ConversationModifierService {
    /**
     * Starts the modifier service, which will create a modified 
     * conversation from the provided conversation and modifier type/arguments
     */
    Conversation modify();
}