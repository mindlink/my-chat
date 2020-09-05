package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.ConversationExtended;
import com.mindlinksoft.recruitment.mychat.Objects.Message;
import com.mindlinksoft.recruitment.mychat.Objects.User;

import java.util.ArrayList;
import java.util.List;

public class Report {

    private static Conversation conversation;

    public static void generateActivityData(Conversation conversation) {
        Report.conversation = conversation;
    }

    public static ConversationExtended generateReport() {
        List<User> users = new ArrayList<>();
        ConversationExtended outputConExt = new ConversationExtended(conversation.name,conversation.messages, users);

        for (Message message : outputConExt.messages)
        {

        }

        return outputConExt;
    }
}
