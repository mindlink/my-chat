package com.mindlinksoft.recruitment.mychat.exporter.modifier.hide;

import java.util.List;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

/**
 * Represents a modifier that hides certain key words in messages
 */
public class HideKeyWord extends ModifierBase implements Hide {

    /**
     * The key word to hide
     */
    private final String keyWord;

    /**
     * Returns a modifier that hides certain key words in messages
     * @param conversation contains the messages you wish to hide the key word from
     * @param keyWord the key word to hide
     */
    public HideKeyWord(Conversation conversation, String keyWord) {
        super(conversation);
        this.keyWord = keyWord;
    }

    @Override
    protected Conversation modify() {
        return hide();
    }
    
    /**
     * Creates a new Conversation with the key words hidden
     * @return filtered Conversation
     */
    @Override
    public Conversation hide() {
        Conversation resultConversation = createConversation();
        List<Message> resultMessages = resultConversation.getMessages();
        List<Message> messages = conversation.getMessages();
        hideMessages(messages, resultMessages);
        return resultConversation;
    }

    /**
     * Helper method which adds old messages to the new messages
     * if it contains this keyword
     * @param oldMessages the messages to be filtered
     * @param resultMessages the message filtered by this sender
     */
    private void hideMessages(List<Message> oldMessages, List<Message> resultMessages) {
        for (int i = 0; i < oldMessages.size(); i++) {
            Message message = oldMessages.get(i);
            String content = message.getContent();
            String modifiedContent = modifyString(content);
            Message modifiedMessage = copyMessageExceptContent(message, modifiedContent);
            resultMessages.add(modifiedMessage);
        }
    }

    private Message copyMessageExceptContent(Message message, String newContent) {
        return new Message(message.getTimestamp(), message.getSenderText(), newContent);
    }

    private String modifyString(String content) {
        return content.replaceAll("(?i)\\b" + keyWord + "\\b", "*redacted*");
    }

}