package com.mindlinksoft.recruitment.mychat.Model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {

    //Making variables final (and private for messages) to make the class immutable.

    /**
     * The name of the conversation.
     */
    public final String name;

    /**
     * The messages in the conversation.
     */
    private final Collection<Message> messages;

    //By returning a copy of the original list, this prevents people from modifing the contents of the messages variable
    //Note: Messages are immutable as well, so one still can't change the elements of the orginal list by ref shenanigans.
    public Collection<Message> getMessages() {
        return new ArrayList<Message>(messages);
    }

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

}
