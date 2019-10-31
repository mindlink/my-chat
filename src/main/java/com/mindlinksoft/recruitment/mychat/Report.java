package com.mindlinksoft.recruitment.mychat;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Class for making the user activity report, detailing which users are the most active by the number of comments.
 */
public class Report {
    List<String> mostActiveRanking;

    public Report() {
        mostActiveRanking = new ArrayList<>();
    }

    /**
     * Method takes in the conversation and counts the occurrences of a username (and thus a user message) inside a map.
     * The map is then converted into a String List after being sorted in reverse order.
     *
     * @param conversation
     * @return List counting the number of occurances of every username
     */
    public List<String> makeReport(Conversation conversation) {
        Map<String, Integer> countOccurences = new HashMap<>();
        conversation.messages.forEach(s -> {
            if (countOccurences.containsKey(s.username)) {
                countOccurences.put(s.username, countOccurences.get(s.username) + 1);
            } else {
                countOccurences.put(s.username, 1);
            }
        });
        mostActiveRanking.add("This is the report for the most active users, in order" +
                "of the number of messages they sent: ");
        List<Map.Entry<String, Integer>> counted = countOccurences.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
        Collections.reverse(counted);
        counted.forEach(s -> {
            mostActiveRanking.add(s.toString());

        });

        return mostActiveRanking;
    }


}
