package com.mindlinksoft.recruitment.mychat.filters;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

import java.util.ArrayList;
import java.util.Iterator;

public class BlacklistFilter {

    private Conversation conversation, blackListedConversation;
    private String argument;

    private ArrayList<String> blackListWords = new ArrayList<String>();
    private ArrayList<Message> blacklistedMessages = new ArrayList<Message>();
    private final String redacted = "*redacted*";

    public BlacklistFilter(Conversation conversation, String argument) {
        this.conversation = conversation;
        this.argument = argument;
    }

    public Conversation filterConversation(){

        Iterator<Message> iterator = conversation.messages.iterator();
        String[] split = argument.split("=");

        for (int i = 1; i < split.length; i++){
            String[] subSplit = split[i].split(" ");
            blackListWords.add(subSplit[0]);
        }

        for (int i = 0; i < blackListWords.size(); i++){
            System.out.println(blackListWords.get(i));
        }

        while(iterator.hasNext()){
            Message m = iterator.next();
            for (int i = 0; i < blackListWords.size(); i++){
                String blacklistWord = blackListWords.get(i);
                if (m.content.contains(blacklistWord)){
                    m.content = m.content.replace(blacklistWord, redacted);
                }
            }
            blacklistedMessages.add(m);
        }

        blackListedConversation = new Conversation(conversation.name, blacklistedMessages);

        return blackListedConversation;

    }
}
