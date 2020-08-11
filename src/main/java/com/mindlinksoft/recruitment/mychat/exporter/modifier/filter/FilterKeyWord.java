package com.mindlinksoft.recruitment.mychat.exporter.modifier.filter;

import java.util.List;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

/**
 * Represents a filter that selects a certain key word
 */
public class FilterKeyWord extends ModifierBase implements Filter {

    /**
     * The key word to filter
     */
    private final String[] keyWords;

    /**
     * Returns a filter that selects the given key word
     * @param conversation contains the messages you wish to filter
     * @param keyWord the key word to filter
     */
    public FilterKeyWord(Conversation conversation, String[] keyWords) {
        super(conversation);
        this.keyWords = keyWords;
    }

    @Override
    protected Conversation modify() {
        return filter();
    }

    /**
     * Creates a new Conversation that only contains
     * messages with this key word
     * @return filtered Conversation
     */
    @Override
    public Conversation filter() {
        Conversation resultConversation = createConversation();
        List<Message> resultMessages = resultConversation.getMessages();
        List<Message> messages = conversation.getMessages();
        filterMessages(messages, resultMessages);
        return resultConversation;
    }

    /**
     * Helper method which adds old messages to the new messages
     * if it contains this keyword
     * @param oldMessages the messages to be filtered
     * @param resultMessages the message filtered by this sender
     */
    private void filterMessages(List<Message> oldMessages, List<Message> resultMessages) {
        for (int i = 0; i < oldMessages.size(); i++) {
            Message message = oldMessages.get(i);
            String content = message.getContent();
            for (int j = 0; j < keyWords.length; j++) {
                if (content.contains(keyWords[j])) {
                    resultMessages.add(message);
                }
            }
        }
    }
}