package com.mindlinksoft.recruitment.mychat.editor.filters;

import com.mindlinksoft.recruitment.mychat.conversation.ConversationInterface;

/**
 * Conversation filter interface
 * New filters must implement this interface
 */
public interface ConversationFilterInterface {

    /**
     * method definition to filter conversation
     * must be implemented by filters
     * @param conversation object that implements {@link ConversationInterface}.
     * @return a filtered conversation object that implements {@link ConversationInterface}
     */
    ConversationInterface filterConversation(ConversationInterface conversation);
}
