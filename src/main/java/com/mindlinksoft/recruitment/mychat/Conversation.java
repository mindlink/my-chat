package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import com.google.gson.annotations.*;
/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    @Expose public String name;

    /**
     * The messages in the conversation.
     */
    @Expose public Collection<Message> messages;
	
	/**
	* The report for each user
	*/
	@Expose public Collection<UserReport> activity;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
	 * @param activity The reports for each user in the conversation.
     */
    public Conversation(String name, Collection<Message> messages, Collection<UserReport> activity) {
        this.name = name;
        this.messages = messages;
		this.activity = activity;
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
