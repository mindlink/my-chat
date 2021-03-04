package com.mindlinksoft.recruitment.mychat;

import java.util.List;
import java.util.ArrayList;

class KeywordFilterMutator implements ConversationMutator {

    String keyword;
    public KeywordFilterMutator(String keyword) {
        this.keyword = keyword;
    }

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
