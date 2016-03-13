package com.mindlinksoft.recruitment.mychat.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.Instant;

/**
 * InstantSerializer separeted as I don't like inner classes.
 *
 * @author Gabor
 */
public class InstantSerializer implements JsonSerializer<Instant> {

    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(instant.getEpochSecond());
    }
}
