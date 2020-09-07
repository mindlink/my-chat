package com.mindlinksoft.recruitment.mychat.Utilities.Filter;

import com.mindlinksoft.recruitment.mychat.Objects.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Objects.Message;

public class FilterHide extends Filter {
    @Override
    public ConversationDefault populateAndReturn(ConversationDefault conversationDefault, String value) {
        instantiate(conversationDefault);

        String[] blackList = value.split(",");

        for (Message message : conversationDefault.messages) {
            for (String word : blackList) {
                word = "\\W*((?i)" + word + "(?-i))\\W*";
                message.content = message.content.replaceAll(word, "*redacted*");
            }
            filteredCon.messages.add(message);
        }

        return filteredCon;
    }
}
