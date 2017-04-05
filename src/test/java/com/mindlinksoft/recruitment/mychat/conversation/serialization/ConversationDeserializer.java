package com.mindlinksoft.recruitment.mychat.conversation.serialization;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.message.IMessage;
import com.mindlinksoft.recruitment.mychat.message.Message;

public class ConversationDeserializer implements JsonDeserializer<Conversation>{

	private static final String NAME_TAG = "name";
	private static final String MSGS_TAG = "messages";
	private static final String TIMESTAMP_TAG = "timestamp";
	private static final String SENDER_TAG = "senderId";
	private static final String CONTENT_TAG ="content";
	
	@Override
	public Conversation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		
		JsonObject jsonObject = (JsonObject) jsonElement;
		
		if (!jsonObject.has(NAME_TAG)) {
			throw new JsonParseException("Expected conversation name element, but does not exist.");
		}
		if (!jsonObject.has(MSGS_TAG)) {
			throw new JsonParseException("Expected messages element, but does not exist.");
		}
		
		String conversationName = jsonObject.get(NAME_TAG).getAsString();	
		JsonArray jsonMessages = jsonObject.get(MSGS_TAG).getAsJsonArray();
		
		Collection<IMessage> messages = new ArrayList<IMessage>();
		for (int i = 0; i < jsonMessages.size(); i++) {
			JsonObject jsonMessage = (JsonObject) jsonMessages.get(i);
			Instant timestamp = context.deserialize(
					jsonMessage.get(TIMESTAMP_TAG), 
					Instant.class);
			String senderId = jsonMessage.get(SENDER_TAG).getAsString();
			String content = jsonMessage.get(CONTENT_TAG).getAsString();
			messages.add(new Message(timestamp, senderId, content));
		}
		
		return new Conversation(conversationName, messages);
	}

}
