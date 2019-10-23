package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class ConversationExporterTests {
	
	public Gson g = buildGson();
	
	/**
	 * Create an output directory for test jsons
	 * @throws IOException
	 */
	@Before
	public void setUp() throws IOException {
		Path dir = Paths.get("output");
		Files.createDirectory(dir);
	}
	
	/**
	 * Delete files in output folder and the folder itself
	 * @throws IOException
	 */
	@After
	public void tearDown() throws IOException {
		Files.walk(Paths.get("output"))
	        .filter(Files::isRegularFile)
	        .map(Path::toFile)
	        .forEach(File::delete);
		Files.delete(Paths.get("output"));
	}
	
	/**
	 * Test that a FileNotFoundException is thrown when the input file does not exist
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void notFoundExceptionWhenFileNotExists() throws FileNotFoundException, IOException {
		 ConversationExporter exporter = new ConversationExporter();

	      assertThrows(FileNotFoundException.class, () -> {
		      exporter.exportConversation("non-exist-chat.txt", "output/chat.json", null);
	      });
	}

	/**
	 * Test to see if a conversation exports as expected
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testExportingConversationExportsConversation() throws FileNotFoundException, IOException {
		
        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation("chat.txt", "output/chat.json", null);

	
	        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("output/chat.json")), Conversation.class);
	
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
	 * Tests to see if a conversation is correctly exported when a user filter is applied
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testExportingConversationExportsConversationWithUser() throws FileNotFoundException, IOException {
		ConversationExporter e = new ConversationExporter();
		ConversationFilter filter = new ConversationFilter("bob", null, null);
		e.exportConversation("chat.txt", "output/chat.json", filter);
	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("output/chat.json")), Conversation.class);
		
		assertEquals("My Conversation", c.name);

        assertEquals(3, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");		
	}
	
	/**
	 * Tests to see if a conversation is correctly exported when a keyword filter is applied
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testExportingConversationExportsConversationWithKeyword() throws FileNotFoundException, IOException {
		ConversationExporter e = new ConversationExporter();
		ConversationFilter filter = new ConversationFilter(null, "pie", null);
		e.exportConversation("chat.txt", "output/chat.json", filter);
	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("output/chat.json")), Conversation.class);
		
        assertEquals("My Conversation", c.name);

        assertEquals(4, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[1].senderId, "angus");
        assertEquals(ms[1].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[3].senderId, "angus");
        assertEquals(ms[3].content, "YES! I'm the head pie eater there...");
	}
	
	/**
	 * Tests to see if a conversation is correctly exported when a single blacklisted word is applied
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testExportingConversationExportsConversationWithSingleBlacklist() throws FileNotFoundException, IOException {
		ConversationExporter e = new ConversationExporter();
		ConversationFilter filter = new ConversationFilter(null, null, new String[] {"pie"});
		e.exportConversation("chat.txt", "output/chat.json", filter);
	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("output/chat.json")), Conversation.class);
		
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
        assertEquals(ms[2].content, "I'm good thanks, do you like *redacted*?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some *redacted*?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the *redacted* society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head *redacted* eater there...");
	}
	
	/**
	 * Tests to see if a conversation is correctly exported when multple blacklisted words are applied
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testExportingConversationExportsConversationWithMultipleBlacklist() throws FileNotFoundException, IOException {
		ConversationExporter e = new ConversationExporter();
		ConversationFilter filter = new ConversationFilter(null, null, new String[] {"pie", "Hell"});
		e.exportConversation("chat.txt", "output/chat.json", filter);
	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("output/chat.json")), Conversation.class);
		
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
        assertEquals(ms[2].content, "I'm good thanks, do you like *redacted*?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "*redacted* yes! Are we buying some *redacted*?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the *redacted* society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head *redacted* eater there...");
	}
	
	/**
	 * Tests to see if a conversation is correctly exported when multiple filters and a blacklist is applied
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testExportingConversationExportsConversationWithMultipleFiltersAndBlacklist() throws FileNotFoundException, IOException {
		ConversationExporter e = new ConversationExporter();
		ConversationFilter filter = new ConversationFilter("bob", "pie", new String[] {"pie"});
		e.exportConversation("chat.txt", "output/chat.json", filter);
	
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("output/chat.json")), Conversation.class);
		
        assertEquals("My Conversation", c.name);

        assertEquals(2, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "I'm good thanks, do you like *redacted*?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "No, just want to know if there's anybody else in the *redacted* society...");
	}
	
	public Gson buildGson() {
		 GsonBuilder builder = new GsonBuilder();
		 builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
	     return builder.create();
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