package com.mindlinksoft.recruitment.mychat;
import java.util.stream.Collectors;
import java.util.*;

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

    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
    
    public void userFiltered(String usr){
        if (usr == null || usr.trim().equals(""))
            return;
        messages = messages.stream().filter(message -> message.senderId.equals(usr))
                .collect(Collectors.toList());
    }
    
    public void wordsFiltered(String word){
        if (word == null || word.trim().equals(""))
            return;
        messages = messages.stream().filter(message -> message.content.matches("\b(?i:.*"+word+".*)\b"))
                .collect(Collectors.toList());
    }

    public void wordHidden(String[] hideWord){
        if (hideWord == null || hideWord.length == 0)
            return;
        for (String i: hideWord){
          this.messages.forEach(message -> message.redactWord(i));
        }
    }

    public void reportGenerated(){
        HashMap<String, Integer> map = new HashMap<>();
        for (Message m:messages){
            map.put(m.idSender,map.getOrDefault(m.idSender,0)+1);
        }
        List<User> listing = new ArrayList<>();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.orderReversed()))
                .forEachOrdered(x-> listing.add(new User(i.getWord(),i.getValue())));

        System.out.println("Report "+listing);
        this.report = listing;
    }

}
