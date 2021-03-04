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
        List<Message> messages = (List<Message>)conversation.messages;
        List<Message> newMessages = new ArrayList<>();
        for(Message msg: messages) {
            if(msg.senderId.equals(userId)) {
                newMessages.add(msg);
            }
        }
        conversation.messages = newMessages;
        return this;
    }

    /**
     * This function removes messages that don't contain keyword
     * @param keyword used as a filter
     */
    public ConversationBuilder byKeyword(String keyword) {
        List<Message> messages = (List<Message>)conversation.messages;
        List<Message> newMessages = new ArrayList<>();
        for(Message msg: messages) {
            if(msg.content.contains(keyword)) {
                newMessages.add(msg);
            }
        }
        conversation.messages = newMessages;
        return this;
    }

}
