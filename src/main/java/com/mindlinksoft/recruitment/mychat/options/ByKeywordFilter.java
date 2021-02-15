package com.mindlinksoft.recruitment.mychat.options;

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
                System.out.println("Filtered by keyword - Removed `" + message.getContent() + "` from conversation as it does not contain keyword `" + filterKeyword + "`"); // TODO [logging]: Make proper logging - not just sout`s :)
            }
        }

        messages.removeAll(messagesToRemove);

        return conversation;
    }
}
