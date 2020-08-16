package com.mindlinksoft.recruitment.mychat.exporter.modifier.report;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Sender;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

import java.util.*;

/**
 * Represents a modifier that appends a report of the most active users to a conversation
 */
public class ReportActiveSenders extends ModifierBase {

    /**
     * Represents a frequency map of sender's message count
     */
    private final Map<String, Long> frequencyMap;

    /**
     * Returns an instance of active user reports
     *
     * @param conversation contains list of messages
     */
    public ReportActiveSenders(Conversation conversation) {
        super(conversation);
        this.frequencyMap = new HashMap<>();
    }

    @Override
    protected Conversation modify() {
        return report();
    }

    /**
     * Returns this conversation after counting its messages
     *
     * @return conversation with report appended
     */
    private Conversation report() {
        List<Message> messages = conversation.getMessages();
        List<Sender> senders = countMessages(messages);
        conversation.setActiveUsers(senders);
        return conversation;
    }

    /**
     * Counts the number of messages sent by each sender, then adds
     * them to a list and sorts them by that amount.
     *
     * @param messages list of messages in a conversation
     * @return list of senders ordered by their message count
     */
    private List<Sender> countMessages(List<Message> messages) {
        List<Sender> mostActiveUsers = new ArrayList<>();

        for (Message message : messages) {
            String senderText = message.getSenderText();

            long count = frequencyMap.getOrDefault(senderText, 0L) + 1L;
            frequencyMap.put(senderText, count);

            if (!hasSender(mostActiveUsers, senderText, count)) {
                mostActiveUsers.add(createSender(senderText, count));
            }
        }

        mostActiveUsers.sort((Comparator.comparing(e -> frequencyMap.get(e.getSenderText()))));
        Collections.reverse(mostActiveUsers);
        return mostActiveUsers;
    }

    /**
     * Checks if given senderText is in list of senders. If true, updates
     * its count to the given value.
     *
     * @param senders    list of senders
     * @param senderText the sender as it appears in text
     * @param count      number of messages sent by the given sender
     * @return true if sender already exists, else false
     */
    private boolean hasSender(List<Sender> senders, String senderText, long count) {
        for (Sender sender : senders) {
            if (sender.getSenderText().equalsIgnoreCase(senderText)) {
                sender.setMessageCount(count);
                return true;
            }
        }

        return false;
    }

    /**
     * Creates a new Sender from senderText, and attaches the number
     * of messages sent by that sender.
     *
     * @param senderText the sender as it appears in text
     * @param count      number of messages sent by the given sender
     * @return Sender representing given senderText
     */
    private Sender createSender(String senderText, long count) {
        Sender sender = new Sender(senderText);
        sender.setMessageCount(count);
        return sender;
    }
}
