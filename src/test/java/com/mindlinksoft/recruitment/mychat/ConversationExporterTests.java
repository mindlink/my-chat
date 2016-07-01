package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import exceptionHandeling.writeExcep;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
	/**
	 * Tests that exporting a conversation will export the conversation correctly.
	 * @throws Exception When something bad happens.
	 * 
	 * 
	 */
	

	@Test
	public void testExportingConversationExportsConversation() throws Exception {
		ConversationExporter exporter = new ConversationExporter();

		//		String[] param = {
		//				"-" + ConvoConfig.INPUT.getValue(), "chat.txt",
		//				"-" + ConvoConfig.OUTPUT.getValue(), "chatTest.json",
		//				"-" + ConvoConfig.USER.getValue(), "angus",
		//				"-" + ConvoConfig.KEYWORD.getValue(), "head",
		//				"-" + ConvoConfig.BLACKLIST.getValue(), "No, YES"
		//		};

		exporter.exportConversation(new String[] { "-" + ConvoConfig.INPUT.getValue(), "chat.txt", "-"
				+ ConvoConfig.OUTPUT.getValue(), "chat.json"});

		// exporter.exportConversation("chat.txt", "chat.json");



		try (InputStreamReader reader = new InputStreamReader(new FileInputStream("chat.json"))){
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

			Gson g = builder.create();
			Conversation c = g.fromJson(reader, Conversation.class);

			compareConvo(c);
			//System.out.println("test");
		}

		

	}

	public static void compareConvo(Conversation c){

		// System.out.println("comparing convo");
		assertEquals("My Conversation", c.name);

		assertEquals(7, c.messages.size());
		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
		assertEquals(ms[0].senderId, "bob");
		assertEquals(ms[0].content, "Hello there!");

		assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
		assertEquals(ms[1].senderId, "mike");
		assertEquals(ms[1].content, "how are you?");

		assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
		assertEquals(ms[2].senderId, "bob");
		assertEquals(ms[2].content, "I'm good thanks, do you like pie?");

		assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
		assertEquals(ms[3].senderId, "mike");
		assertEquals(ms[3].content, "no, let me ask Angus...");

		assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
		assertEquals(ms[4].senderId, "angus");
		assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

		assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
		assertEquals(ms[5].senderId, "bob");
		assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

		assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
		assertEquals(ms[6].senderId, "angus");
		assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
		
		System.out.println("Conversation was exported successfully");
		
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
