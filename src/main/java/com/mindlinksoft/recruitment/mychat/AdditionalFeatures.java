package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.exceptions.WrongCommandException;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;

import java.util.*;
import java.util.stream.Collectors;

public class AdditionalFeatures {

    /**
     * Analyses which feature should be applied
     *
     * @param conversation containing all the messages
     * @param feature      to apply
     * @return the conversation with the applied feature
     */
    static Conversation optionalFeatures(Conversation conversation, String feature) throws WrongCommandException {
        Conversation resultWithFeatures;
        switch (feature) {
            case "obfuscate":
                resultWithFeatures = obfuscateIDs(conversation);
                break;
            case "report":
                resultWithFeatures = conversationReport(conversation);
                break;
            default:
                throw new WrongCommandException("Please specify a feature that is available.");
        }
        return resultWithFeatures;
    }

    /**
     * Writes a report about users activity in the conversation
     *
     * @param conversation containing all the messages
     * @return the conversation with the added report
     */
    private static Conversation conversationReport(Conversation conversation) {
        Map<String, Integer> messageCount = new HashMap<>();

        for (Message m : conversation.messages) messageCount = add(m.senderId, messageCount);
        messageCount = sortByValue((HashMap<String, Integer>) messageCount);

        ArrayList<String> arr = new ArrayList<>(messageCount.size());
        for (String s : messageCount.keySet()) arr.add(s + ":" + messageCount.get(s));

        conversation.messages.add(new Message(null, "Activity Report", arr.toString()));
        return conversation;
    }

    /**
     * Orders the hashmap by number of messages sent by each user
     *
     * @param hm hashmap to analyse
     * @return the hasmap ordered from to user with the most messages to the user with least messages
     */
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    /**
     * Adds an element to the Hashmap
     * @param senderId which sent a message
     * @param msgMap   where the number of messages is saved
     */
    public static Map<String, Integer> add(String senderId, Map<String, Integer> msgMap) {
        if (!msgMap.containsKey(senderId)) msgMap.put(senderId, 1);
        else msgMap.put(senderId, msgMap.get(senderId) + 1);
        return msgMap;
    }

    /**
     * Hides user IDs to the output
     * @param conversation containing all the messages
     * @return the conversation with obfuscated IDs
     */
    private static Conversation obfuscateIDs(Conversation conversation) {
        conversation.messages =
                conversation.messages.stream()
                        .map(AdditionalFeatures::obfuscate)
                        .collect(Collectors.toList());
        return conversation;
    }

    /**
     * Encodes the senderId
     * @param m is the message to analyse
     * @return the obfuscated senderId
     */
    private static Message obfuscate(Message m) {
        m.senderId = Base64.getEncoder().encodeToString(m.senderId.getBytes());
        return m;
    }
}
