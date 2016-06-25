package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {

	/**Class field*/
	ConversationExporter exporter;

	@Before
	/**Perfom setup before running these class tests*/
	public void setUp() {
		exporter = new ConversationExporter();
	}

	@Test
	/**Test object returned by reading file procedure*/
	public void testReadConversation() throws IOException {
		Conversation c = exporter.readConversation("chat.txt");

		assertEquals("My Conversation", c.name);

		assertEquals(7, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
		assertEquals("bob",ms[0].senderId);
		assertEquals("Hello there!", ms[0].content);

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
		assertEquals("mike", ms[1].senderId);
		assertEquals("how are you?", ms[1].content);

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
		assertEquals("bob", ms[2].senderId);
		assertEquals("I'm good thanks, do you like pie?", ms[2].content);

		assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
		assertEquals("mike", ms[3].senderId);
		assertEquals("no, let me ask Angus...", ms[3].content);

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
		assertEquals("angus", ms[4].senderId);
		assertEquals("Hell yes! Are we buying some pie?", ms[4].content);

		assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
		assertEquals("bob", ms[5].senderId);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
		assertEquals("angus", ms[6].senderId);
		assertEquals("YES! I'm the head pie eater there...", ms[6].content);
	}

	/**
	 * @throws IOException */
	//	@Test
	//	public void testWriteConversation() throws IOException {
	//		Conversation c = exporter.readConversation("chat.txt");
	//		exporter.writeConversation(c, "chat.json");
	//				
	//	}

	/**
	 * Tests that exporting a conversation will export the conversation correctly.
	 */
	@Test
	public void testExportConversation() throws IOException {
		exporter = new ConversationExporter();

		exporter.exportConversation("chat.txt", "chat.json");

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
		Files.delete(FileSystems.getDefault().getPath("chat.json"));

		assertNotNull(c);
		assertEquals("My Conversation", c.name);

		assertEquals(7, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
		assertEquals("bob",ms[0].senderId);
		assertEquals("Hello there!", ms[0].content);

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
		assertEquals("mike", ms[1].senderId);
		assertEquals("how are you?", ms[1].content);

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
		assertEquals("bob", ms[2].senderId);
		assertEquals("I'm good thanks, do you like pie?", ms[2].content);

		assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
		assertEquals("mike", ms[3].senderId);
		assertEquals("no, let me ask Angus...", ms[3].content);

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
		assertEquals("angus", ms[4].senderId);
		assertEquals("Hell yes! Are we buying some pie?", ms[4].content);

		assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
		assertEquals("bob", ms[5].senderId);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
		assertEquals("angus", ms[6].senderId);
		assertEquals("YES! I'm the head pie eater there...", ms[6].content);
	}

	/**command line argument test*/
	@Test
	public void testCLI() throws IOException {
		MainCLI.main(new String[] { "chat.txt", "chat.json" });

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
		Files.delete(FileSystems.getDefault().getPath("chat.json"));
		assertNotNull(c);
		assertEquals("My Conversation", c.name);

		assertEquals(7, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
		assertEquals("bob",ms[0].senderId);
		assertEquals("Hello there!", ms[0].content);

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
		assertEquals("mike", ms[1].senderId);
		assertEquals("how are you?", ms[1].content);

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
		assertEquals("bob", ms[2].senderId);
		assertEquals("I'm good thanks, do you like pie?", ms[2].content);

		assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
		assertEquals("mike", ms[3].senderId);
		assertEquals("no, let me ask Angus...", ms[3].content);

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
		assertEquals("angus", ms[4].senderId);
		assertEquals("Hell yes! Are we buying some pie?", ms[4].content);

		assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
		assertEquals("bob", ms[5].senderId);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
		assertEquals("angus", ms[6].senderId);
		assertEquals("YES! I'm the head pie eater there...", ms[6].content);
	}

	private void verifyPieConversation(Conversation c) {

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
