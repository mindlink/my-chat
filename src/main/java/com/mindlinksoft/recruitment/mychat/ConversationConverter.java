package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ConversationConverter {

    public ConversationExporterConfiguration config;


    public void filterByUser(Conversation convo) {
        convo.messages = convo.messages.stream().filter(message -> isUser(message, config.userFilter)).collect(Collectors.toList());
    }

    private boolean isUser(Message message, String word) {
        return normalizeString(message.getSender()).toLowerCase().equals(normalizeString(word).toLowerCase());
    }


    public void filterByKeyword(Conversation convo) {
        convo.messages = convo.messages.stream().filter(message -> containsKeyword(message, config.wordFilter)).collect(Collectors.toList());
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


    public void removeBlacklist(Conversation convo) {
        for (Message message : convo.messages) {
            List<String> words = Arrays.asList(message.getContent().split(" "));
            words.replaceAll(word -> inBlacklist(word, config.blacklistWords));
            message.setContent(String.join(" ", words));
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


    public void produceReport(Conversation convo) {
        HashMap<String, Integer> counterMap = new HashMap<String, Integer>();
        for (Message message : convo.messages) {
            String sender = message.getSender();
            if (counterMap.containsKey(sender)) {
                counterMap.put(sender, counterMap.get(sender) + 1);
            } else {
                counterMap.put(sender, 1);
            }
        }

        List<Activity> activity = new ArrayList<Activity>();
        for (String sender : counterMap.keySet()) {
            Activity tempActivity = new Activity(sender, counterMap.get(sender));
            activity.add(tempActivity);
        }

        convo.addReport(activity);

    }


    private String normalizeString(String str) {
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }


    public void convertAll(Conversation convo) {
        if (config.userFilter != null && !config.userFilter.equals(" ")) {
            filterByUser(convo);
        }

        if (config.wordFilter != null && !config.wordFilter.equals(" ")) {
            filterByKeyword(convo);
        }

        if (config.blacklistWords != null) {
            removeBlacklist(convo);
        }

        if (config.report) {
            produceReport(convo);
        }
    }


    public ConversationConverter(ConversationExporterConfiguration config) {
        this.config = config;
    }

}
