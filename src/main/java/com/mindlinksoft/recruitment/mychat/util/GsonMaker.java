package com.mindlinksoft.recruitment.mychat.util;

import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonMaker {

	/*
	 * The instance returned can be used to convert objects to JSON.
	 * 
	 * @return gsonBuilder.create() - An instance of Gson.
	 */
	public Gson createGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
		gsonBuilder.registerTypeAdapter(Instant.class, new InstantDeserializer());
		return gsonBuilder.create();
	}

	class InstantSerializer implements JsonSerializer<Instant> {
		@Override
		public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(instant.getEpochSecond());
		}
	}

	class InstantDeserializer implements JsonDeserializer<Instant> {

		@Override
		public Instant deserialize(JsonElement jsonElement, Type type,
				JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			if (!jsonElement.isJsonPrimitive()) {
				throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
			}

			JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

			if (!jsonPrimitive.isNumber()) {
				throw new JsonParseException(
						"Expected instant represented as JSON number, but different primitive found.");
			}

			return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
		}
	}

}