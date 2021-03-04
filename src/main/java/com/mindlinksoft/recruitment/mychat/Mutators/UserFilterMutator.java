package com.mindlinksoft.recruitment.mychat;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a mutator which filters a conversation's messages by user.
 */
class UserFilterMutator implements ConversationMutator {

    /**
     * The user whose messages this mutator filters for.
     */
    String username;

    /**
     * Constructor which specifies the user to filter for.
     * @param username The user to filter for.
     */
    public UserFilterMutator(String username) {
        this.username = username;
    }

    /**
     * Mutates a conversation's messages such that they are
     * filtered for the specified user.
     * @param c The conversation to be filtered.
     */
    public void mutateConversation(Conversation c) {
        List<Message> filteredMsgs = new ArrayList<Message>();
        for (Message msg : c.messages) {
            if (msg.senderId.toLowerCase().equals(this.username.toLowerCase())) {
                filteredMsgs.add(msg);
            }
        }
        c.messages = filteredMsgs;
    }
}
