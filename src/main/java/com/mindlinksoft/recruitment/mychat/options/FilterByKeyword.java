package com.mindlinksoft.recruitment.mychat.options;

import java.util.ArrayList;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.MyChat;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

public class FilterByKeyword {
    Conversation conversation;
    String keyword;

    /**
     * Initializes a new instance of the {@link FilterByKeyword} class.
     * 
     * @param conversation The conversation.
     * @param keyword      To get the keyword that needs to be applied.
     */
    public FilterByKeyword(Conversation conversation, String keyword) {
        this.conversation = conversation;
        this.keyword = keyword;
    }

    /**
     * Filter the conversation based on the defined keyword filter
     */
    public Collection<Message> process() {
        MyChat.logger.trace("Filtering (by keyword)...");
        Collection<Message> newMessages = new ArrayList<Message>();

        Collection<Message> messages = this.conversation.getMessages();
        for (Message message : messages) {
            // TODO: fix as this only checks for matching chars
            // (for example: could be part of a word)
            if (message.getContent().toUpperCase().indexOf(this.keyword.toUpperCase()) != -1) {
                newMessages.add(message);
            }
        }
        MyChat.logger.info("Filtered to only show messages containing the keyword '" + this.keyword + "'");
        return newMessages;
    }

}
