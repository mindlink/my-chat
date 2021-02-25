package com.mindlinksoft.recruitment.mychat.options;

import java.util.ArrayList;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

public class FilterByUser {
    Conversation conversation;
    String user;

    /**
     * Initializes a new instance of the {@link FilterByUser} class.
     * 
     * @param conversation The conversation.
     * @param user         To get the user that need to be applied.
     */
    public FilterByUser(Conversation conversation, String user) {
        this.conversation = conversation;
        this.user = user;
    }

    /**
     * Filter the conversation based on the defined user filter
     */
    public Collection<Message> process() {
        ConversationExporter.logger.trace("Filtering (by user)...");
        Collection<Message> newMessages = new ArrayList<Message>();

        Collection<Message> messages = this.conversation.getMessages();
        for (Message message : messages) {
            if (message.getSenderId().equals(this.user)) {
                newMessages.add(message);
            }
        }
        ConversationExporter.logger.info("Filtered to only show messages sent by " + this.user);
        return newMessages;
    }

}
