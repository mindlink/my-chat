package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

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
		ConversationExporter exporter = new ConversationExporter();

		exporter.exportConversation("chat.txt", "chat.json");

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

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
	}

	/**
	 * Tests that filters operate correctly
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFiltering() throws Exception {
		ConversationExporter exporter = new ConversationExporter();
		ConversationExporterConfiguration config = new ConversationExporterConfiguration("in", "out");

		List<Message> messages = new ArrayList<Message>();
		messages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
		messages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
		messages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?"));
		messages.add(new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus..."));
		messages.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?"));
		messages.add(new Message(Instant.ofEpochSecond(1448470914), "bob",
				"No, just want to know if there's anybody else in the pie society..."));
		messages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head pie eater there..."));

		Conversation conversation = new Conversation("My conversation", messages);

		config.filterUser = true;
		config.username = "bob";
		Conversation filteredConversation = exporter.filterConversation(conversation, config);
		assertEquals(3, filteredConversation.messages.size());
		for(Message message: filteredConversation.messages) {
			assertEquals("bob", message.senderId);
		}
		
		exporter = new ConversationExporter();
		config = new ConversationExporterConfiguration("in", "out");
		conversation = new Conversation("My conversation", messages);		
		config.filterKeyword = true;
		config.keyword = "pie";
		filteredConversation = exporter.filterConversation(conversation, config);
		assertEquals(4,filteredConversation.messages.size());
		Message[] ms = new Message[filteredConversation.messages.size()];
		filteredConversation.messages.toArray(ms);
		assertEquals("I'm good thanks, do you like pie?", ms[0].content);
		assertEquals("Hell yes! Are we buying some pie?", ms[1].content);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);
		assertEquals("YES! I'm the head pie eater there...", ms[3].content);
		
		exporter = new ConversationExporter();
		config = new ConversationExporterConfiguration("in", "out");
		conversation = new Conversation("My conversation", messages);
		config.filterBlacklist = true;
		config.blacklist = "pie";
		filteredConversation = exporter.filterConversation(conversation, config);
		assertEquals(7, filteredConversation.messages.size());
		ms = new Message[filteredConversation.messages.size()];
		filteredConversation.messages.toArray(ms);
		assertEquals("Hello there!", ms[0].content);
		assertEquals("how are you?", ms[1].content);
		assertEquals("I'm good thanks, do you like \\*redacted\\*?", ms[2].content);
		assertEquals("no, let me ask Angus...", ms[3].content);
		assertEquals("Hell yes! Are we buying some \\*redacted\\*?", ms[4].content);
		assertEquals("No, just want to know if there's anybody else in the \\*redacted\\* society...", ms[5].content);
		assertEquals("YES! I'm the head \\*redacted\\* eater there...", ms[6].content);
		
		exporter = new ConversationExporter();
		config = new ConversationExporterConfiguration("in", "out");		
		config.filterNumbers = true;
		messages = new ArrayList<Message>();
		messages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "My phone number is 01234567891. What's yours?"));
		messages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "Mine is 01234567892."));
		messages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "Also my credit card number is 1111222233334444."));
		conversation = new Conversation("My conversation", messages);
		filteredConversation = exporter.filterConversation(conversation, config);
		ms = new Message[filteredConversation.messages.size()];
		filteredConversation.messages.toArray(ms);
		assertEquals("My phone number is \\*redacted\\*. What's yours?", ms[0].content);
		assertEquals("Mine is \\*redacted\\*.", ms[1].content);
		assertEquals("Also my credit card number is \\*redacted\\*.", ms[2].content);
	}

	class InstantDeserializer implements JsonDeserializer<Instant> {

		@Override
		public Instant deserialize(JsonElement jsonElement, Type type,
				JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			if (!jsonElement.isJsonPrimitive()) {
				throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
			}

			JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

			if (!jsonPrimitive.isNumber()) {
				throw new JsonParseException(
						"Expected instant represented as JSON number, but different primitive found.");
			}

			return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
		}
	}
}
