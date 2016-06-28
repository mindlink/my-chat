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

	/**
	 * Tests that conversations read have correctly modified themselves based
	 * on exporter config parameters. Makes assumptions about conversation 
	 * config field in exporter: {@link ConversationExporterConfiguration}
	 * @throws IOException 
	 * */
	@Test
	public void testApplyFilters() throws IOException {
		//Set up:
		ConversationExporterConfiguration config = 
				new ConversationExporterConfiguration("chat.txt", "chat.json");
		
		//all essential filters:
		config.put('u', "bob");
		config.put('k', "pie");
		config.put('b', "you");
		
		exporter = new ConversationExporter(config);
		Conversation c = exporter.readConversation("chat.txt");
		
		//Action:
		exporter.applyFilters(c);
		
		//Verify:
		assertEquals(2, c.messages.size());

		Message ms [] = new Message[c.messages.size()];
		c.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470906), ms[0].timestamp);
		assertEquals("bob", ms[0].senderId);
		assertEquals("I'm good thanks, do *redacted* like pie?", ms[0].content);
		
		assertEquals(Instant.ofEpochSecond(1448470914), ms[1].timestamp);
		assertEquals("bob", ms[1].senderId);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[1].content);
	}
	
	/**
	 * Tests that exporting a conversation will export the conversation correctly.
	 */
	@Test
	public void testExportConversation() throws IOException {
		//Set up:
		exporter = new ConversationExporter();
		//write to file:
		exporter.exportConversation("chat.txt", "chat.json");

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		//read content written just now:
		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
		Files.delete(FileSystems.getDefault().getPath("chat.json"));

		//Verify:
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
	
	/**
	 * Tests that exporting a conversation will export the conversation correctly
	 * when filters are applied.
	 */
	@Test
	public void testExportConversationFiltered() throws IOException {
		//Set up:
		ConversationExporterConfiguration config = 
				new ConversationExporterConfiguration("chat.txt", "chat.json");

		//all essential filters:
		config.put('u', "bob");
		config.put('k', "pie");
		config.put('b', "you");

		exporter = new ConversationExporter(config);
		//write to file:
		exporter.exportConversation("chat.txt", "chat.json");

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
		Files.delete(FileSystems.getDefault().getPath("chat.json"));


		//Verify:
		assertNotNull(c);
		assertEquals("My Conversation", c.name);

		assertEquals(2, c.messages.size());

		Message ms [] = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470906), ms[0].timestamp);
		assertEquals("bob", ms[0].senderId);
		assertEquals("I'm good thanks, do *redacted* like pie?", ms[0].content);

		assertEquals(Instant.ofEpochSecond(1448470914), ms[1].timestamp);
		assertEquals("bob", ms[1].senderId);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[1].content);
	}

}
