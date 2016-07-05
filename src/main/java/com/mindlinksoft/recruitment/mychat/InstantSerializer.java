package com.mindlinksoft.recruitment.mychat;

import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Class used to provide custom serialization into JSON for Instant Java class.
 * Responsible for knowing how to serialize the types in a {@link Conversation}
 * or {@link Message}.
 * */
class InstantSerializer implements JsonSerializer<Instant> {
	
	@Override
	public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
		return new JsonPrimitive(instant.getEpochSecond());
	}
}
