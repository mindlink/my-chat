package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

public class ConversationFilterer {

    /**
     * Filters a conversation to only contain messages sent by the specified user
     * @param conversation The conversation to filter.
     * @param user The user whose messages are being kept.
     */
    public void filterConversationByUser(Conversation conversation, String user) {
        List<Message> messagesToDelete = new ArrayList<>();

        // set up message for removal if its sender is not the same as the user specified (ignores case if users have capitalised names, for example)
        for (Message message : conversation.getMessages()) {
            if (!message.getSenderId().equalsIgnoreCase(user)) {
                messagesToDelete.add(message);
            }
        }
        // remove those messages
        conversation.getMessages().removeAll(messagesToDelete);

    }

    /**
     * Filters a conversation to only contain messages that contain the specified keyword
     * @param conversation The conversation to filter.
     * @param keyword The keyword whose containing messages are being kept.
     */
    public void filterConversationByKeyword(Conversation conversation, String keyword) {
        List<Message> messagesToDelete = new ArrayList<>();

        // set up message for removal if it does not contain the keyword specified
        for (Message message : conversation.getMessages()) {
            if (!message.getContent().contains(keyword)) {
                messagesToDelete.add(message);
            }
        }
        // remove those messages
        conversation.getMessages().removeAll(messagesToDelete);
    }

    /**
     * Filters a conversation to only contain messages that contain the specified keyword
     * @param conversation The conversation to filter.
     * @param blacklistedWords The words to be redacted from all messages.
     */
    public void redactBlacklistedWords(Conversation conversation, String[] blacklistedWords) {
        for (String blacklistedWord : blacklistedWords) {
            for (Message message : conversation.getMessages()) {
                if (message.getContent().contains(blacklistedWord)) {
                    // indexes of the start and end of the blacklisted word in the message
                    int wordStartIndex = message.getContent().indexOf(blacklistedWord);
                    int wordEndIndex = wordStartIndex + blacklistedWord.length();
                    // get the parts of the message that come before and after the blacklisted word
                    String messageBeforeWord = message.getContent().substring(0, wordStartIndex);
                    String messageAfterWord = message.getContent().substring(wordEndIndex);
                    // replace the message with post-redaction message
                    message.setContent(messageBeforeWord + "*redacted*" + messageAfterWord);
                }
            }
        }
    }
}
