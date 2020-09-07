package com.mindlinksoft.recruitment.mychat.Utilities.Filter;

import com.mindlinksoft.recruitment.mychat.Objects.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Objects.Message;

public class FilterKeyword extends Filter {
    @Override
    public ConversationDefault populateAndReturn(ConversationDefault conversationDefault, String value) {
        instantiate(conversationDefault);
        for (Message message : conversationDefault.messages) {
            if (message.content.contains(value)) {
                filteredCon.messages.add(message);
            }
        }
        return filteredCon;
    }
}
