package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents the model of a conversation.
 */
public final class Conversation {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The name of the conversation.
     */
    private String name;

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    private String[] users;
    private String[] obfuscatedUsernames;

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    /**
     * The messages in the conversation.
     */
    private ArrayList<Message> messages;

    private void generateObfuscatedUsernames()
    {
        String chars = "ABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
        int NAME_LENGTH = 8; //length of obfuscated names
        Random random = new Random();

        for (int i=0;i<users.length;i++)
        {
            for (int j=0;j<NAME_LENGTH;j++)
            {
                obfuscatedUsernames[i] += chars.charAt(random.nextInt(chars.length()));
            }
        }
    }


    /**
     * Find text in messages int.
     *
     * @param pattern           takes a regexp pattern with the group being searched for having the label "search" - e.g "(?<search>/Qbob/E)"
     * @param messages          a reference to the collection of all of the messages in the conversation
     * @return                  returns a collection (ArrayList) of "pointers" that point to the search term (/pattern) in the messages collection
     *
     * I could really use some pointers right now :\ Oh well
     * Also, not sure if regexp is worth it, might cause more trouble than it's worth.
     */
    public Collection<MessagePointer> findTextInMessages(ArrayList<Message> messages, String pattern)
    {
        Collection<MessagePointer> msgPtr = new ArrayList<MessagePointer>();
        for (int i = 0; i<messages.size();i++)
        {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(messages.get(i).getContent());
            if (m.find()) {
                msgPtr.add( new MessagePointer(messages, i,  m.start(), m.start()+m.group("search").length())); //TODO: this returns NULL and throws exceptions
            }
        }
        return msgPtr;
    }

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, String[] users, ArrayList<Message> messages) {
        this.name = name;
        this.messages = messages;
        this.users = users;
        //TODO: Extract users from chat messages and replace their names with an ID
        //TODO: generate a random string until it doesn't appear in the messages, then replace username[i] with it
    }
}
