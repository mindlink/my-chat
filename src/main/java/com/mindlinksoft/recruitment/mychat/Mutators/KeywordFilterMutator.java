package com.mindlinksoft.recruitment.mychat;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a mutator which filters a conversation's messages by keyword.
 */
class KeywordFilterMutator implements ConversationMutator {

    /**
     * The keyword this mutator filters for.
     */
    String keyword;

    /**
     * Constructor which specifies the keyword to filter for.
     * @param keyword The keyword to filter for
     */
    public KeywordFilterMutator(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Mutates a conversation's messages such that they are
     * filtered for the specified keyword.
     * @param c The conversation to be filtered.
     */
    public void mutateConversation(Conversation c) {
        List<Message> filteredMsgs = new ArrayList<Message>();
        for (Message msg : c.messages) {
            if (msg.content.toLowerCase().contains(this.keyword.toLowerCase())) {
                filteredMsgs.add(msg);
            }
        }
        c.messages = filteredMsgs;
    }
}
