package com.mindlinksoft.recruitment.mychat.exporter.modifier.report;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Sender;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
     * Counts the number of messages sent by each sender, and
     * places them in a frequency map.
     *
     * @param messages list of messages in a conversation
     * @return list of senders ordered by their message count
     */
    private List<Sender> countMessages(List<Message> messages) {
        for (Message message : messages) {
            String senderText = message.getSenderText();

            long count = frequencyMap.getOrDefault(senderText, 0L) + 1L;
            frequencyMap.put(senderText, count);
        }

        return createSortedSenderList();
    }

    /**
     * Returns a list of most active users according to
     * this frequency map.
     *
     * @return list of senders sorted by message count
     */
    private List<Sender> createSortedSenderList() {
        List<Sender> result;

        result = frequencyMap.entrySet()
                .stream()
                .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                .map(e -> new Sender(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return result;
    }
}
