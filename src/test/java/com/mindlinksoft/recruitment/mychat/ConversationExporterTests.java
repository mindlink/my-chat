package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
	/**
	 * Tests that exporting a conversation will export the conversation
	 * correctly.
	 * 
	 * @throws Exception
	 *             When something bad happens.
	 */
	@Test
	public void testExportingConversationExportsConversation() throws Exception {


		String searchName = "Angus";
		String searchWord = "pie";
		//Blacklist in a CSV sort of way..
		String blackList = "pie,anybody";
		//Not sure how this was to be injected so i did it this way.. 
		String cardPattern = "((?:(?:\\d{4}[- ]){3}\\d{4}|\\d{16}))(?![\\d])";

		String[] args = new String[] { "chat.txt", "chat.json", searchName, searchWord, blackList, cardPattern };
		ConversationExporter.main(args);

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

		assertEquals("My Conversation", c.name);

		assertEquals(5, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470912));
		assertEquals(ms[0].senderId, "lXGRK");
		assertEquals(ms[0].content, "Hell yes! Are we buying some *REDACTED*?");
		
		assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470915));
		assertEquals(ms[1].senderId, "lXGRK");
		assertEquals(ms[1].content, "YES! I'm the head *REDACTED* eater there...");

		assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470915));
		assertEquals(ms[2].senderId, "lXGRK");
		assertEquals(ms[2].content, "YES! VISA CARD *REDACTED* ...");
		
		assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470915));
		assertEquals(ms[3].senderId, "lXGRK");
		assertEquals(ms[3].content, "YES! Amex CARD *REDACTED* ...");
		
		assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470915));
		assertEquals(ms[4].senderId, "lXGRK");
		assertEquals(ms[4].content, "YES! Master CARD *REDACTED* ...");
	}

	class InstantDeserializer implements JsonDeserializer<Instant> {

		public Instant deserialize(JsonElement jsonElement, Type type,
				JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
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
