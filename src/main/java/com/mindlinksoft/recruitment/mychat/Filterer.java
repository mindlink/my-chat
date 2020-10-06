package com.mindlinksoft.recruitment.mychat;

public class Filterer {
    /**
     * Filter the conversation based on the conversation configuration
     * @param conversation The conversation that needs filtering
     * @param config The configuration of the conversation
     */
    public Conversation filterConversation(Conversation conversation, ConversationExporterConfiguration config) throws Exception {
        if(config.filterUser != null) {
            conversation = new FilterByUser().filterConversation(conversation, config.filterUser);
        }

        if(config.filterKeyword != null) {
            conversation = new FilterByKeyword().filterConversation(conversation, config.filterKeyword);
        }
        
        return conversation;
    }
}