package com.mindlinksoft.recruitment.mychat;

import java.util.List;
import java.util.ArrayList;

class BlacklistMutator implements ConversationMutator {

    String[] blacklist;
    public BlacklistMutator(String[] blacklist) {
        this.blacklist = blacklist;
    }

    public void mutateConversation(Conversation c) {
        List<Message> censoredMsgs = new ArrayList<Message>();
        for (Message msg : c.messages) {
            for (String word : blacklist) {
                msg.content = msg.content.replaceAll("(?i)" + word, "*redacted*");
            }
            censoredMsgs.add(msg);
        }
        c.messages = censoredMsgs;
    }
}
