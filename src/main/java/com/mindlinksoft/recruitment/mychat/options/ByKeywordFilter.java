package com.mindlinksoft.recruitment.mychat.options;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents one of the export options - By Keyword Filter
 * To filter out all messages that do not contain a specified {@code filterKeyword} in a supplied {@link Conversation}
 */
public class ByKeywordFilter implements ConversationExportOptionInterface {
    String filterKeyword;

    /**
     * Initializes a new instance of the {@link ByKeywordFilter} class.
     * @param filterKeyword A single word to filter messages with as passed from {@link ConversationExporterConfiguration}
     */
    public ByKeywordFilter(String filterKeyword) {
        this.filterKeyword = filterKeyword;
    }

    /**
     * {@code process} method implemented to process the given conversation for a particular export option
     * In this option's case, to filter out all messages that do not contain a specified {@code filterKeyword} in a supplied {@link Conversation}
     * @param conversation  The {@link Conversation} to be processed
     * @return Conversation The processed {@link Conversation}
     */
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
