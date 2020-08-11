package com.mindlinksoft.recruitment.mychat.exporter.modifier.hide;

import java.util.List;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

/**
 * Represents a modifier that hides phone/credit card numbers in messages
 */
public class HideCreditCardPhoneNumbers extends ModifierBase implements Hide {

    /**
     * Returns a modifier that hides phone numbers and credit card numbers in messages
     * @param conversation contains the messages you wish to hide numbers from
     */
    public HideCreditCardPhoneNumbers(Conversation conversation) {
        super(conversation);
    }

    @Override
    protected Conversation modify() {
        return hide();
    }
    
    /**
     * Creates a new Conversation with the credit card and
     * phone numbers hidden
     * @return Conversation with key numbers hidden
     */
    @Override
    public Conversation hide() {
        Conversation resultConversation = createConversation();
        List<Message> resultMessages = resultConversation.getMessages();
        List<Message> messages = conversation.getMessages();
        hideMessages(messages, resultMessages);
        return resultConversation;
    }

    /**
     * Helper method which adds old messages to the new conversation
     * with credit card and phone numbers replaced
     * @param oldMessages the messages to be redacted
     * @param resultMessages the message redacted by this sender
     */
    private void hideMessages(List<Message> oldMessages, List<Message> resultMessages) {
        for (int i = 0; i < oldMessages.size(); i++) {
            Message message = oldMessages.get(i);
            String content = message.getContent();
            String hideNumbers = replacePhoneNumber(content);
            String hideCreditNumbers = replaceCreditCardNumber(hideNumbers);
            Message modifiedMessage = copyMessageExceptContent(message, hideCreditNumbers);
            resultMessages.add(modifiedMessage);
        }
    }

    /**
     * Replaces UK phone numbers with redacted. Numbers
     * must be of the form:
     *  - [0][0-9][0-9][ ][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]
     *  - [0][0-9][0-9][0-9][0-9][ ][0-9][0-9][0-9][0-9][0-9][0-9]
     *  - [00]OR[+][44] [0-9][0-9] [0-9][0-9][0-9][0-9][0-9][0-9][0-9]
     * All spaces are optional
     * @param content string you wish to hide phone numbers from
     * @return string with phone numbers redacted
     */
    private String replacePhoneNumber(String content) {
        content = content.replaceAll("(?i)\\b([0][\\d]{10})|([0][\\d]{4}[ ]?[\\d]{6})|([0][\\d]{2}[ ]?[\\d]{8})|((00|\\+)(44)[ ]?[\\d]{2}[ ]?[\\d]{8})\\b", "*redacted*");
        return content;
    }


    /**
     * Replaces 16 digit credit card numbers with redacted.
     * @param content string you wish to hide credit card numbers from
     * @return string with credit card numbers redacted
     */
    private String replaceCreditCardNumber(String content) {
        content = content.replaceAll("(?i)\\b([\\d]{4}[ ]?[\\d]{4}[ ]?[\\d]{4}[ ]?[\\d]{4})\\b", "*redacted*");
        return content;
    }

    /**
     * Creates a new message based on the old message's sender and timestamp
     * @param message the message you wish to clone
     * @param newContent the content you wish to replace with
     * @return message with same sender and timestamp, but new content
     */
    private Message copyMessageExceptContent(Message message, String newContent) {
        return new Message(message.getTimestamp(), message.getSenderText(), newContent);
    }
}