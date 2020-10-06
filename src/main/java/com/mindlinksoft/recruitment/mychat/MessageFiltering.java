package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

interface MessageFiltering {
    /**
     * Abstract class to be overriden for different filteration of a conversation.
     * @param conversation The conversation to be filtered.
     * @param filter The filter user/keyword.
     * @throws IllegalArgumentException Filter user/keyword passed is null.
     */
    public Conversation filterConversation(Conversation conversation, String filter) throws IllegalArgumentException;
}

/**
 *  Class that filters the conversation by given user.
 */
class FilterByUser implements MessageFiltering {
    public Conversation filterConversation(Conversation conversation, String user) throws IllegalArgumentException {
        try {
            // loop through messages in the conversation and keep those that have the senderId of given user.
            List<Message> filteredMessages = new ArrayList<Message>();
            for(Message message : conversation.messages) {
                if(message.senderId.equals(user)) {
                    filteredMessages.add(message);
                }
            }

            // replace conversation messages with messages from given user.
            conversation.messages = filteredMessages;

            return conversation;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("User to be filtered was null.");
        }
    }
}

/**
 * Class that filters the conversation by given keyword.
 */
class FilterByKeyword implements MessageFiltering {
    public Conversation filterConversation(Conversation conversation, String keyword) throws IllegalArgumentException {
        try {
            // loop through messages in the conversation and keep those that contain the keyword in their content.
            List<Message> filteredMessages = new ArrayList<Message>();
            for(Message message : conversation.messages) {
                boolean containsKeyword = false;
                String[] split = message.content.split(" ");

                for(String word : split) {
                    if(word.equals(keyword)) {
                        containsKeyword = true;
                    }
                }

                if(containsKeyword) {
                    filteredMessages.add(message);
                }
            }

            // replace conversation messages that have keyword present.
            conversation.messages = filteredMessages;

            return conversation;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("User to be filtered was null.");
        }
    }
}