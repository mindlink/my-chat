package com.mindlinksoft.recruitment.mychat.conversation;

import com.mindlinksoft.recruitment.mychat.message.Message;
import com.mindlinksoft.recruitment.mychat.riport.Riport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Riport for the most active users in the conversation.
     */
    private Collection<Riport> riport;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     *
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(final String name, final Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    /**
     * Generates the riport list containing the senders and the messages counts
     * in a descending order.
     */
    public void generateRiport() {
        Map<String, Riport> riportMap = new HashMap<>();
        Riport temp;
        // count the messages for each sender
        for (Message message : messages) {
            temp = riportMap.get(message.getSenderId());
            if (temp == null) {
                temp = new Riport(message.getSenderId());
            }
            temp.increaseMessageCount();
            riportMap.put(message.getSenderId(), temp);
        }
        // create list from map
        riport = new ArrayList<>(riportMap.values());
        // order the riports using Comparator and lambda expression
        Collections.sort((List) riport, (Riport o1, Riport o2) -> o2.getMessageCount() - o1.getMessageCount());
    }
}
