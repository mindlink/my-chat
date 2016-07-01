package com.mindlinksoft.recruitment.mychat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import exceptionHandeling.readExcep;
import exceptionHandeling.writeExcep;

/**
 * Tests for the {@link FileIO}.
 */

public class FileIOTest {
	/**
	 * tests reading the chat.txt file 
	 * @throws Exception
	 */
	@Test
	public void readTest() throws Exception{
		FileIO io = new FileIO();
		Conversation convo = io.readConversation("chat.txt");

		ConversationExporterTests.compareConvo(convo);

		System.out.println("Successfully read file");
	}
	
	/**
	 * tests writing a chatCopy.json file
	 * @throws Exception
	 */
	
	@Test
	public void writeTest() throws Exception{
		FileIO io = new FileIO();
		Conversation convo = convoCopy.copy();
		io.writeConversation(convo, "chatCopy.json");

		try (InputStreamReader reader = new InputStreamReader(new FileInputStream("chatCopy.json"))) {

			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
			Gson g = builder.create();
			Conversation c = g.fromJson(reader, Conversation.class);

			ConversationExporterTests.compareConvo(c);
			
			System.out.println("Successfuly wrote file");
		}
	}
	
	class InstantDeserializer implements JsonDeserializer<Instant> {

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
