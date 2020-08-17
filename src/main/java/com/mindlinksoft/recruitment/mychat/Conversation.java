package com.mindlinksoft.recruitment.mychat;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
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
        if(user == null) return;
        this.messages = this.messages
                .stream()
                .filter(message -> message.senderId.equals(user))
                .collect(Collectors.toList());
    }

    /**
     * keep messages which contain the keyword (case-insensitive)
     * @param keyword keyword for filtering
     */
    public void filterMessagesByKeyword(String keyword){
        if(keyword ==null) return;
//        this.messages = this.messages
//                .stream()
//                .filter(message -> (new HashSet<String>( Arrays.asList(message.content.split(" ")) ) ).contains(keyword) )
//                .collect(Collectors.toList());
        this.messages = this.messages
                .stream()
                .filter(message -> message.content.toLowerCase().contains(keyword.toLowerCase())  )
                .collect(Collectors.toList());
    }
}
