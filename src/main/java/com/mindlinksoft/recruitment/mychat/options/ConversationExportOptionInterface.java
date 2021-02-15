package com.mindlinksoft.recruitment.mychat.options;

import com.mindlinksoft.recruitment.mychat.models.Conversation;


public interface ConversationExportOptionInterface {
    /**
     * A interface method for all export option classes to implement
     * To process a given {@code conversation} and apply the particular export option
     * @param conversation  The {@link Conversation} to be processed
     * @return Conversation The processed {@link Conversation}
     */
    Conversation process(Conversation conversation);
}
