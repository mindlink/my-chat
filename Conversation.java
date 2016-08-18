package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    private String name;

    /**
     * The messages in the conversation.
     */
    private Collection<Message> messages;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
     
    public String getName() {
		return name;
	}

    //In case user wants to change the conversation name
	public void setName(String name) {
		this.name = name;
	}

	public Collection<Message> getMessages() {
		return messages;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(name + "\n");
        for (Message mes : messages) {
            sb.append(mes.toString());
        }

        return sb.toString();
    }

}
