package com.mindlinksoft.recruitment.mychat.models;

import java.util.*;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    private String name;

    /**
     * The messages in the conversation.
     */
    private Collection<Message> messages;

    /**
     * The activity report of users in conversation
     */
    private List<User> activity;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name     The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
        this.activity = null;
    }

    public String getName() {
        return name;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public List<User> getActivity() {
        return activity;
    }

    public void createActivityReport() {
        Map<String, Integer> userActivityCount = new HashMap<String, Integer>();

        for (Message message : messages) {
            String senderID = message.getSenderID();
            if (userActivityCount.containsKey(senderID)){
                Integer messageCount = userActivityCount.get(senderID);
                userActivityCount.put(senderID, messageCount + 1);
            } else {
                userActivityCount.put(senderID, 1);
            }
        }

        activity = new ArrayList<User>();
        for (String u : userActivityCount.keySet()){
            User user = new User(u, userActivityCount.get(u));
            activity.add(user);
        }

        Collections.sort(activity);
        Collections.reverse(activity);
    }
}
