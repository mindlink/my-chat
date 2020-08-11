package com.mindlinksoft.recruitment.mychat.exporter.modifier.obfuscate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Sender;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

/**
 * Represents a modifier that will obfuscate users from messages
 */
public class ObfuscateUsers extends ModifierBase implements Obfuscate {

    /**
     * Maps the sender as it appears in text to its Sender object
     */
    private final Map<String, Sender> senderMap;

    /**
     * Returns a modifier that hides phone numbers and credit card numbers in messages
     * @param conversation contains the messages you wish to hide numbers from
     */
    public ObfuscateUsers(Conversation conversation) {
        super(conversation);
        this.senderMap = new HashMap<>();
    }

    @Override
    protected Conversation modify() {
        return obfuscate();
    }

    /**
     * Returns a copy of this conversation with the senders obfuscated
     * @return conversation with senders obfuscated
     */
    @Override
    public Conversation obfuscate() {
        Conversation resultConversation = createConversation();
        List<Message> resultMessages = resultConversation.getMessages();
        List<Message> messages = conversation.getMessages();
        obfuscateMessages(messages, resultMessages);
        return resultConversation;
    }

    /**
     * Helper method which adds old messages to the new messages
     * if it contains these key words
     * @param oldMessages the messages to be filtered
     * @param resultMessages the message filtered by this sender
     */
    private void obfuscateMessages(List<Message> oldMessages, List<Message> resultMessages) {
        for (int i = 0; i < oldMessages.size(); i++) {
            Message message = oldMessages.get(i);
            String senderText = message.getSenderText();
            String senderId = getOrPutSender(senderText);
            Message modifiedMessage = copyMessageExceptSender(message, senderId);
            resultMessages.add(modifiedMessage);
        }
    }

    /**
     * Checks if given senderText is in map, and if true,
     * returns it's id, else, creates a new Sender, puts 
     * it in this senderMap, before returning its id
     * 
     * @param senderText the sender as it appears in text
     * @return the id of its mapped Sender in String
     */
    private String getOrPutSender(String senderText) {
        long senderId;
        if (senderMap.containsKey(senderText)) {
            senderId = senderMap.get(senderText).getSenderId();
        } else {
            Sender sender = new Sender(senderText);
            senderMap.put(senderText, sender);
            senderId = sender.getSenderId();
        }
        return String.valueOf(senderId);
    }

    /**
     * Creates a new message based on the old message's content and timestamp
     * @param message the message you wish to clone
     * @param newSender the sender you wish to replace with
     * @return message with same content and timestamp, but new sender
     */
    private Message copyMessageExceptSender(Message message, String newSender) {
        return new Message(message.getTimestamp(), newSender, message.getContent());
    }
}