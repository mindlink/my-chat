package com.mindlinksoft.recruitment.mychat;

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

    /**
     * The logs of who was in the conversation and how many messages they sent
     */
    public List<Log> report;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param c The specified conversation.
     * @param report Report containing logs of how many times each user has sent a message
     */
    public Conversation(Conversation c, List<Log> report) {
        this.name = c.getName();
        this.messages = c.getMessages();
        this.report = report;
    }

    public String getName() {
        return name;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    /**
     * Filters collection of messages by userName.
     * @param userName the name of the message sender
     */
    public void filterByUserName(String userName) {
        this.messages.removeIf((Message msg) -> !msg.isSentBy(userName));
    }

    /**
     * Filters collection of messages by userName.
     * @param keyWord the word to filter for
     */
    public void filterByKeyWord(String keyWord) {
        this.messages.removeIf((Message msg) -> !msg.contentContains(keyWord));
    }

    /**
     * Redacts list of words from conversation.
     * @param redactedWords the words to redact
     */
    public void redactByKeyWords(String[] redactedWords) {
        this.messages.forEach((Message msg) -> msg.redact(redactedWords));
    }
    /**
     * Redacts list of words from conversation.
     */
    public List<Log> generateReport() {
        Map<String, Integer> logs = new HashMap<String, Integer>();
        report = new ArrayList<Log>();

        for (Message m: this.messages) {
            String userName = m.senderId;
            if(!logs.containsKey(userName)){
                logs.put(userName, 1);
            } else {
                logs.put(userName, logs.get(userName) + 1);
            }
        }

        for(String userName: logs.keySet()){
            report.add(new Log(userName, logs.get(userName)));
        }

        Collections.sort(report);
        return report;
    }

}
