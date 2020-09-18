package com.mindlinksoft.recruitment.mychat.features;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.utils.AppInformation;

import java.util.stream.Collectors;

public class EssentialFeatures {

    /**
     * Filters the conversation for messages of the senderId
     *
     * @param conversation containing all the messages
     * @param senderId     is the user whose messages should be selected for the output
     * @returns the conversation containing only the specified user's message
     */
    public static Conversation filterByUser(Conversation conversation, String senderId) {
        conversation.messages =
                conversation.messages.stream()
                        .filter(m -> m.senderId.equals(senderId))
                        .collect(Collectors.toList());
        return conversation;

    }

    /**
     * Filters the conversation for messages containing a specific word
     *
     * @param conversation containing all the messages
     * @param word         to analyse messages, which should be selected for the output
     * @returns the conversation containing only messages which contain the word
     */
    public static Conversation filterByWord(Conversation conversation, String word) {
        conversation.messages =
                conversation.messages.stream()
                        .filter(m -> m.content.toLowerCase().contains(word.toLowerCase()))
                        .collect(Collectors.toList());
        return conversation;
    }

    /**
     * Redacts words from messages from a given target word
     *
     * @param conversation containing all the messages
     * @param targetWord   to analyse messages and redact any target word
     * @returns the conversation with redacted words
     */
    public static Conversation redactWords(Conversation conversation, String targetWord) {
        conversation.messages =
                conversation.messages.stream()
                        .peek(m -> m.content = m.content.replaceAll(targetWord, AppInformation.REDACT_VALUE))
                        .collect(Collectors.toList());
        return conversation;
    }
}
