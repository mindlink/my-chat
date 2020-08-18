package com.mindlinksoft.recruitment.mychat.exporter.modifier.hide;

import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

/**
 * Represents a modifier that hides phone/credit card numbers in messages
 */
public class HideCreditCardPhoneNumbers extends ModifierBase {

    /**
     * Returns a modifier that hides phone numbers and credit card numbers in messages
     *
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
     *
     * @return Conversation with key numbers hidden
     */
    public Conversation hide() {
        List<Message> messages = conversation.getMessages();
        List<Message> resultMessages = hideMessages(messages);
        return new Conversation(conversation.getName(), resultMessages, conversation.getActiveUsers());
    }

    /**
     * Helper method which adds old messages to the new conversation
     * with credit card and phone numbers replaced
     *
     * @param oldMessages the messages to be redacted
     * @return resultMessages the message redacted by this sender
     */
    private List<Message> hideMessages(List<Message> oldMessages) {
        List<Message> resultMessages = new ArrayList<>();

        for (Message message : oldMessages) {
            String content = message.getContent();
            String hideNumbers = replacePhoneNumber(content);
            String hideCreditNumbers = replaceCreditCardNumber(hideNumbers);
            Message modifiedMessage = copyMessageExceptContent(message, hideCreditNumbers);
            resultMessages.add(modifiedMessage);
        }

        return resultMessages;
    }

    /**
     * Replaces UK phone numbers with redacted. Numbers
     * must be of the form:
     * - [0][0-9][0-9][ ][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]
     * - [0][0-9][0-9][0-9][0-9][ ][0-9][0-9][0-9][0-9][0-9][0-9]
     * - [00]OR[+][44] [0-9][0-9] [0-9][0-9][0-9][0-9][0-9][0-9][0-9]
     * All spaces are optional
     *
     * @param content string you wish to hide phone numbers from
     * @return string with phone numbers redacted
     */
    private String replacePhoneNumber(String content) {
        String regex = "(?i)\\b([0][\\d]{10})|([0][\\d]{4}[ ]?[\\d]{6})|([0][\\d]{2}[ ]?[\\d]{8})|((00|\\+)(44)[ ]?[\\d]{2}[ ]?[\\d]{8})\\b";
        content = content.replaceAll(regex, "*redacted*");
        return content;
    }


    /**
     * Replaces 16 digit credit card numbers with redacted.
     *
     * @param content string you wish to hide credit card numbers from
     * @return string with credit card numbers redacted
     */
    private String replaceCreditCardNumber(String content) {
        String regex = "(?i)\\b([\\d]{4}[ ]?[\\d]{4}[ ]?[\\d]{4}[ ]?[\\d]{4})\\b";
        content = content.replaceAll(regex, "*redacted*");
        return content;
    }

    /**
     * Creates a new message based on the old message's sender and timestamp
     *
     * @param message    the message you wish to clone
     * @param newContent the content you wish to replace with
     * @return message with same sender and timestamp, but new content
     */
    private Message copyMessageExceptContent(Message message, String newContent) {
        return new Message(message.getTimestamp(), message.getSenderText(), newContent);
    }
}