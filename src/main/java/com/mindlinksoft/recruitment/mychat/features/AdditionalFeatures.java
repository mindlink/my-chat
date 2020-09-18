package com.mindlinksoft.recruitment.mychat.features;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdditionalFeatures {

    /**
     * Writes a report about users activity in the conversation
     *
     * @param conversation containing all the messages
     * @return the conversation with the added report
     */
    public static Conversation activityReport(Conversation conversation) {
        HashMap<String, Integer> userMessageCount = new HashMap<>();
        for (Message m : conversation.messages) {
            add(m.senderId, userMessageCount);
        }
        userMessageCount = sortByValue(userMessageCount);
        return (new Conversation(conversation.name, conversation.messages, userMessageCount));
    }

    /**
     * Orders the hashmap by number of messages sent by each user
     *
     * @param hm hashmap to analyse
     * @return the hasmap ordered from to user with the most messages to the user with least messages
     */
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> listEntry : list) {
            sortedMap.put(listEntry.getKey(), listEntry.getValue());
        }
        return sortedMap;
    }

    /**
     * Adds an element to the Hashmap
     *
     * @param senderId         which sent a message
     * @param userMessageCount where the number of messages is saved
     */
    public static Map<String, Integer> add(String senderId, Map<String, Integer> userMessageCount) {
        if (!userMessageCount.containsKey(senderId)) userMessageCount.put(senderId, 1);
        else userMessageCount.put(senderId, userMessageCount.get(senderId) + 1);
        return userMessageCount;
    }

    /**
     * Hides user IDs to the output
     *
     * @param conversation containing all the messages
     * @return the conversation with obfuscated IDs
     */
    public static Conversation obfuscateIDs(Conversation conversation) {
        conversation.messages =
                conversation.messages.stream()
                        .map(AdditionalFeatures::obfuscate)
                        .collect(Collectors.toList());
        return conversation;
    }

    /**
     * Encodes the senderId
     *
     * @param m is the message to analyse
     * @return the obfuscated senderId
     */
    public static Message obfuscate(Message m) {
        m.senderId = Base64.getEncoder().encodeToString(m.senderId.getBytes());
        return m;
    }

}
