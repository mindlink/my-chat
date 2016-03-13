package com.mindlinksoft.recruitment.mychat.json;

import com.google.gson.GsonBuilder;
import java.io.Reader;
import java.time.Instant;

/**
 * It uses a singleton model to make it reusable.
 *
 * @author Gabor
 */
public class GsonWrapper {

    private static GsonWrapper instance = null;
    final private GsonBuilder gsonBuilder;

    public static GsonWrapper getInstance() {
        if (instance == null) {
            instance = new GsonWrapper();
        }
        return instance;
    }

    private GsonWrapper() {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantDeserializer());
    }

    /**
     * Wrapper for converting object to json.
     *
     * @param o
     * @return
     */
    public String toJson(Object o) {
        return gsonBuilder.create().toJson(o);
    }

    /**
     * Wrapper for converting json to object.
     *
     * @param json
     * @param type
     * @return
     */
    public Object fromJson(Reader json, final Class<?> type) {
        return gsonBuilder.create().fromJson(json, type);
    }
}
