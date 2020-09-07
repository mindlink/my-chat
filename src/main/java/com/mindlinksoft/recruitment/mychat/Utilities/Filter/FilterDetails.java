package com.mindlinksoft.recruitment.mychat.Utilities.Filter;

import com.mindlinksoft.recruitment.mychat.Objects.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Objects.Message;

import java.util.ArrayList;
import java.util.List;

public class FilterDetails {
    public ConversationDefault populateAndReturn(ConversationDefault conversationDefault) {
        List<Message> messages = new ArrayList<>();
        ConversationDefault filteredCon = new ConversationDefault(conversationDefault.name, messages);

        for (Message message : conversationDefault.messages) {
            String messageContent = message.content.replaceAll("(\\(?\\+44\\)?\\s?([12378])\\d{3}|\\(?(01|02|03|07|08)\\d{3}\\)?)\\s?\\d{3}\\s?\\d{3}|(\\(?\\+44\\)?\\s?([123578])\\d{2}|\\(?(01|02|03|05|07|08)\\d{2}\\)?)\\s?\\d{3}\\s?\\d{4}|(\\(?\\+44\\)?\\s?([59])\\d{2}|\\(?(05|09)\\d{2}\\)?)\\s?\\d{3}\\s?\\d{3}", "*redacted*");
            messageContent = messageContent.replaceAll("\\b((\\d{4})-? ?(\\d{4})-? ?(\\d{4})-? ?(\\d{4}))\\b", "*redacted*");
            message.content = messageContent;
            filteredCon.messages.add(message);
        }

        return filteredCon;
    }
}
