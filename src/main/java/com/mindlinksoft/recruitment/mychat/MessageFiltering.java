package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

public abstract class MessageFiltering {
    /**
     * Abstract class to be overriden for different filteration of a conversation.
     * @param conversation The conversation to be filtered.
     * @param config The configuration of the conversation
     * @throws IllegalArgumentException Filter user/keyword passed is null.
     */
    public abstract Conversation filterConversation(Conversation conversation, ConversationExporterConfiguration config) throws IllegalArgumentException;

    /**
     *  Class finds a word in a given message
     * @param messages The message to search through.
     * @param keyword The word to search for.
     */
    public Boolean findWord(Message message, String keyword) {
        boolean containsKeyword = false;

        keyword = keyword.toLowerCase();
        // split message content by spaces, making all words lower case and removing any punctuation.
        String[] split = message.content.replaceAll("\\p{Punct}", "").toLowerCase().split(" ");

        for(String word : split) {
            if(word.equals(keyword)) {
                containsKeyword = true;
            }
        }

        return containsKeyword;
    }
}

/**
 *  Class that filters the conversation by given user.
 */
class FilterByUser extends MessageFiltering {
    public Conversation filterConversation(Conversation conversation, ConversationExporterConfiguration config) throws IllegalArgumentException {
        try {
            String user = config.filterUser;
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
class FilterByKeyword extends MessageFiltering {
    public Conversation filterConversation(Conversation conversation, ConversationExporterConfiguration config) throws IllegalArgumentException {
        try {
            String keyword = config.filterKeyword;
            // loop through messages in the conversation and keep those that contain the keyword in their content.
            List<Message> filteredMessages = new ArrayList<Message>();
            for(Message message : conversation.messages) {
                if(findWord(message, keyword)) {
                    filteredMessages.add(message);
                }
            }

            // replace conversation messages that have keyword present.
            conversation.messages = filteredMessages;

            return conversation;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Keyword to be filtered was null.");
        }
    }
}

class FilterByBlacklist extends MessageFiltering {
    public Conversation filterConversation(Conversation conversation, ConversationExporterConfiguration config) throws IllegalArgumentException {
        try {
            String[] words = config.blacklistWords;
            // loop through messages in the conversation and redact words that are blacklisted
            for(Message message : conversation.messages) {
                for(String word : words) {
                    if(findWord(message, word)) {
                        // replace all words that match with *redacted*, regardless of case
                        message.content = message.content.replaceAll(("(?i)" + word), "*redacted*");
                    }
                }
            }

            return conversation;
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Keyword to be filtered was null.");
        }
    }
}