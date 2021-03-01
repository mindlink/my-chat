package com.mindlinksoft.recruitment.juliankubelec.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to manipulate the conversation according to command-line arguments
 */
public class ConversationBuilder {
    private final Conversation conversation;

    /**
     * Initialises instance of a ConversationConfigurator
     * @param conversation The conversation that will be exported
     */
    public ConversationBuilder(Conversation conversation){
        this.conversation = conversation;
    }

    /**
     * @return the configurator's conversation
     */
    public Conversation build(){
        return this.conversation;
    }

    /**
     * This function replaces the current conversation with a filtered conversation
     * that only contains messages from senderId = userId
     * @param userId used as a filter
     */
    public ConversationBuilder filterByUser(String userId) {
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
    public ConversationBuilder filterByKeyword(String keyword) {
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

    /**
     * This function replaces any blacklisted word with "*redacted*"
     * @param word The blacklisted word to replace
     */
    public ConversationBuilder blacklistWord(String word) {
        String redacted = "*redacted*";
        List<Message> messages = (List<Message>)conversation.messages;
        String regex = buildRegex(word);

        for(Message msg:messages) {
            msg.content = msg.content.replaceAll(regex, redacted);
        }
        return this;
    }

    /**
     * Creates a regrex that finds individual words.
     * The regex made is '\b[aA]word\b'
     * where a is the first letter (lower case version) of the word
     * A is the first letter (upper case version) of the word
     * @param word The word to test for
     * @return the regex in form mentioned above
     */
    private String buildRegex(String word) {
        StringBuilder sb = new StringBuilder(word);
        String res;
        char firstUpper = word.toUpperCase().toCharArray()[0];
        char firstLower = word.toLowerCase().toCharArray()[0];
        String set = "["+firstUpper +firstLower +"]";
        sb.deleteCharAt(0);
        res = "\\b"+ set + sb.toString() + "\\b";
        return res;
    }
}
