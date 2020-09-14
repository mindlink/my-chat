package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
	/**
	 * Tests that exporting a conversation will export the conversation correctly.
	 * @throws Exception When something bad happens.
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
		assertEquals(c.activity, null);

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
	 * In all cases, when testing, the main function is called and the command line arguments are passed as a string array.
	 * Continuing to assert that the conversation names and number of messages are as expected, as in the provided test function,
	 * as a debugging tool to ensure I'm testing the files that I expect to be testing. 
	 * This is to prevent me from changing an otherwise correct function returning an unexpected value because the input was not as I thought it was.
	 * */
	
	
	@Test
	public void testUsername() throws Exception
	{
        String testNameExists = "bob";

		String[] args = {
				"chat.txt",
				"chat.json",
				"User: "+testNameExists
		};
		ConversationExporter.main(args);
		
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals(3, c.messages.size());
		assertEquals(c.activity, null);
		assertEquals("My Conversation, filtered by name: " + testNameExists, c.name);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
        
        // iterate over every message to ensure the username filter worked - no usernames outside of "bob" should appear
        for (int i = 0; i < ms.length; i++) {
        	assertEquals(ms[i].senderId, testNameExists);
        }
	} 
	
	@Test
	public void testKeywordFilter() throws Exception
	{
		String testKeywordExists = "Pie";
		String[] args = {
				"chat.txt",
				"chat.json",
				"Keyword: "+testKeywordExists
		};
		ConversationExporter.main(args);

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

		assertEquals(4, c.messages.size());
		assertEquals(c.activity, null);
		assertEquals("My Conversation, filtered by keyword: " + testKeywordExists, c.name);

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

        for (int i = 0; i < ms.length; i++) {
        	// case-agnostic test for a case-agnostic filter
        	assertEquals(ms[i].content.toUpperCase().contains(testKeywordExists.toUpperCase()), true);
        }
	}

	@Test
	public void testBlacklist() throws Exception
	{
        String redactWord = "pie";

		String[] args = {
				"chat.txt",
				"chat.json",
				"Blacklist: " + redactWord
		};
		
		ConversationExporter.main(args);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
		assertEquals(7, c.messages.size());
		assertEquals(c.activity, null);
		assertEquals("My Conversation, with redaction.", c.name);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
        
        for (int i = 0; i < ms.length; i++) {
    		// the redacted word should not be present anywhere         	
        	assertNotEquals(ms[i].content.contains(redactWord), true);
        	
        	// messages at these indices should contain "*redacted*", as they previously contained "pie"
        	if (i == 2 || i == 4 || i == 5 || i == 6) {
                assertEquals(ms[i].content.contains("*redacted*"), true);
        	}

        	// messages at the other indices should not contain "*redacted*" 
        	else {
                assertNotEquals(ms[i].content.contains("*redacted*"), true);
        	}
        }
       
        
        /**
         * Test that redacting with a keyword that does NOT exist in the chat.txt file has no effect.
         */
        String testKeywordDoesNotExist = "chicken";
		String[] nonexistentArgs = {
				"chat.txt",
				"chat.json",
				"Blacklist: " + testKeywordDoesNotExist
		};
		ConversationExporter.main(nonexistentArgs);

        Conversation unredactedConversation = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
		assertEquals(7, unredactedConversation.messages.size());
		assertEquals(unredactedConversation.activity, null);
		assertEquals("My Conversation, with redaction.", unredactedConversation.name);

        Message[] unredactedMs = new Message[unredactedConversation.messages.size()];
        unredactedConversation.messages.toArray(unredactedMs);
        
        // no message should contain "redacted" as "chicken" doesn't appear
        for (int i = 0; i < unredactedMs.length; i++) {
            assertNotEquals(unredactedMs[i].content.contains("*redacted*"), true);
        }
    }

	@Test
	public void testNumberRedactFilter() throws Exception
	{
		String[] args = {
				"cardchat.txt",
				"cardchat.json",
				"f100"
		};
		ConversationExporter.main(args);
		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("cardchat.json")), Conversation.class);
		assertEquals(5, c.messages.size());
		assertEquals(c.activity, null);
		assertEquals("Cards and Phones, with redacted card numbers.", c.name);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
        
		// every line from 0 to 3 has a phone number or card number to redact, so every line in the output should contain "*redacted*"
        for (int i = 0; i < ms.length - 1; i++) {
    		assertEquals(ms[i].content.contains("*redacted*"), true);
        }
        
        // last line has no number, should not contain redacted
		assertEquals(ms[4].content.contains("*redacted*"), false);

	}

	@Test
	public void testObfuscate() throws Exception
	{
		String[] args = {
				"chat.txt",
				"chat.json",
				"f010"
		};
		
		String[] users = {
				"bob",
				"mike",
				"angus"
		};
		
		ConversationExporter.main(args);
		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
		assertEquals(7, c.messages.size());
		assertEquals(c.activity, null);
		assertEquals("My Conversation, with obfuscated userID.", c.name);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
        
        for (int i = 0; i < ms.length; i++) {
        	for (int j = 0; j < users.length; j++) {
        		// no message should have any of the users in its senderId field
        		assertNotEquals(ms[i].senderId.toUpperCase(), users[j].toUpperCase());
        	}
        }
	}

	@Test
	public void testPopularity() throws Exception
	{
		String[] args = {
				"chat.txt",
				"chat.json",
				"f001"
		};
		ConversationExporter.main(args);

		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
		assertEquals(7, c.messages.size());
		assertEquals(3, c.activity.size());
		assertEquals("My Conversation", c.name);
		
		UserNode[] pop = new UserNode[c.activity.size()];
		c.activity.toArray(pop);
		
		for (int i = 0; i < pop.length - 1; i++) {
			// assert descending order - most popular at the front
			assertEquals(pop[i].count >= pop[i+1].count, true);
		}

		assertEquals(pop[0].userId, "bob");
		assertEquals(pop[1].userId, "mike");
		assertEquals(pop[2].userId, "angus");
		
		assertEquals(pop[0].count, 3);
		assertEquals(pop[1].count, 2);
		assertEquals(pop[2].count, 2);       
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
