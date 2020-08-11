package com.mindlinksoft.recruitment.mychat;
import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.*;


public class GsonInit {
	
	public GsonBuilder gsonBuilder;
	public Gson g;
	
	public GsonInit() {
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
		g = gsonBuilder.create();
	}
	
	class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
