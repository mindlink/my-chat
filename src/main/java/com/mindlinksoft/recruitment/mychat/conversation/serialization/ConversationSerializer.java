package com.mindlinksoft.recruitment.mychat.conversation.serialization;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mindlinksoft.recruitment.mychat.conversation.IConversation;

/**
 * JSON serializer for {@link IConversation} objects. 
 *
 */
public class ConversationSerializer implements JsonSerializer<IConversation>{

	private static final String NAME_TAG = "name";
	private static final String MSGS_TAG = "messages";
	private static final String ACTIVITY_TAG = "user-activity";
	
	@Override
	public JsonElement serialize(IConversation conversation, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(NAME_TAG, conversation.getName());
		jsonObject.add(MSGS_TAG, context.serialize(conversation.getMessages()));
		jsonObject.add(ACTIVITY_TAG, context.serialize(conversation.getUserActivity()));
		
		return jsonObject;
	}

}
