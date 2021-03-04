package com.mindlinksoft.recruitment.mychat.ConversationFilterers;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

import java.util.ArrayList;
import java.util.List;

public class KeywordFilterer extends Filterer {

    private String keyword;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Filters a conversation to only contain messages that contain the specified keyword
     * @param conversation The conversation to filter.
     */
    public Conversation filter(Conversation conversation) {
        // if keyword is a null string or an empty string, return original conversation
        if (keyword == null || keyword.equals("")) {
            return conversation;
        }

        List<Message> filteredMessages = new ArrayList<>();

        // add message to filtered messages list if it contains the keyword
        for (Message message : conversation.getMessages()) {
            if (message.getContent().contains(keyword)) {
                filteredMessages.add(message);
            }
        }

        // create new conversation object with the filtered messages
        Conversation filteredConversation = new Conversation(conversation.getName(), filteredMessages);

        return filteredConversation;
    }
}
