package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tom on 03-Jun-16.
 */
public class ChatFilter {

    public static Conversation filterByUser(Conversation conv, String user)
    {
        ArrayList<Message> filteredMessages = new ArrayList<Message>();
        for (Message msg:conv.getMessages()) {
            if (msg.getSenderId().equals(user)) {
                filteredMessages.add(msg);
            }
        }
        String[] users = {user};
        return new Conversation(conv.getName(), users, filteredMessages);
    }

    public static Conversation filterByKeyword(Conversation conv, String keyword)
    {
        ArrayList<Message> filteredMessages = new ArrayList<Message>();
        HashSet<String> users = new HashSet<String>();
        for (Message msg:conv.getMessages()) {
            if (msg.getContent().contains(keyword)) {
                filteredMessages.add(msg);
                users.add(msg.getSenderId());
            }
        }
        return new Conversation(conv.getName(), users.toArray(new String[users.size()]), filteredMessages);
    }

    //TODO: should probably filter out the entire word on partial match - e.g. "pies" will be transformed into "\*redacted\*s" atm
    public static Conversation filterOutWord(Conversation conv, String word)
    {
        ArrayList<Message> filteredMessages = new ArrayList<Message>();
        for (Message msg:conv.getMessages()) {
            filteredMessages.add( new Message(msg.getTimestamp(), msg.getSenderId(), msg.getContent().replace(word, "\\*redacted\\*") ));
        }
        return new Conversation(conv.getName(), conv.getUsers(), filteredMessages);
    }

    public static Conversation filterOutWordList(Conversation conv, String[] wordList)
    {
        ArrayList<Message> filteredMessages = new ArrayList<Message>();
        for (Message msg:conv.getMessages()) {
            String filteredContent = msg.getContent();
            for (String word:wordList) {
                filteredContent = filteredContent.replace(word, "\\*redacted\\*");
            }
            filteredMessages.add( new Message(msg.getTimestamp(), msg.getSenderId(), filteredContent ));
        }
        return new Conversation(conv.getName(), conv.getUsers(), filteredMessages);
    }

    public static Conversation filterOutCardNumbers(Conversation conv)
    {
        //\b4[0-9]{12}(?:[0-9]{3})?\b
        ArrayList<Message> filteredMessages = new ArrayList<Message>();
        Pattern p = Pattern.compile("(\\b4[0-9]{12}(?:[0-9]{3})?\\b)");
        for (Message msg:conv.getMessages()) {
            Matcher m = p.matcher(msg.getContent());
            String filteredContent = msg.getContent();
            if (m.find()) {
                for (int i = 0; i<m.groupCount(); i++)
                {
                    filteredContent = filteredContent.replace(m.group(i), "\\*redacted\\*");
                }
                filteredMessages.add( new Message(msg.getTimestamp(), msg.getSenderId(), filteredContent ));
            }else{
                filteredMessages.add(msg);
            }
        }
        return new Conversation(conv.getName(), conv.getUsers(), filteredMessages);
    }

    //TODO: very narrow regex atm, expand it
    public static Conversation filterOutPhoneNumbers(Conversation conv)
    {
        //"(?<number>(\\b|\\s|^)(?:(?:\\+44)|0)\\s?\\d{4}\\s?\\d{6}\\b)  \n"
        ArrayList<Message> filteredMessages = new ArrayList<Message>();
        Pattern p = Pattern.compile("(?<number>(?:(?:\\+44)|0)\\s?\\d{4}\\s?\\d{6}\\b)");
        for (Message msg:conv.getMessages()) {
            Matcher m = p.matcher(msg.getContent());
            String filteredContent = msg.getContent();
            while (m.find()) {
                filteredContent = filteredContent.replace(m.group("number"), "\\*redacted\\*");
            }
            filteredMessages.add( new Message(msg.getTimestamp(), msg.getSenderId(), filteredContent ));
        }
        return new Conversation(conv.getName(), conv.getUsers(), filteredMessages);
    }

}
