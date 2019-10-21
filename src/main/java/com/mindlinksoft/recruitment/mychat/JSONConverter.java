package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

/**
 * Used to convert objects (specifically Conversation objects containing lengthy timestamps) to JSON.
 */
public class JSONConverter  {
    /**
     * Converts object to JSON sting
     * @param inputData Input object that is to be converted to JSON.
     */
    public static String convertToJSON(Object inputData) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
        Gson g = gsonBuilder.create();
        return g.toJson(inputData);
    }

    static class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
