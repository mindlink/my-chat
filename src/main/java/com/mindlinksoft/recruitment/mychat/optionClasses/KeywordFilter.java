package com.mindlinksoft.recruitment.mychat.optionClasses;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * ChatOption used to return messages only if they contain a specified key word.
 */
public class KeywordFilter implements ChatOption {
    /**
     * Stores the keyword string.
     */
    public static String argumentValue;

    /**
     * Remove this message from the conversation, if the keyword is not present.
     * @param message A message in the conversation.
     * @return The message, if it contains the keyword. Otherwise, null is returned (which will be removed from the conversation).
     */
    @Override
    public Message applyDuring(Message message) {
        if (message.content.contains(argumentValue)) {
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
     * Set option argument, in this case the keyword.
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
