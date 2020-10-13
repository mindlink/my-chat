package com.mindlinksoft.recruitment.mychat.filters;

import java.util.stream.Collectors;

import com.mindlinksoft.recruitment.mychat.models.*;

public class FilterByUser extends Filter {
    /**
     * Method which filters all messages inside a conversation to only contain those sent by 
     * a specific user
     * @param conversation Conversation object which will be altered in place
     * @param user word representing the user to filter
     */
    public void runFilter(Conversation conversation) {
        conversation.messages = conversation.messages.stream().filter(message -> isUser(message, filterWords.get(0))).collect(Collectors.toList());
    }

    /**
     * Helper method which equates two strings and returns a boolean
     * @param message The specific message pertaining to the conversation
     * @param user The user filter
     * @return a boolean whether the message was sent by the specified user
     */
    private boolean isUser(Message message, String user) {
        return normalizeString(message.getSender()).toLowerCase().equals(normalizeString(user).toLowerCase());
    }

    public FilterByUser(String word) {
        super(word);
    }
}
