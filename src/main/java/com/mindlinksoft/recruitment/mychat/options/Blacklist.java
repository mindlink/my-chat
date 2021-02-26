package com.mindlinksoft.recruitment.mychat.options;

import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.MyChat;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

public class Blacklist {
    Conversation conversation;
    String[] blacklist;

    /**
     * Initializes a new instance of the {@link Blacklist} class.
     * 
     * @param conversation The conversation.
     * @param blacklist    To get the blacklist that needs to be applied.
     */
    public Blacklist(Conversation conversation, String[] blacklist) {
        this.conversation = conversation;
        this.blacklist = blacklist;
    }

    /**
     * Update the conversation based on the defined blacklist
     */
    public Collection<Message> process() {
        MyChat.logger.trace("Filtering (blacklist)...");
        Collection<Message> messages = this.conversation.getMessages();
        for (Message message : messages) {
            for (String word : this.blacklist) {
                if (message.getContent().toUpperCase().indexOf(word.toUpperCase()) != -1) {
                    // TODO: revisit as the replace code only redacts if the word if lead by a space
                    // (depends if the requirements want just the combination of letters to be
                    // redacted or if its a fully isolated word)
                    message.setContent(message.getContent().replaceAll("(?i)\\b" + word, "\\*redacted\\*"));
                }
            }
        }
        MyChat.logger.info("Filtered to censor the occurances of blacklisted words");
        return messages;
    }
}
