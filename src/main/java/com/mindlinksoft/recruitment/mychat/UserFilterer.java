package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a filtering processor (by user)
 *
 * Responsibilities: Filter by user the conversation given and store it
 *
 * NB: Does not mutate given conversation. (Creates shallow copy)
 */
public class UserFilterer implements ProcessingCapable {

    private Conversation processedConversation;
    private Conversation receivedConversation;
    private String userName;

    public UserFilterer(Conversation conversation, String userName) {
        this.receivedConversation = conversation;
        this.userName = userName;
        this.processedConversation = new Conversation(this.receivedConversation.name);
    }

    /**
     * Function that filters
     */
    @Override
    public void process() {

        if(this.userName != null){
                String user = this.userName;
                for(Message msg : this.receivedConversation.messages){
                    if(msg.senderId.equals(user)){
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
