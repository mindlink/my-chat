package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    //return messages sent by

    /**
     * filter messages sent by user
     * @param user name of user
     */
    public void filterMessagesByUser(String user){
        if(user == null || user.isEmpty()) return;
        this.messages = this.messages
                .stream()
                .filter(message -> message.senderId.equals(user))
                .collect(Collectors.toList());
//        Collection<Message> filteredMessages = new ArrayList<>();
//        for (Message m:messages) {
//            if(m.senderId.equals(user)){
//                filteredMessages.add(m);
//            }
//        }
//        messages = filteredMessages;
    }
}
