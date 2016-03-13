package com.mindlinksoft.recruitment.mychat.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.mindlinksoft.recruitment.mychat.models.Conversation;

/**
 * Helper class to read the test file into a conversation object.
 */
public class ReadFileHelper {
	/**
     * Read output JSON File.
	 * @throws FileNotFoundException When it cannot find the test file.
	 * @throws JsonIOException When there is a problem parsing the test file.
	 * @throws JsonSyntaxException When there is a problem with the syntax within the test file.
     */
	public static Conversation readTestFile() throws FileNotFoundException, JsonIOException, JsonSyntaxException {
		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new _InstantDeserializer());
        Gson gson = builder.create();
        
        return gson.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
	}
	
    private static class _InstantDeserializer implements JsonDeserializer<Instant> {
    	
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
