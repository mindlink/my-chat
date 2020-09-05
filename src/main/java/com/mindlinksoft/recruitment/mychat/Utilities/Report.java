package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.ConversationExtended;
import com.mindlinksoft.recruitment.mychat.Objects.User;

import java.util.ArrayList;
import java.util.List;

public class Report {
    public final ConversationExtended filterName(Conversation conversation) {
        List<User> users = new ArrayList<>();
        ConversationExtended outputConExt = new ConversationExtended(conversation.name,conversation.messages, users);

        return outputConExt;
    }
}
