package com.mindlinksoft.recruitment.juliankubelec.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Sub-class of ConversationBuilder for filtering a provided conversation
 */
public class FilteredConversationBuilder extends ConversationBuilder{

    public FilteredConversationBuilder(Conversation conversation){
        super(conversation);
    }
    /**
     * This function replaces the current conversation with a filtered conversation
     * that only contains messages from senderId = userId
     * @param userId used as a filter
     */
    public FilteredConversationBuilder byUser(String userId) {
        List<Message> newMessages = new ArrayList<>();
        Conversation newConversation;
        for(Message msg: conversation.getMessages()) {
            if(msg.getSenderId().equals(userId)) {
                newMessages.add(msg);
            }
        }
        newConversation = new Conversation(conversation.getName(), newMessages);
        conversation = newConversation;
        return this;
    }

    /**
     * This function removes messages that don't contain keyword
     * @param keyword used as a filter
     */
    public ConversationBuilder byKeyword(String keyword) {
        List<Message> newMessages = new ArrayList<>();
        for(Message msg: conversation.getMessages()) {
            if(msg.getContent().contains(keyword)) {
                newMessages.add(msg);
            }
        }
        conversation = new Conversation(conversation.getName(), newMessages);
        return this;
    }

}
