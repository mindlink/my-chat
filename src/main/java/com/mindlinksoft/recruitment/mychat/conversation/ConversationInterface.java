package com.mindlinksoft.recruitment.mychat.conversation;

import com.mindlinksoft.recruitment.mychat.message.MessageInterface;

import java.util.Collection;


/**
 * Conversation Interface must be implemented by conversation objects
 */
public interface ConversationInterface {
    /**
     * method definition to get the name of the conversation
     * @return String the name of the conversation
     */
    String getName();

    /**
     * method definition to get message collection
     * @return Collection of message
     */
    Collection<MessageInterface> getMessages();

    /**
     * method definition to setup active user report
     */
    void setupActiveUserReport();

    /**
     * method definition to set the name of a conversation
     * @param name String the name of the conversation
     */
    void setName(String name);

    /**
     * method definition to set messages
     * @param messages Collection of message objects that implement {@link MessageInterface}
     */
    void setMessages(Collection<MessageInterface> messages);
}
