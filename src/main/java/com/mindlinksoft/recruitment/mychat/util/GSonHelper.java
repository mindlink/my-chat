package com.mindlinksoft.recruitment.mychat.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Helper class for creating gson builders
 */
public class GSonHelper {

	public GsonBuilder createGsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
		gsonBuilder.setFieldNamingStrategy(new NamingStrategy());
		gsonBuilder.setPrettyPrinting();
		return gsonBuilder;
	}

	class InstantSerializer implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
		@Override
		public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(instant.getEpochSecond());
		}

		@Override
		public Instant deserialize(JsonElement jsonElement, Type type,
				JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			if (!jsonElement.isJsonPrimitive())
				throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");

			JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
			if (!jsonPrimitive.isNumber())
				throw new JsonParseException(
						"Expected instant represented as JSON number, but different primitive found.");

			return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
		}

	}

	class NamingStrategy implements FieldNamingStrategy {
		public String translateName(Field field) {
			String fieldName = FieldNamingPolicy.UPPER_CAMEL_CASE.translateName(field);
			if (fieldName.endsWith("_"))
				fieldName = fieldName.substring(0, fieldName.length() - 1);
			return fieldName;
		}
	}
}
