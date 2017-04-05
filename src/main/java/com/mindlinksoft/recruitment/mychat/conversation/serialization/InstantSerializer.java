package com.mindlinksoft.recruitment.mychat.conversation.serialization;

import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * JSON serializer for {@link Instant} objects. 
 *
 */
public class InstantSerializer implements JsonSerializer<Instant> {
    
	@Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(instant.getEpochSecond());
    }

}
