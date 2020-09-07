package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.Message;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    public Conversation filterName(Conversation conversation, String value) {
        List<Message> messages = new ArrayList<>();
        Conversation filteredCon = new Conversation(conversation.name, messages);

        for (Message message : conversation.messages) {
            if (message.senderId.equals(value)) {
                filteredCon.messages.add(message);
            }
        }

        return filteredCon;
    }

    public Conversation filterKeyword(Conversation conversation, String value) {
        List<Message> messages = new ArrayList<>();
        Conversation filteredCon = new Conversation(conversation.name, messages);

        for (Message message : conversation.messages) {
            if (message.content.contains(value)) {
                filteredCon.messages.add(message);
            }
        }

        return filteredCon;
    }

    public Conversation filterHide(Conversation conversation, String value) {
        List<Message> messages = new ArrayList<>();
        Conversation filteredCon = new Conversation(conversation.name, messages);

        String[] blackList = value.split(",");

        for (Message message : conversation.messages) {
            for (String word : blackList) {
                word = "\\W*((?i)" + word + "(?-i))\\W*";
                message.content = message.content.replaceAll(word, "*redacted*");
            }
            filteredCon.messages.add(message);
        }

        return filteredCon;
    }

    public Conversation filterDetails(Conversation conversation) {
        List<Message> messages = new ArrayList<>();
        Conversation filteredCon = new Conversation(conversation.name, messages);

        for (Message message : conversation.messages) {
            String messageContent = message.content.replaceAll("(\\(?\\+44\\)?\\s?([12378])\\d{3}|\\(?(01|02|03|07|08)\\d{3}\\)?)\\s?\\d{3}\\s?\\d{3}|(\\(?\\+44\\)?\\s?([123578])\\d{2}|\\(?(01|02|03|05|07|08)\\d{2}\\)?)\\s?\\d{3}\\s?\\d{4}|(\\(?\\+44\\)?\\s?([59])\\d{2}|\\(?(05|09)\\d{2}\\)?)\\s?\\d{3}\\s?\\d{3}", "*redacted*");
            messageContent = messageContent.replaceAll("\\b((\\d{4})-? ?(\\d{4})-? ?(\\d{4})-? ?(\\d{4}))\\b", "*redacted*");
            message.content = messageContent;
            filteredCon.messages.add(message);
        }

        return filteredCon;
    }
}
