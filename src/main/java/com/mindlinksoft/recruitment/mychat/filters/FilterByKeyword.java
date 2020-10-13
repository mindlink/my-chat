package com.mindlinksoft.recruitment.mychat.filters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mindlinksoft.recruitment.mychat.models.*;

public class FilterByKeyword extends Filter {
    /**
     * Method that filters all messages to those only containing the specified keyword
     * @param conversation Conversation object to be altered in place
     * @param word the keyword which determines messages filtered
     */
    public void runFilter(Conversation conversation) {
        conversation.messages = conversation.messages.stream().filter(message -> containsKeyword(message, filterWords.get(0))).collect(Collectors.toList());
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


    public FilterByKeyword(String word) {
        super(word);
    }
}
