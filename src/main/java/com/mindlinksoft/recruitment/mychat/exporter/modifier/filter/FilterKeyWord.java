package com.mindlinksoft.recruitment.mychat.exporter.modifier.filter;

import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;

public class FilterKeyWord implements Filter {

    /**
     * The key word to filter
     */
    private final String keyWord;
    
    /**
     * The conversation to be filtered
     */
    private final Conversation conversation;

    /**
     * Returns a filter that selects the given key word
     * @param conversation contains the messages you wish to filter
     * @param keyWord the key word to filter
     */
    public FilterKeyWord(Conversation conversation, String keyWord) {
        this.conversation = conversation;
        this.keyWord = keyWord;
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
     * Creates a conversation of the same name and no messages
     * @return an empty conversation
     */
    private Conversation createConversation() {
        return new Conversation(conversation.getName(), new ArrayList<Message>());
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
            if (content.contains(keyWord)) {
                resultMessages.add(message);
            }
        }
    }
}