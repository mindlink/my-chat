package com.mindlinksoft.recruitment.mychat.optionSettings;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * OptionSetting is used to obfuscate the userIDs in a conversation.
 */
public class Obfuscate implements OptionSetting {

    /**
     * Obfuscate the userID of this message.
     * @param message A message in the conversation.
     * @return The same message with the name of the user (userID) obfuscated.
     */
    @Override
    public Message duringIteration(Message message) {
        message.userID = String.valueOf(message.userID.hashCode());
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
