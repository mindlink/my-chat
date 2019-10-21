package com.mindlinksoft.recruitment.mychat.optionClasses;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * ChatOption used to hide credit card and phone numbers in messages.
 */
public class PhoneCardFilter implements ChatOption {

    /**
     * Redacts credit card/phone numbers from this messages content, if present.
     * @param message A message in the conversation.
     * @return The message, with credit card/phone numbers retracted.
     */
    @Override
    public Message applyDuring(Message message) {

        //regex used to match credit card and phone numbers
        String phoneNumberRegex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        String creditCardNumberRegex = "((?:(?:\\d{4}[- ]){3}\\d{4}|\\d{16}))(?![\\d])";

        message.content = message.content.replaceAll(phoneNumberRegex, "*redacted*");
        message.content = message.content.replaceAll(creditCardNumberRegex, "*redacted*");

        return message;
    }

    /**
     * Called after looping through all conversation messages. Irrelevant to this option.
     */
    @Override
    public Conversation applyAfter(Conversation conversation) {
        return conversation;
    }

    /**
     * Set option argument (this option doesn't require an argument so no action is taken).
     */
    @Override
    public void setArgument(String argument) { return; }

    /**
     * Returns a boolean representing whether this option requires an argument.
     */
    @Override
    public boolean needsArgument() {
        return false;
    }
}
