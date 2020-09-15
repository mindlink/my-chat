package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;
import com.mindlinksoft.recruitment.mychat.exceptions.WrongCommandException;
import java.util.stream.Collectors;

public class EssentialFeatures {

    /**
     * Filters the conversation for messages of the senderId
     *
     * @param conversation containing all the messages
     * @param senderId     is the user whose messages should be selected for the output
     * @returns the conversation containing only the specified user's message
     */
    private static Conversation filterByUser(Conversation conversation, String senderId) {
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
     * @param targetWords  to analyse messages and redact any target word
     * @returns the conversation with redacted words
     */
    private static Conversation redactWords(Conversation conversation, String targetWords) {
        conversation.messages =
                conversation.messages.stream()
                        .map(m -> redactMessageContent(m, targetWords))
                        .collect(Collectors.toList());
        return conversation;
    }

    /**
     * Replaces any target word with *redacted*
     *
     * @param m      is the current Message
     * @param target is the word to analyse
     * @returns the message with redacted content if a match was found
     */
    private static Message redactMessageContent(Message m, String target) {
        String[] wordsToRedact = target.split(",");
        for (String wordToRedact : wordsToRedact) {
            m.content = m.content.replaceAll(wordToRedact, "*redacted*").trim();
        }
        return m;
    }

    /**
     * Analyses which command should be applied
     *
     * @param conversation containing all messages
     * @param data         is the command
     * @returns the conversation with the applied command
     */
    public static Conversation applyFilters(Conversation conversation, String data) throws Exception {

        String commandCase = data.split(":")[0];
        String target = data.split(":")[1];
        Conversation result = null;

        switch (commandCase) {
            case "user": result = filterByUser(conversation, target);break;
            case "word": result = filterByWord(conversation, target);break;
            case "redact": result = redactWords(conversation, target);break;
            default: throw new WrongCommandException("Please specify a command that is available.");
        }
        return result;
    }
}
