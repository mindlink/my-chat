package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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

    /**
     * The messages in the conversation.
     */
    public Collection<User> users;

    /**
     * Initialises a new instance of the {@link Conversation} class.
     *
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    public void refreshUsers() {
        if (users!=null) {
            users.clear();
        }
        List users = new ArrayList();
        for (Message message : messages) {
        boolean userPresent = false;
            for (Iterator it = users.iterator(); it.hasNext();) {
                User user = (User) it.next();
                if (user.getName().equals(message.senderId)) {
                    user.setActivity(user.getActivity() + 1);
                    userPresent = true;
                    break;
                }
            }
            if (!userPresent) {
                users.add(new User(message.senderId));
            }
        }
        Collections.sort(users, new User.ActivityComparator());
        this.users=users;
    }
}
