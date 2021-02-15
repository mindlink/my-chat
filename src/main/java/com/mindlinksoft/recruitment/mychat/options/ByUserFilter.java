package com.mindlinksoft.recruitment.mychat.options;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import java.util.ArrayList;
import java.util.Collection;

public class ByUserFilter implements ConversationExportOptionInterface {
    String filterUserID;

    public ByUserFilter(String filterUser) {
        this.filterUserID = filterUser;
    }

    @Override
    public Conversation process(Conversation conversation) {
        Collection<Message> messages = conversation.getMessages();
        Collection<Message> messagesToRemove = new ArrayList<Message>();

        for (Message message : messages) {
            if (!message.getSenderID().equals(filterUserID)){
                messagesToRemove.add(message);
                ConversationExporter.logger.info("Processed: Filter by user - Removed `" + message.getContent() + "` from conversation as was not sent by user `" + filterUserID + "`");
            }
        }

        messages.removeAll(messagesToRemove);

        return conversation;
    }
}
