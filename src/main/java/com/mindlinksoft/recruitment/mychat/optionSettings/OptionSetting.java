package com.mindlinksoft.recruitment.mychat.optionSettings;

import com.mindlinksoft.recruitment.mychat.Message;
import com.mindlinksoft.recruitment.mychat.Conversation;

/**
 * An interface for the commandline options.
 */
public interface OptionSetting {
    /**
     * Prior to JSON conversion of conversation using ConversationExporter, this is applied to each message.
     * @param message A message in the conversation.
     * @return The message having the option applied to it
     */
    Message duringIteration(Message message);

    /**
     * Certain options necessitate additional processing post iteration, yet still before the conversation is converted to JSON.
     * @param conversation The conversation being converted to JSON.
     * @return Final work applied to a conversation prior to the JSON conversion.
     */
    Conversation postIteration(Conversation conversation);

    /**
     * Sets the option argument that is provided from commandline.
     * @param argument Option argument.
     */
    void setArgument(String argument);

    /**
     * Determines whether the option in question needs an argument.
     * @return True if the option requires an argument and False if not
     */
    boolean argumentRequired();
}