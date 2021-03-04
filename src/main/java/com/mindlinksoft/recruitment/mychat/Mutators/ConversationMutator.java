package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a mutator which will carry out some operation, e.g. filtering
 * or blacklisting messges, on a conversation.
 */
interface ConversationMutator {
    /*
     * The method to be implemented by mutators: describes the mutator's
     * operation on the conversation passed in.
     * @param c The conversation to be mutated.
     */
    public void mutateConversation(Conversation c);
}
