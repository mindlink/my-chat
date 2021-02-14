package com.mindlinksoft.recruitment.mychat.options;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.User;

public class reportOption implements conversationExportInterface{
    @Override
    public Conversation process(Conversation conversation) {
        conversation.createActivityReport();
        System.out.println("Report option - Created activity report"); // TODO [logging]: Make proper logging - not just sout`s :)

        return conversation;
    }
}
