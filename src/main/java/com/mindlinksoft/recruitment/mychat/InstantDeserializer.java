package com.mindlinksoft.recruitment.mychat;
import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.*;

/**
 * Class that implements {@link JsonDeserializer}. Allows for a Deserialized
 * {@link Conversation} to be created from an input Json file.
 */
public class InstantDeserializer implements JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonElement jsonElement, Type type,
                               JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
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
