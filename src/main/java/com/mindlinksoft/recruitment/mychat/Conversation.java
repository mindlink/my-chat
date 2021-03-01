package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    public String name;

    /**
     * The messages in the conversation.
     */
    public Collection<Message> messages;

    public Collection<Activity> activities = null;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    /*
     * Returns only the messages which are sent by specified user
     * @param name The name of the user
     */
    public Collection<Message> filteredByUser(String name) {
        List<Message> filteredMsgs = new ArrayList<Message>();
        for (Message msg : this.messages) {
            if (msg.senderId.toLowerCase().equals(name.toLowerCase())) {
                filteredMsgs.add(msg);
            }
        }
        return filteredMsgs;
    }

    /*
     * Returns only the messages which contain specified keyword
     * @param keyword The keyword to be included
     */
    public Collection<Message> filteredByKeyword(String keyword) {
        List<Message> filteredMsgs = new ArrayList<Message>();
        for (Message msg : this.messages) {
            if (msg.content.toLowerCase().contains(keyword.toLowerCase())) {
                filteredMsgs.add(msg);
            }
        }
        return filteredMsgs;
    }

    /*
     * Returns the messages with blacklisted words redacted
     * @param blacklist The blacklist of words to be redacted
     */
    public Collection<Message> censored(String[] blacklist) {
        List<Message> censoredMsgs = new ArrayList<Message>();
        for (Message msg : this.messages) {
            for (String word : blacklist) {
                msg.content = msg.content.replaceAll("(?i)" + word, "*redacted*");
            }
            censoredMsgs.add(msg);
        }
        return censoredMsgs;
    }

    public Collection<Activity> composeReport() {
        Map<String, Integer> counts = new HashMap<String, Integer>();
        for (Message msg : messages) {
            if (counts.containsKey(msg.senderId)) {
                counts.put(msg.senderId, counts.get(msg.senderId) + 1);
            }
            else {
                counts.put(msg.senderId, 1);
            }
        }

        Collection<Activity> activities = new ArrayList<Activity>();
        for (Map.Entry<String, Integer> count : counts.entrySet()) {
            activities.add(new Activity(count.getKey(), count.getValue()));
        }
        return activities;
    }

    class Activity {
        private String sender;
        private int count;

        private Activity(String s, int c) {
            this.sender = s;
            this.count = c;
        }
    }
}
