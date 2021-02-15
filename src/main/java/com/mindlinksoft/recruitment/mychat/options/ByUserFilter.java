package com.mindlinksoft.recruitment.mychat.options;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents one of the export options - By User Filter
 * To filter out all messages that were not sent by a specified {@code filterUser} in a supplied {@link Conversation}
 */
public class ByUserFilter implements ConversationExportOptionInterface {
    String filterUserID;

    /**
     * Initializes a new instance of the {@link ByUserFilter} class.
     * @param filterUser A single user to filter messages with as passed from {@link ConversationExporterConfiguration}
     */
    public ByUserFilter(String filterUser) {
        this.filterUserID = filterUser;
    }

    /**
     * {@code process} method implemented to process the given conversation for a particular export option
     * In this option's case, to filter out all messages that were not sent by a specified {@code filterUser} in a supplied {@link Conversation}
     * @param conversation  The {@link Conversation} to be processed
     * @return Conversation The processed {@link Conversation}
     */
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
