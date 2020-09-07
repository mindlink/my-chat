package com.mindlinksoft.recruitment.mychat.Utilities.Filter;

import com.mindlinksoft.recruitment.mychat.Constructs.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Constructs.Message;

public class FilterName extends Filter {
    @Override
    public ConversationDefault populateAndReturn(ConversationDefault conversationDefault, String value) {
        instantiate(conversationDefault);
        for (Message message : conversationDefault.messages) {
            if (message.senderId.equals(value)) {
                filteredCon.messages.add(message);
            }
        }
        return filteredCon;
    }
}
