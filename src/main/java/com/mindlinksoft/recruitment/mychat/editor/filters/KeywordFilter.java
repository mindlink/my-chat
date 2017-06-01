package com.mindlinksoft.recruitment.mychat.editor.filters;

import com.mindlinksoft.recruitment.mychat.conversation.ConversationInterface;
import com.mindlinksoft.recruitment.mychat.message.MessageInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter to filter messages by keyword
 */
public class KeywordFilter implements ConversationFilterInterface {

    /**
     * The keyword to filter
     */
    private String keyword;

    /**
     * List to add filtered messages
     */
    private List<MessageInterface> filteredMessages = new ArrayList<>();

    /**
     * KeywordFilter Constructor.
     * @param keyword to filter with
     */
    public KeywordFilter( String keyword ){
        this.keyword = keyword;
    }

    /**
     * method to filter conversation by the keyword specified when instantiating the filter
     * @param conversation object that implements {@link ConversationInterface}.
     * @return the filtered conversation object that implements {@link ConversationInterface}
     */
    public ConversationInterface filterConversation(ConversationInterface conversation){

        Collection<MessageInterface> messages = conversation.getMessages();

        String patternString = "\\b(" + this.keyword + ")\\b";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher;
        for( MessageInterface message : messages){
            matcher = pattern.matcher(message.getContent());
            if( matcher.find() ) {
                this.filteredMessages.add(message);
            }
        }
        conversation.setMessages(this.filteredMessages);
        return conversation;
    }
}
