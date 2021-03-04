package com.mindlinksoft.recruitment.mychat;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a mutator which redacts certain words from a conversation's messages.
 */
class BlacklistMutator implements ConversationMutator {

    /**
     * The words this mutator redacts.
     */
    public String[] blacklist;

    /**
     * Constructor which specifies the words to be redacted.
     * @param blacklist The words to be redacted
     */
    public BlacklistMutator(String[] blacklist) {
        this.blacklist = blacklist;
    }

    /**
     * Mutates a conversation's messages such that specified words are redacted.
     * @param c The conversation to be censored.
     */
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
