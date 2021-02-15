package com.mindlinksoft.recruitment.mychat.filters;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

import java.util.Iterator;

public class KeywordFilter implements ConversationFilter{

    private Conversation conversation;
    private String argument, keyword;

    public KeywordFilter(Conversation conversation, String argument) {
        this.conversation = conversation;
        this.argument = argument;
    }

    public Conversation filterConversation(){

        keyword = argument.substring(18).trim();

        Iterator<Message> i = conversation.messages.iterator();

        System.out.println("KEYWORD: " + keyword);

        while(i.hasNext()){
            Message m = i.next();
            if (!(m.content.contains(keyword))){
                i.remove();
            }
        }

        return conversation;

    }
}
