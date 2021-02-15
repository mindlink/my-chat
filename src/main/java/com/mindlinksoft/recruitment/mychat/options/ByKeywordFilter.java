package com.mindlinksoft.recruitment.mychat.options;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import java.util.ArrayList;
import java.util.Collection;

public class ByKeywordFilter implements ConversationExportOptionInterface {
    String filterKeyword;

    public ByKeywordFilter(String filterKeyword) {
        this.filterKeyword = filterKeyword;
    }

    @Override
    public Conversation process(Conversation conversation) {
        Collection<Message> messages = conversation.getMessages();
        Collection<Message> messagesToRemove = new ArrayList<Message>();

        for (Message message : messages) {
            String messageContent = message.getContent();
            if (!messageContent.toLowerCase().contains(filterKeyword.toLowerCase())){
                messagesToRemove.add(message);
                ConversationExporter.logger.info("Processed: Filtered by keyword - Removed `" + message.getContent() + "` from conversation as it does not contain keyword `" + filterKeyword + "`");
            }
        }

        messages.removeAll(messagesToRemove);

        return conversation;
    }
}
