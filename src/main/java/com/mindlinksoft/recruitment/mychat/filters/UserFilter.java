package com.mindlinksoft.recruitment.mychat.filters;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

import java.util.Iterator;

public class UserFilter implements ConversationFilter {

    private Conversation conversation;
    private String argument, user;

    public UserFilter(Conversation conversation, String argument) {
        this.conversation = conversation;
        this.argument = argument;
    }

    public Conversation filterConversation(){

        user = argument.substring(15).trim();

        Iterator<Message> i = conversation.messages.iterator();

        while(i.hasNext()){
            Message m = i.next();
            if (!(m.senderId.equalsIgnoreCase(user))){
                i.remove();
            }
        }

        return conversation;

    }

}
