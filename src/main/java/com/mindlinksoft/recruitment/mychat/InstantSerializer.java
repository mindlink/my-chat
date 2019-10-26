package com.mindlinksoft.recruitment.mychat;

import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Converts a {@link Conversation} object to a Json object. Implements
 * {@link JsonSerializer}.
 */
public class InstantSerializer implements JsonSerializer<Instant> {

	@Override
	public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
		return new JsonPrimitive(instant.getEpochSecond());
	}

	/**
	 * Creates Json file from the input {@code conversation}.
	 * 
	 * @param conversation The {@link Conversation} object to convert.
	 * @return Json file that has been converted.
	 */
	public static String createJsonSerialized(Conversation conversation) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

		Gson g = gsonBuilder.create();

		return g.toJson(conversation);
	}
}