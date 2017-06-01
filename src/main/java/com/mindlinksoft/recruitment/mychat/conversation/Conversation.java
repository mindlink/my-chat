package com.mindlinksoft.recruitment.mychat.conversation;

import com.mindlinksoft.recruitment.mychat.message.Message;
import com.mindlinksoft.recruitment.mychat.message.MessageInterface;

import java.util.*;

/**
 * Represents the model of a conversation.
 */
public final class Conversation implements ConversationInterface {
    /**
     * The name of the conversation.
     */
    private String name;

    /**
     * The messages in the conversation.
     */
    private Collection<MessageInterface> messages;

    /**
     * The active user report array
     */
    private List<UserStats> userActivityStats = new ArrayList<UserStats>();

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<MessageInterface> messages) {
        this.name = name;
        this.messages = messages;
    }

    /**
     * gets the conversation name
     * @return the conversation name
     */
    public String getName(){
        return name;
    }

    /**
     * gets message collection
     * @return the messages collection
     */
    public Collection<MessageInterface> getMessages(){
        return messages;
    }

    /**
     * sets the name of the conversation
     * @param name string representing the name of the conversation
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * sets the conversation messages
     * @param messages a {@link Collection<MessageInterface>}
     */
    public void setMessages(Collection<MessageInterface> messages) {
        this.messages = messages;
    }

    /**
     * Creates an active user report based on the CURRENT STATE of the conversation
     */
    public void setupActiveUserReport(){
        String messageSender;
        HashMap<String, Integer> mostActiveUsers = new HashMap<>();

        for(MessageInterface message : this.messages){
            messageSender = message.getSenderId();

            if(mostActiveUsers.containsKey(messageSender)){
                Integer sentMessagesNumber = mostActiveUsers.get(messageSender);
                sentMessagesNumber += 1;
                mostActiveUsers.replace(messageSender, sentMessagesNumber);
            } else {
                mostActiveUsers.put(messageSender, 1);
            }
        }

        //TODO: change this??
        for(Map.Entry<String, Integer> entry : mostActiveUsers.entrySet()){
            UserStats stats = new UserStats(entry.getKey(), entry.getValue());
            this.userActivityStats.add(stats);
        }
        Collections.sort(this.userActivityStats, UserStats.COMPARE_BY_SENT_MESSAGES);
    }
}
