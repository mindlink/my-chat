package com.mindlinksoft.recruitment.mychat.editor.formatters;

import com.mindlinksoft.recruitment.mychat.message.MessageInterface;

/**
 * Conversation formatter interface
 * New formatters must implement this interface
 */
public interface MessageFormatterInterface {
    /**
     * method definition to format message
     * must be implemented by formatters
     * @param message object that implements {@link MessageInterface}
     * @return a formatted message object that implements {@link MessageInterface}
     */
    MessageInterface formatMessage(MessageInterface message);
}
