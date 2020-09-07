package com.mindlinksoft.recruitment.mychat.Utilities.Filter;

import com.mindlinksoft.recruitment.mychat.Constructs.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Constructs.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class Filter {
    ConversationDefault filteredCon;

    void instantiate(ConversationDefault conversationDefault) {
        List<Message> messages = new ArrayList<>();
        filteredCon = new ConversationDefault(conversationDefault.name, messages);
    }

    public abstract ConversationDefault populateAndReturn(ConversationDefault conversationDefault, String value);
}
