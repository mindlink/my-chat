package com.mindlinksoft.recruitment.mychat;

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

    public void filterSpecificKeyWords(String keyword){
        if (keyword == null || keyword.trim().equals(""))
            return;
        messages = messages.stream().filter(message -> message.content.matches("\b(?i:.*"+keyword+".*)\b"))
                .collect(Collectors.toList());
    }

    public void filterBySpecificUser(String user){
        if (user == null || user.trim().equals(""))
            return;
        messages = messages.stream().filter(message -> message.senderId.equals(user))
                .collect(Collectors.toList());
    }
    public void hideSpecificWord(String[] blacklist){
        if (blacklist == null || blacklist.length == 0)
            return;
        for (String i: blacklist){
          this.messages.forEach(message -> message.redactWord(i));
        }
    }

    public void generateReport(){
        HashMap<String, Integer> map = new HashMap<>();
        for (Message i:messages){
            map.put(i.senderId,map.getOrDefault(i.senderId,0)+1);
        }
        List<User> list = new ArrayList<>();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x-> list.add(new User(x.getKey(),x.getValue())));

        System.out.println("Report "+list);
        this.report = list;
    }

}
