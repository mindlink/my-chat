package com.mindlinksoft.recruitment.mychat.Utilities;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

class BuildCreateGson {
    Gson g;

    BuildCreateGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new BuildCreateGson.InstantSerializer());
        g = gsonBuilder.create();
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
