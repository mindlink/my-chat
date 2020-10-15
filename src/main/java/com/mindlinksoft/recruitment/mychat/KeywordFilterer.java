package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a filtering-by-keyword processor
 *
 * Responsibilities: Filter by the given keyword the conversation also given
 *                   and store the result
 *
 * NB: Does not mutate given conversation. (Creates shallow copy)
 */

public class KeywordFilterer implements ProcessingCapable {

    private Conversation processedConversation;
    private Conversation receivedConversation;
    private String keyword;

    public KeywordFilterer(Conversation conversation, String keyword) {
        this.receivedConversation = conversation;
        this.keyword = keyword;
        this.processedConversation = new Conversation(this.receivedConversation.name);
    }

    /**
     * Function that filters
     */
    @Override
    public void process() {
        if(this.keyword != null) {
            String keyword = this.keyword;
            for (Message msg : this.receivedConversation.messages) {
                if (msg.content.contains(keyword)) {
                    this.processedConversation.messages.add(msg);
                }
            }
        }
    }

    @Override
    public Conversation getProcessedConversation() {
        return this.processedConversation;
    }
}
