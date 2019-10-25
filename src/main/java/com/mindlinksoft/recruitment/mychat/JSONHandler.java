package com.mindlinksoft.recruitment.mychat;
import com.mindlinksoft.recruitment.mychat.Conversation;
import com.google.gson.*;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

class JSONHandler {
    /**
     * Converts object to JSON
     * @param input Input object that is to be converted to JSON.
     */
    public static String toJSON(Object input) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
        Gson gson = gsonBuilder.create();
        return gson.toJson(input);
    }

    public static Conversation fromJSON(FileInputStream input){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson gson = gsonBuilder.create();

        return gson.fromJson(new InputStreamReader(input), Conversation.class);
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
