package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.Message;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    public static Conversation filterName(Conversation conversation, String value) {
        List<Message> messages = new ArrayList<>();
        Conversation filteredCon = new Conversation(conversation.name, messages);

        for (Message message : conversation.messages) {
            if (message.senderId.equals(value)) {
                filteredCon.messages.add(message);
            }
        }

        return filteredCon;
    }

    public static Conversation filterKeyword(Conversation conversation, String value) {
        List<Message> messages = new ArrayList<>();
        Conversation filteredCon = new Conversation(conversation.name, messages);

        for (Message message : conversation.messages) {
            if (message.content.contains(value)) {
                filteredCon.messages.add(message);
            }
        }

        return filteredCon;
    }

    public static Conversation filterHide(Conversation conversation, String value) {
        List<Message> messages = new ArrayList<>();
        Conversation filteredCon = new Conversation(conversation.name, messages);

        String[] blackList = value.split(",");

        for (String word : blackList) {
            word = "\\b" + word + "\\b";
            for (Message message : conversation.messages) {
                message.content = message.content.replaceAll(word, "*redacted");
                filteredCon.messages.add(message);
            }
        }

        return filteredCon;
    }
}
