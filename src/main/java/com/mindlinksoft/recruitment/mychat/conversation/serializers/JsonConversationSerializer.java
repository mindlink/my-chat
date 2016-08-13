package main.java.com.mindlinksoft.recruitment.mychat.conversation.serializers;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.java.com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import main.java.com.mindlinksoft.recruitment.mychat.exceptions.SerializationException;

public class JsonConversationSerializer implements ConversationSerializer{
	private static final Logger LOGGER = Logger.getLogger(JsonConversationSerializer.class.getName() );

	@Override
	public String serialize(Conversation conversation) throws SerializationException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

        Gson g = gsonBuilder.create();
        String result = g.toJson(conversation);
        
        if (result == null)
        	throw new SerializationException(
        			"Failed to serialize conversation with Gson");
        
        String debugLog = String.format("Serialization to JSON successful. Result: '%s'", result);
        LOGGER.log(Level.FINER, debugLog);
		return result;
	}
	
    private class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
