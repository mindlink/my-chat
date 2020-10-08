package com.mindlinksoft.recruitment.mychat;

public class Filterer {
    /**
     * Filter the conversation based on the conversation configuration
     * The order of filterUser and filterKeyword doesn't matter 
     * however, blacklistWords must be after filterKeywords to fully work.
     * @param conversation The conversation that needs filtering
     * @param config The configuration of the conversation
     */
    public Conversation filterConversation(Conversation conversation, ConversationExporterConfiguration config) throws Exception {
        if(config.filterUser != null) {
            conversation = new FilterByUser().filterConversation(conversation, config);
        }

        if(config.filterKeyword != null) {
            conversation = new FilterByKeyword().filterConversation(conversation, config);
        }

        if(config.blacklistWords != null) {
            conversation = new FilterByBlacklist().filterConversation(conversation, config);
        }
        
        return conversation;
    }
}