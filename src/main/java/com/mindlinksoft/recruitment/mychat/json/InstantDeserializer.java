package com.mindlinksoft.recruitment.mychat.json;

import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class InstantDeserializer implements  JsonDeserializer<Instant>{

    @Override
    public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!jsonElement.isJsonPrimitive()) {
            throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
        }

        JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

        if (!jsonPrimitive.isNumber()) {
            throw new JsonParseException("Expected instant represented as JSON number, but different primitive found.");
        }

        return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
    }

}
