package com.mindlinksoft.recruitment.juliankubelec.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to manipulate the conversation according to command-line arguments
 */
public class ConversationBuilder {
    protected Conversation conversation;

    /**
     * Initialises instance of a ConversationConfigurator
     * @param conversation The conversation that will be exported
     */
    public ConversationBuilder(Conversation conversation){
        this.conversation = conversation;
    }

    /**
     * @return the configurator's conversation
     */
    public Conversation build(){
        return this.conversation;
    }


    FilteredConversationBuilder filter(){
        return new FilteredConversationBuilder(conversation);
    }

    RedactedConversationBuilder redact() {
        return new RedactedConversationBuilder(conversation);
    }
}
