package com.mindlinksoft.recruitment.mychat;

import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Static inner class used to provide custom serialization into JSON for 
 * Instant Java class
 * */
class InstantSerializer implements JsonSerializer<Instant> {
	
	@Override
	public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
		return new JsonPrimitive(instant.getEpochSecond());
	}
}
