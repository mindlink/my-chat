/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

/**
 *
 * @author Michael
 */
public abstract class Filter {
   abstract void filterAction(Message message, Object word, Object replacement);
    Conversation conversation;

    public final Conversation Filter(Conversation c, Object word, Object replacement) throws Exception {
        conversation=c;
        Object[] conversationArray = conversation.messages.toArray();
        for (Object m : conversationArray) {
            Message message = (Message) m;
            
            filterAction(message, word, replacement);
            
        }
        return conversation;
    }
    
    
}
