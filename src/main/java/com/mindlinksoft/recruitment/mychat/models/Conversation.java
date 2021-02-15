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
     * @code  activity An attribute to associate the activity report to the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
        this.activity = null;
    }

    /**
     * Getter method for access to the {@code name} of a conversation
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for access to the {@code messages} of a conversation
     */
    public Collection<Message> getMessages() {
        return messages;
    }

    /**
     * Getter method for access to the {@code activity} report of a conversation
     */
    public List<User> getActivity() {
        return activity;
    }

    /**
     * Method for the generation of a {@code activity} report to associate with a {@link Conversation}
     */
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
