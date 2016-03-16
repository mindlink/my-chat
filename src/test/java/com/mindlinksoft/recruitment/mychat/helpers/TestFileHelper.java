package com.mindlinksoft.recruitment.mychat.helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import com.mindlinksoft.recruitment.mychat.exceptions.ReadConversationException;
import com.mindlinksoft.recruitment.mychat.exceptions.WriteConversationException;
import com.mindlinksoft.recruitment.mychat.models.Conversation;

/**
 * Static helper class to help with file operations on the test file.
 */
public final class TestFileHelper {
	
    /**
     * Clear the JSON output test file.
     * 
	 * @throws WriteConversationException When it cannot write to the output test file.
     */
	public static void clearOutput() throws WriteConversationException {
		try (OutputStream outputStream = new FileOutputStream("chat.json", false)) {
			
			outputStream.write(0);
			
		} catch (IOException e) {
			throw new WriteConversationException("Could not write to the output test file", e);			
		}
	}
	
	/**
     * Read the JSON output test file.
     * 
	 * @throws JsonIOException When there is a problem parsing the test file.
	 * @throws JsonSyntaxException When there is a problem with the syntax within the test file.
	 * @throws ReadConversationException When there is a problem reading from the test file.
     */
	public static Conversation readOutput() throws JsonIOException, JsonSyntaxException, ReadConversationException {
		
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream("chat.json"))) {
        	
        	GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Instant.class, new _InstantDeserializer());
            Gson gson = builder.create();
            
        	return gson.fromJson(reader, Conversation.class);
        	
        } catch (IOException e) {
			throw new ReadConversationException("Could not read from the output test file", e); 	
        } 	
	}
	
	/**
     * Override deserialize for reading the JSON
     */
    private static class _InstantDeserializer implements JsonDeserializer<Instant> {
    	
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
