package com.mindlinksoft.recruitment.mychat.optionClasses;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * ChatOption used to add replace argumentValue with "*redacted*" in a conversation.
 */
public class BlacklistFilter implements ChatOption {

    /**
     * Used to store the string that is blacklisted from the conversation.
     */
    public static String argumentValue;

    /**
     * Remove the blacklisted word from this message, if it is present.
     * @param message A message in the conversation.
     * @return The message with the blacklisted word retracted.
     */
    @Override
    public Message applyDuring(Message message) {
        message.content = message.content.replace(argumentValue, "*redacted*");
        return  message;
    }

    /**
     * Called after looping through all conversation messages. Irrelevant to this option
     */
    @Override
    public Conversation applyAfter(Conversation conversation) {
        return conversation;
    }

    /**
     * Set option argument, in this case the word to be blacklisted.
     */
    @Override
    public void setArgument(String argument) { argumentValue = argument; }

    /**
     * Returns a boolean representing whether this option requires an argument.
     */
    @Override
    public boolean needsArgument() {
        return true;
    }
}
