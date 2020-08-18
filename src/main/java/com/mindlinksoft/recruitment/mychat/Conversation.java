package com.mindlinksoft.recruitment.mychat;


import org.junit.Test;

import java.util.*;
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


    public List<User> report = new ArrayList<>();
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

    /**
     * replace words in blacklist with *redacted*
     * @param blacklist list of words to replace
     */
    public void hideWords(List<String> blacklist){
        if(blacklist.isEmpty()) return;
        this.messages.forEach(message -> message.hideWords(blacklist));
    }

    public void generateReport(){
        //use hashmap to store no of messages for each user
        Map<String,Integer> reportMap = new HashMap<>();

        for(Message m: this.messages){
            if(reportMap.containsKey(m.senderId)){
                reportMap.put(m.senderId,reportMap.get(m.senderId)+1);
            }else{
                reportMap.put(m.senderId,1);
            }
        }
        //Add this data to an array use User class to hold data
        for (String username: reportMap.keySet()){
            int messages = reportMap.get(username);
            User u = new User(username,messages);
            report.add(u);
        }
        //sort list based on no of messages
        report.sort((o1, o2) -> o2.no_of_messages - o1.no_of_messages);
    }


}
