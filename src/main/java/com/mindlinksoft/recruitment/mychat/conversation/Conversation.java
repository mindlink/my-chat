package main.java.com.mindlinksoft.recruitment.mychat.conversation;

import java.util.Collection;
import java.util.Map;

import main.java.com.mindlinksoft.recruitment.mychat.message.Message;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {

    private final String name;

    private final Collection<Message> messages;
    
    private final Map<String, Map<String, Integer>> extraData;

    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
        this.extraData = null;
    }
    
    public Conversation(String name, Collection<Message> messages, Map<String, Map<String, Integer>> extraData) {
        this.name = name;
        this.messages = messages;
        this.extraData = extraData;
    }

	public String getName() {
		return name;
	}

	public Collection<Message> getMessages() {
		return messages;
	}
	
	public Map<String, Map<String, Integer>> getExtraLines() {
		return extraData;
	}
}
