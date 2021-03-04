package com.mindlinksoft.recruitment.mychat;

import java.util.List;
import java.util.ArrayList;

class UserFilterMutator implements ConversationMutator {

    String username;
    public UserFilterMutator(String username) {
        this.username = username;
    }

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
