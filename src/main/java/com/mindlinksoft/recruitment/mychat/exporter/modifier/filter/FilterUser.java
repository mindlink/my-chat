package com.mindlinksoft.recruitment.mychat.exporter.modifier.filter;

import java.util.List;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

/**
 * Represents a filter that selects certain senders
 */
public class FilterUser extends ModifierBase {

    /**
     * The senders as they appear in text
     */
    private final String[] senderTexts;

    /**
     * Returns a filter that selects the given senders
     * @param conversation contains the messages you wish to filter
     * @param senderTexts the senders as they appear in text
     */
    public FilterUser(Conversation conversation, String[] senderTexts) {
        super(conversation);
        this.senderTexts = senderTexts;
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
    public Conversation filter() {
        Conversation resultConversation = createConversation();
        List<Message> resultMessages = resultConversation.getMessages();
        List<Message> messages = conversation.getMessages();
        filterMessages(messages, resultMessages);
        return resultConversation;
    }

    /**
     * Helper method which adds old messages to the new messages
     * if it was sent by these senders
     * @param oldMessages the messages to be filtered
     * @param resultMessages the message filtered by this sender
     */
    private void filterMessages(List<Message> oldMessages, List<Message> resultMessages) {
        for (Message message : oldMessages) {
            String messageSender = message.getSenderText();
            for (String senderText : senderTexts) {
                if (messageSender.equals(senderText)) {
                    resultMessages.add(message);
                }
            }
        }
    }
    
}