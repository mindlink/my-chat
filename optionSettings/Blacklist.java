package com.mindlinksoft.recruitment.mychat.optionSettings;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * OptionSetting is used to replace a set word (argumentValue) with "*redacted*" in a conversation.
 */
public class Blacklist implements OptionSetting {

    /**
     * Used to store the string that is blacklisted from the conversation.
     */
    public static String argumentValue;

    /**
     * If blacklisted word appears in message, replace the word with "*redacted*".
     * @param message A message in the conversation.
     * @return The same message except for the blacklisted word being redacted.
     */
    @Override
    public Message duringIteration(Message message) {
        message.content = message.content.replace(argumentValue, "*redacted*");
        return  message;
    }

    /**
     * After iterating through every message in a conversation this is called.
     */
    @Override
    public Conversation postIteration(Conversation conversation) {
        return conversation;
    }

    /**
     * Sets the option argument (the word needing blacklisting).
     */
    @Override
    public void setArgument(String argument) { argumentValue = argument; }

    /**
     * Returns a boolean to state whether the option in question needs an argument.
     */
    @Override
    public boolean argumentRequired() {
        return true;
    }
}