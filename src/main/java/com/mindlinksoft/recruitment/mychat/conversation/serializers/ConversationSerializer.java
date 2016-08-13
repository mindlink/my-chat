package main.java.com.mindlinksoft.recruitment.mychat.conversation.serializers;

import main.java.com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import main.java.com.mindlinksoft.recruitment.mychat.exceptions.SerializationException;

public interface ConversationSerializer {
	public String serialize(Conversation conversation) throws SerializationException;
}
