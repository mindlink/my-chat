package com.mindlinksoft.recruitment.mychat.ConversationFilterers;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

import java.util.ArrayList;
import java.util.List;

public class UserFilterer extends Filterer {

    private String user;

    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Filters a conversation to only contain messages sent by the specified user
     * @param conversation The conversation to filter.
     */
    public Conversation filter(Conversation conversation) {
        // if user is a null string or an empty string, return original conversation
        if (user == null || user.equals("")) {
            return conversation;
        }

        List<Message> filteredMessages = new ArrayList<>();

        // if message sender is the same as the user specified, add message to filtered messages list (case insensitive)
        for (Message message : conversation.getMessages()) {
            if (message.getSenderId().equalsIgnoreCase(user)) {
                filteredMessages.add(message);
            }
        }

        // create a new conversation object with the filtered messages
        Conversation filteredConversation = new Conversation(conversation.getName(), filteredMessages);

        return filteredConversation;
    }
}
