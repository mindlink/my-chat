package com.mindlinksoft.recruitment.mychat.optionClasses;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * ChatOption used to obfuscate userIDs from all messages in a conversation, whilst maintaining message <-> sender relationships.
 */
public class UserObfuscate implements ChatOption {

    /**
     * Obfuscate the userID of this message.
     * @param message A message in the conversation.
     * @return The message, with the userID obfuscated.
     */
    @Override
    public Message applyDuring(Message message) {
        message.senderId = String.valueOf(message.senderId.hashCode());
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
