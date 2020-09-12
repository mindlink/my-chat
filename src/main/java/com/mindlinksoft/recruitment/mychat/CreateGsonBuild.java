package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

/**
 * Create the JSON string content for the JSON output file.
 */
public class CreateGsonBuild
{
    /**
     * Convert a {@link Conversation} to JSON string format.
     *
     * @param conversation The {@link Conversation} to be transformed into a JSON string.
     * @return The JSON string.
     */
    public String convert(Conversation conversation)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
        return gsonBuilder.create().toJson(conversation);
    }

    static class InstantSerializer implements JsonSerializer<Instant>
    {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
