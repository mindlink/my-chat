package com.mindlinksoft.recruitment.mychat.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

public class GsonConverter {

    public static Gson buildGsonSerializer() {
        return buildGson(new InstantSerializer());
    }

    public static Gson buildGsonDeserializer() {
        return buildGson(new InstantDeserializer());
    }

    /**
     * Builds a Gson and returns
     *
     * @param type of action, Serialize or Deserialize
     * @return a Gson
     */
    private static Gson buildGson(Object type) {
        GsonBuilder g = new GsonBuilder();
        g.registerTypeAdapter(Instant.class, type);
        return g.create();
    }

    static class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }

    static class InstantDeserializer implements JsonDeserializer<Instant> {
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
}
