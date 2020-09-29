package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;
import com.mindlinksoft.recruitment.mychat.util.GsonMaker;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
	/**
	 * Tests that exporting a conversation will export the conversation correctly.
	 * 
	 * @throws Exception When something bad happens.
	 */
	@Test
	public void testExportingConversationExportsConversation_NoFiltering() throws Exception {
		ConversationExporter exporter = new ConversationExporter();
		ConversationExporterConfiguration conversationExporterConfiguration = new ConversationExporterConfiguration(
				"chat.txt", "chat.json");
		exporter.exportConversation(conversationExporterConfiguration);

		GsonMaker gsonMaker = new GsonMaker();
		Gson g = gsonMaker.createGson();

		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

		assertEquals("My Conversation", c.getName());

		assertEquals(7, c.getMessages().size());

		Message[] ms = new Message[c.getMessages().size()];
		c.getMessages().toArray(ms);

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
	 * Tests that exporting a conversation with all applicable filters, will export
	 * the conversation correctly.
	 */
	@Test
	public void testExportingConversationExportsConversation_AllFiltering() throws Exception {
		ConversationExporter exporter = new ConversationExporter();
		ConversationExporterConfiguration conversationExporterConfiguration = new ConversationExporterConfiguration(
				"chat.txt", "chat.json");
		conversationExporterConfiguration.setUserFilter("bob");
		conversationExporterConfiguration.setKeywordFilter("pie");
		conversationExporterConfiguration.setBlacklist("good");
		conversationExporterConfiguration.setObfuscateUsers(true);
		exporter.exportConversation(conversationExporterConfiguration);

		GsonMaker gsonMaker = new GsonMaker();
		Gson g = gsonMaker.createGson();

		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

		assertEquals("My Conversation", c.getName());

		assertEquals(2, c.getMessages().size());

		Message[] ms = new Message[c.getMessages().size()];
		c.getMessages().toArray(ms);

		assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
		assertEquals(ms[0].senderId, "+ju7IUCTWKsCMJ1QXKYq8g==");
		assertEquals(ms[0].content, "I'm *redacted* thanks, do you like pie?");

		assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470914));
		assertEquals(ms[1].senderId, "+ju7IUCTWKsCMJ1QXKYq8g==");
		assertEquals(ms[1].content, "No, just want to know if there's anybody else in the pie society...");
	}

}
