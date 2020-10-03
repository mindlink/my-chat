package com.mindlinksoft.recruitment.mychat.filters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mindlinksoft.recruitment.mychat.models.*;

public class ConversationFilter {
    
    public void filterByUser(Conversation convo, String word) {
        convo.messages = convo.messages.stream().filter(message -> isUser(message, word)).collect(Collectors.toList());
    }

    private boolean isUser(Message message, String word) {
        return normalizeString(message.getSender()).toLowerCase().equals(normalizeString(word).toLowerCase());
    }


    public void filterByKeyword(Conversation convo, String word) {
        convo.messages = convo.messages.stream().filter(message -> containsKeyword(message, word)).collect(Collectors.toList());
    }

    private boolean containsKeyword(Message message, String word) {
        List<String> words = Arrays.asList(message.getContent().split(" "));
        words.replaceAll(w -> normalizeString(w).toLowerCase());

        if (words.contains(word)) {
            return true;
        } else {
            return false;
        }
    }


    public void removeBlacklist(Conversation convo, List<String> blacklist) {
        for (Message message : convo.messages) {
            List<String> convoWords = Arrays.asList(message.getContent().split(" "));
            convoWords.replaceAll(word -> inBlacklist(word, blacklist));
            message.setContent(String.join(" ", convoWords));
        }
    }

    private String inBlacklist(String word, List<String> blacklist) {
        blacklist.replaceAll(w -> normalizeString(w).toLowerCase());
        if (blacklist.contains(normalizeString(word).toLowerCase()) && !word.equals("*redacted*")) {
            return "*redacted*";
        } else {
            return word;
        }
    }

    private String normalizeString(String str) {
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }

}
