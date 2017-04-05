package com.mindlinksoft.recruitment.mychat.conversation;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;

import com.mindlinksoft.recruitment.mychat.message.IMessage;

/**
 * Represents the model of a conversation.
 */
public final class Conversation implements IConversation {
    /**
     * The name of the conversation.
     */
    private String name;

    /**
     * The messages in the conversation.
     */
    private Collection<IMessage> messages;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<IMessage> messages) {
        this.name = Validate.notEmpty(name);
        this.messages = Validate.notNull(messages);
    }

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<IMessage> getMessages() {
		return messages;
	}
	
	@Override
	public Map<String, Long> getUserActivity() {
		Map<String, Long> userActivity = new LinkedHashMap<String, Long>();
		messages
			.stream()
			.collect(Collectors.groupingBy(m -> m.getSenderId(), Collectors.counting()))
			.entrySet().stream()
			.sorted(Map.Entry.<String, Long>comparingByValue().reversed())
			.forEachOrdered(e -> userActivity.put(e.getKey(), e.getValue()));
		return userActivity;
	}
}
