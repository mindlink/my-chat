package com.mindlinksoft.recruitment.mychat.options;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.models.Conversation;

/**
 * Represents a export option - Report Option
 * To generate a report of the number of messages each user sent for a given {@link Conversation}
 */
public class ReportOption implements ConversationExportOptionInterface {
    /**
     * A method to be implemented to process the given conversation with a particular export option
     * @param conversation  The {@link Conversation} to be processed
     * @return Conversation The processed {@link Conversation}
     */
    @Override
    public Conversation process(Conversation conversation) {
        conversation.createActivityReport();
        ConversationExporter.logger.info("Report option - Created activity report for conversation" +  conversation.getName());

        return conversation;
    }
}
