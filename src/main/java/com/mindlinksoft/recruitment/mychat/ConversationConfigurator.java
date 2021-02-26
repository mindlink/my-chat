package com.mindlinksoft.recruitment.mychat;

/**
 * Used to manipulate the conversation according to command-line arguments
 */
public class ConversationConfigurator {
    private Conversation conversation;
    private String redacted = "*redacted*";
    /**
     * Initialises instance of a ConversationConfigurator
     * @param conversation The conversation that will be exported
     */
    public ConversationConfigurator(Conversation conversation){
        this.conversation = conversation;
    }

    /**
     * @return the configurator's conversation
     */
    public Conversation getConversation(){
        return this.conversation;
    }

    /**
     * This function removes messages that don't have userId
     * @param userId used as a filter
     */
    public void filterByUser(String userId)
    {
        //TODO: Complete implementation of filterByUser
    }

    /**
     * This function removes messages that don't contain keyword
     * @param keyword used as a filter
     */
    public void filterByKeyword(String keyword)
    {
        //TODO: Complete implementation of filterByKeyword
    }

    /**
     * This function replaces any blacklisted word with "*redacted*"
     * @param word
     */
    public void blacklistWord(String word)
    {
        //TODO: Complete implementation of blacklistWord
    }
}
