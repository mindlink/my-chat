package com.mindlinksoft.recruitment.mychat.utils;

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
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mindlinksoft.recruitment.mychat.model.Conversation;

/**
 * Class to convert Conversation to and from JSON strings
 *
 */
public class JSONConverter 
{
	/**
	 * Converts a Conversation into JSON string
	 * @param convo Conversation to be converted
	 * @return JSON String of Conversation
	 */
	public static String convertConversationToJSON(Conversation convo)
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

        Gson g = gsonBuilder.create();
        
        return g.toJson(convo);
	}
	
	/**
	 * Converts JSON input into conversation
	 * @param inputfile The input file path
	 * @return Conversation object of JSON file
	 * @throws JsonSyntaxException Thrown when there is JSON syntax error
	 * @throws JsonIOException Thrown when the file couldn't be read from
	 * @throws FileNotFoundException Thrown when the file can't be found
	 */
	public static Conversation convertJSONToConversation(String inputfile) throws JsonSyntaxException, JsonIOException, FileNotFoundException
	{
		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        return g.fromJson(new InputStreamReader(new FileInputStream(inputfile)), Conversation.class);
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
