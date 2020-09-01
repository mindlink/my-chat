package com.mindlinksoft.recruitment.mychat.optionSettings;

import com.mindlinksoft.recruitment.mychat.Message;
import com.mindlinksoft.recruitment.mychat.Conversation;

/**
 * The option used to filter a conversation to only return messages containing a specified keyword.
 */
public class Keywords implements OptionSetting {
    /**
     * Stores the keyword string.
     */
    public static String keyword;

    /**
     * If the specified keyword is not present, this removes the current message from the conversation.
     * @param message A message in the conversation.
     * @return The current message if the specified keyword is present, and null if not (removing the message from the conversation).
     */
    @Override
    public Message duringIteration(Message message) {
        if (message.content.contains(keyword)) {
            return message;
        } else {
            return null;
        }
    }

    /**
     * After iterating through every message in a conversation this is called.
     */
    @Override
    public Conversation postIteration(Conversation conversation) {
        return conversation;
    }

    /**
     * Sets the option argument (the keyword).
     */
    @Override
    public void setArgument(String argument) { keyword = argument; }

    /**
     * Returns a boolean to state whether the option in question needs an argument.
     */
    @Override
    public boolean argumentRequired() {
        return true;
    }
}