package com.mindlinksoft.recruitment.mychat.optionSettings;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * The option used to hide credit card and phone numbers in messages.
 */
public class Numbers implements OptionSetting {

    /**
     * Redacts all phone numbers and all credit card numbers present from the messages.
     * @param message A message in the conversation.
     * @return The message, with credit card/phone numbers retracted.
     */
    @Override
    public Message duringIteration(Message message) {
        String phoneFinder = "(\\d{11}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4})";
        String creditCardFinder = "((?:(?:\\d{4}[- ]){3}\\d{4}|\\d{16}))(?![\\d])";

        message.content = message.content.replaceAll(phoneFinder, "*redacted").replaceAll(creditCardFinder, "*redacted*");

        return message;
    }

    /**
     * After iterating through every message in a conversation this is called.
     */
    @Override
    public Conversation postIteration(Conversation conversation) {
        return conversation;
    }

    /**
     * The option in question does not require an argument, meaning that no action is taken.
     */
    @Override
    public void setArgument(String argument) { return; }

    /**
     * Returns a boolean to state whether the option in question needs an argument.
     */
    @Override
    public boolean argumentRequired() {
        return false;
    }
}

