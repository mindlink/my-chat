package com.mindlinksoft.recruitment.mychat.exporter.modifier.report;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Sender;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

import java.util.*;

public class ReportActiveSenders extends ModifierBase {

    private Map<String, Long> frequencyMap;

    ReportActiveSenders(Conversation conversation) {
        super(conversation);
        this.frequencyMap = new HashMap<>();
    }

    @Override
    protected Conversation modify() {
        return report();
    }

    private Conversation report() {
        List<Message> messages = conversation.getMessages();
        List<Sender> senders = countMessages(messages);
        conversation.setActiveUsers(senders);
        return conversation;
    }

    private List<Sender> countMessages(List<Message> messages) {
        List<Sender> mostActiveUsers = new ArrayList<>();

        for (Message message : messages) {
            String senderText = message.getSenderText();

            long count = frequencyMap.getOrDefault(senderText, 0L)+1L;
            frequencyMap.put(senderText, count);

            if (!hasSender(mostActiveUsers, senderText, count)) {
                mostActiveUsers.add(createSender(senderText, count));
            }
        }

        mostActiveUsers.sort((Comparator.comparing(e -> frequencyMap.get(e.getSenderText()))));
        Collections.reverse(mostActiveUsers);
        return mostActiveUsers;
    }

    private boolean hasSender(List<Sender> senders, String senderText, long count) {
        for (Sender sender : senders) {
            if (sender.getSenderText().equalsIgnoreCase(senderText)) {
                sender.setMessageCount(count);
                return true;
            }
        }

        return false;
    }

    private Sender createSender(String senderText, long count) {
        Sender sender = new Sender(senderText);
        sender.setMessageCount(count);
        return sender;
    }
}
