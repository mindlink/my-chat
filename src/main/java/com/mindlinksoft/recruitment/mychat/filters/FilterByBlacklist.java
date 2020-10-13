package com.mindlinksoft.recruitment.mychat.filters;

import java.util.Arrays;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.models.*;

public class FilterByBlacklist extends Filter {
    /**
     * Method which replaces all occurrences of any blacklisted word in any message content
     * with the word *redacted* 
     * @param conversation Conversation object to be altered in place
     * @param blacklist List object that contains all of the blacklisted words
     */
    public void runFilter(Conversation conversation) {
        for (Message message : conversation.messages) {
            List<String> convoWords = Arrays.asList(message.getContent().split(" "));
            convoWords.replaceAll(word -> inBlacklist(word, filterWords));
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

    public FilterByBlacklist(List<String> filterWords) {
        super(filterWords);
    }
}
