package com.mindlinksoft.recruitment.mychat.options;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.models.Conversation;

public class ReportOption implements ConversationExportOptionInterface {
    @Override
    public Conversation process(Conversation conversation) {
        conversation.createActivityReport();
        ConversationExporter.logger.info("Report option - Created activity report");

        return conversation;
    }
}
