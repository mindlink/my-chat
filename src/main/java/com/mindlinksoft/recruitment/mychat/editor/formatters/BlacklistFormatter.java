package com.mindlinksoft.recruitment.mychat.editor.formatters;

import com.mindlinksoft.recruitment.mychat.message.MessageInterface;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Formatter to format message containing blacklisted words
 */
public class BlacklistFormatter implements MessageFormatterInterface{

    /**
     * blacklisted words
     * comma separated list as passed in from the command line the form: word1,word2,word3,...
     */
    private String words;
    /**
     * regExPattern to be generated using words passed
     */
    private String regExPattern;

    /**
     * BlacklistFormatter Constructor
     * @param words replace.
     * Calls private method setupRegex on instantiation
     */
    public BlacklistFormatter(String words){
        this.words = words;
        this.setupRegex(this.words);
    }

    /**
     * Formatted that replaces blacklisted words with *redacted*
     * @param message object that implements {@link MessageInterface}
     * @return the formatted message object that implements {@link MessageInterface}
     */
    @Override
    public MessageInterface formatMessage(MessageInterface message) {

        String messageContent = message.getContent();
        messageContent = messageContent.replaceAll( "\\b(" + this.regExPattern + ")\\b" , "*redacted*");
        message.setContent(messageContent);
        return message;
    }

    /**
     * sets up the regular expression by replacing "," with "|"
     * in the passed words string
     * @param words comma separated words string
     */
    private void setupRegex(String words){
        this.regExPattern = words.replaceAll(",", "|");
    }
}
