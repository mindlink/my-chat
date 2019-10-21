package com.mindlinksoft.recruitment.mychat.optionClasses;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * ChatOption used to only include messages from a certain userID in the conversation.
 */
public class UserFilter implements ChatOption {

    /**
     * Used to store the userID from which messages should be preserved.
     */
    public static String argumentValue;

    /**
     * Remove this message from the conversation, if the message is not sent from the right userID.
     * @param message A message in the conversation.
     * @return The message, if it is sent from the correct userID. Otherwise, null is returned (which will be removed from the conversation).
     */
    @Override
    public Message applyDuring(Message message) {
        if (message.senderId.equals(argumentValue)) {
            return message;
        } else {
            return null;
        }
    }

    /**
     * Called after looping through all conversation messages. Irrelevant to this option.
     */
    @Override
    public Conversation applyAfter(Conversation conversation) {
        return conversation;
    }

    /**
     * Set option argument, in this case the userID to include messages from.
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
