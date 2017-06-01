package com.mindlinksoft.recruitment.mychat.editor.filters;

import com.mindlinksoft.recruitment.mychat.conversation.ConversationInterface;
import com.mindlinksoft.recruitment.mychat.message.MessageInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Filter to filter messages by user name
 */
public class UserNameFilter implements ConversationFilterInterface{

    /**
     * The user name to filter
     */
    private String userName;

    /**
     * List to add filtered messages
     */
    private List<MessageInterface> filteredMessages = new ArrayList<>();

    /**
     * UserNameFilter Constructor.
     * @param userName to filter with
     */
    public UserNameFilter( String userName ){
        this.userName = userName;
    }

    /**
     * method to filter conversation by userName specified when instantiating the filter
     * @param conversation object that implements {@link ConversationInterface}.
     * @return the filtered conversation object that implements {@link ConversationInterface}
     */
    public ConversationInterface filterConversation(ConversationInterface conversation){

        Collection<MessageInterface> messages = conversation.getMessages();

        for( MessageInterface message : messages){
            if( message.getSenderId().equals(this.userName) ) {
                this.filteredMessages.add(message);
            }
        }
        conversation.setMessages(this.filteredMessages);
        return conversation;
    }
}
