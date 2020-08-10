package com.mindlinksoft.recruitment.mychat.exporter.modifier.filter;

import java.util.List;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

/**
 * Represents a filter that selects a certain sender
 */
public class FilterUser extends ModifierBase implements Filter {

    /**
     * The sender as it appears in text
     */
    private final String senderText;

    /**
     * Returns a filter that selects the given sender
     * @param conversation contains the messages you wish to filter
     * @param senderText the sender as it appears in text
     */
    public FilterUser(Conversation conversation, String senderText) {
        super(conversation);
        this.senderText = senderText;
    }

    @Override
    protected Conversation modify() {
        return filter();
    }

    /**
     * Creates a new Conversation that only contains
     * the specified sender's messages.
     * @return filtered Conversation
     */
    @Override
    public Conversation filter() {
        Conversation resultConversation = createConversation();
        List<Message> resultMessages = resultConversation.getMessages();
        List<Message> messages = conversation.getMessages();
        filterMessages(messages, resultMessages);
        return resultConversation;
    }

    /**
     * Helper method which adds old messages to the new messages
     * if it was sent by this sender
     * @param oldMessages the messages to be filtered
     * @param resultMessages the message filtered by this sender
     */
    private void filterMessages(List<Message> oldMessages, List<Message> resultMessages) {
        for (int i = 0; i < oldMessages.size(); i++) {
            Message message = oldMessages.get(i);
            String messageSender = message.getSenderText();
            if (messageSender.equals(this.senderText)) {
                resultMessages.add(message);
            }
        }
    }
    
}