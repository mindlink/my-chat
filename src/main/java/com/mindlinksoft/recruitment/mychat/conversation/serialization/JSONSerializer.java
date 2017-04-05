package com.mindlinksoft.recruitment.mychat.conversation.serialization;

import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindlinksoft.recruitment.mychat.conversation.Conversation;

/**
 * Serializes objects to JSON.
 *
 */
public class JSONSerializer implements ISerializer {

	private final Gson gson; 
	
	public JSONSerializer() {
		GsonBuilder builder = new GsonBuilder();
		
		//Register serializers
		builder.registerTypeAdapter(Instant.class, new InstantSerializer());
		builder.registerTypeAdapter(Conversation.class, new ConversationSerializer());
		
		gson = builder.setPrettyPrinting().create();
		
	}
	
	@Override
	public String serialize(Object object) {
		return gson.toJson(object);
	}

}
