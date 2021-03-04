package com.mindlinksoft.recruitment.juliankubelec.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sub-class of ConversationBuilder specifically for redacting information
 */
public class RedactedConversationBuilder extends ConversationBuilder{
    public RedactedConversationBuilder(Conversation conversation) {
        super(conversation);
    }

    /**
     * This function replaces any blacklisted word with "*redacted*"
     * @param word The blacklisted word to replace
     */
    public RedactedConversationBuilder byBlacklistedWord(String word) {
        String redacted = "*redacted*";
        String regex = buildRegex(word);
        List<Message> newMessages = new ArrayList<>();
        for(Message msg: conversation.getMessages()) {
            Message newMessage = new Message(msg.getTimestamp(),
                    msg.getSenderId(),
                    msg.getContent().replaceAll(regex, redacted)
            );
            newMessages.add(newMessage);
        }
        conversation = new Conversation(conversation.getName(), newMessages);
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
