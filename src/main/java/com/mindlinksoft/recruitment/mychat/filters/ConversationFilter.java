package com.mindlinksoft.recruitment.mychat.filters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mindlinksoft.recruitment.mychat.models.*;

/**
 * Class which dictates all changes to do with filtering messages
 */
public class ConversationFilter {
    
    /**
     * Method which filters all messages inside a conversation to only contain those sent by 
     * a specific user
     * @param convo Conversation object which will be altered in place
     * @param user word representing the user to filter
     */
    public void filterByUser(Conversation convo, String user) {
        convo.messages = convo.messages.stream().filter(message -> isUser(message, user)).collect(Collectors.toList());
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


    /**
     * Method that filters all messages to those only containing the specified keyword
     * @param convo Conversation object to be altered in place
     * @param word the keyword which determines messages filtered
     */
    public void filterByKeyword(Conversation convo, String word) {
        convo.messages = convo.messages.stream().filter(message -> containsKeyword(message, word)).collect(Collectors.toList());
    }

    /**
     * Helper method that expands and normalizes the content of messages
     * @param message Message to expand
     * @param word Keyword to look for
     * @return Boolean based on whether the keyword occurs in the message content
     */
    private boolean containsKeyword(Message message, String word) {
        List<String> words = Arrays.asList(message.getContent().split(" "));
        words.replaceAll(w -> normalizeString(w).toLowerCase());

        if (words.contains(word)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Method which replaces all occurrences of any blacklisted word in any message content
     * with the word *redacted* 
     * @param convo Conversation object to be altered in place
     * @param blacklist List object that contains all of the blacklisted words
     */
    public void removeBlacklist(Conversation convo, List<String> blacklist) {
        for (Message message : convo.messages) {
            List<String> convoWords = Arrays.asList(message.getContent().split(" "));
            convoWords.replaceAll(word -> inBlacklist(word, blacklist));
            message.setContent(String.join(" ", convoWords));
        }
    }

    /**
     * Helper method to determine whether a normalized word is contained in the blacklist
     * @param word String in question
     * @param blacklist List of blacklisted words
     * @return If not occurring in the blacklist then return the String, else return *redacted*
     */
    private String inBlacklist(String word, List<String> blacklist) {
        blacklist.replaceAll(w -> normalizeString(w).toLowerCase());
        if (blacklist.contains(normalizeString(word).toLowerCase()) && !word.equals("*redacted*")) {
            return "*redacted*";
        } else {
            return word;
        }
    }

    /**
     * Helper method that normalizes strings to only contain alphanumeric characters
     * @param str Input string
     * @return Normalized string
     */
    private String normalizeString(String str) {
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }

}
