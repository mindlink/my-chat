package com.mindlinksoft.recruitment.mychat.ConversationFilterers;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

import java.util.ArrayList;
import java.util.List;

public class BlacklistedWordFilterer extends Filterer {

    private String[] blacklistedWords;

    public void setBlacklistedWords(String[] blacklistedWords) {
        this.blacklistedWords = blacklistedWords;
    }

    /**
     * Filters a conversation to only contain messages that contain the specified keyword
     * @param conversation The conversation to filter.
     */
    public Conversation filter(Conversation conversation) {
        // if there are no blacklisted words, or if the array is null, return original conversation
        if (blacklistedWords == null || blacklistedWords.length == 0) {
            return conversation;
        }

        String messageBeforeWord, messageAfterWord, redactedString;
        int wordStartIndex, wordEndIndex;
        List<Message> filteredMessages = new ArrayList<>();

        for (Message message : conversation.getMessages()) {
            redactedString = message.getContent();
            for (String blacklistedWord : blacklistedWords) {
                if (redactedString.contains(blacklistedWord)) {
                    // indexes of the start and end of the blacklisted word in the message
                    wordStartIndex = redactedString.indexOf(blacklistedWord);
                    wordEndIndex = wordStartIndex + blacklistedWord.length();
                    // get the parts of the message that come before and after the blacklisted word
                    messageBeforeWord = redactedString.substring(0, wordStartIndex);
                    messageAfterWord = redactedString.substring(wordEndIndex);
                    // replace the message with post-redaction message
                    redactedString = messageBeforeWord + "*redacted*" + messageAfterWord;
                }
            }
            // add redacted string to new list of filtered messages
            filteredMessages.add(new Message(message.getTimestamp(), message.getSenderId(), redactedString));
        }

        // create a new conversation object with the filtered messages
        Conversation filteredConversation = new Conversation(conversation.getName(), filteredMessages);

        return filteredConversation;
    }
}
