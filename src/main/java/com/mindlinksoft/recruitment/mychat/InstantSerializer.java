package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

/**
 * instantSerialiser class
 */
public class InstantSerializer implements JsonSerializer<Instant> {
    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(instant.getEpochSecond());
    }

//   public String createJsonSerialized(Conversation conversation) {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
//
//        Gson g = gsonBuilder.create();
//
//        return g.toJson(conversation);
//    }
}

