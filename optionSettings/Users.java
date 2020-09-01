package com.mindlinksoft.recruitment.mychat.optionSettings;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;


/**
 * The option used to filter a conversation to only return messages from a specific user.
 */
public class Users implements OptionSetting {

    /**
     * Stores the UserID string.
     */
    public static String argumentValue;

    /**
     * If the specified UserID is not present, this removes the current message from the conversation.
     * @param message A message in the conversation.
     * @return The current message if the specified UserID is present, and null if not (removing the message from the conversation).
     */
    @Override
    public Message duringIteration(Message message) {
        if (message.userID.equals(argumentValue)) {
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
     * Sets the option argument (the UserID).
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