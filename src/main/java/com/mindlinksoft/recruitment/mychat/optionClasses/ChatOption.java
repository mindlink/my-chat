package com.mindlinksoft.recruitment.mychat.optionClasses;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Interface used to enforce class structure of classes representing commandline options.
 */
public interface ChatOption {
    /**
     * Applied to each message before converting conversation to JSON in {@link com.mindlinksoft.recruitment.mychat.ConversationExporter}.
     * @param message A message in the conversation.
     * @return The message having the option applied to it
     */
    Message applyDuring(Message message);
    /**
     * Some options require additional processing after inspecting each message, this method is called after "applyDuring"
     * just before the conversation is converted to JSON in {@link com.mindlinksoft.recruitment.mychat.ConversationExporter}.
     * @param conversation The conversation being converted to JSON.
     * @return The conversation having final stages of option applied to it (attaching additional information to be included
     * in the JSON output).
     */
    Conversation applyAfter(Conversation conversation);
    /**
     * Pass the argument provided for this option from the commandline
     * @param argument The option argument.
     */
    void setArgument(String argument);
    /**
     * Determines if this option needs an argument or not (essentially distinguishing between an option and a flag).
     * @return True => Option requires an argument, False => option doesn't require an argument (is a flag)
     */
    boolean needsArgument();
}
