package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FilterObfuscateUsernamesTests {

	@Test
	public void testApply() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader("chat.txt"));
		Conversation c = reader.readConversation();
		reader.close();
		
		assertNotNull(c);
		assertEquals("My Conversation", c.name);

		assertEquals(7, c.messages.size());
		
		new FilterObfuscateUsernames().apply(c);

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
		assertEquals("'user" + "bob".hashCode() + "'",ms[0].senderId);
		assertEquals("Hello there!", ms[0].content);

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
		assertEquals("'user" + "mike".hashCode() + "'", ms[1].senderId);
		assertEquals("how are you?", ms[1].content);

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
		assertEquals("'user" + "bob".hashCode() + "'", ms[2].senderId);
		assertEquals("I'm good thanks, do you like pie?", ms[2].content);

		assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
		assertEquals("'user" + "mike".hashCode() + "'", ms[3].senderId);
		assertEquals("no, let me ask " + "'user" + "angus".hashCode() + "'" + "...", ms[3].content);

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
		assertEquals("'user" + "angus".hashCode() + "'", ms[4].senderId);
		assertEquals("Hell yes! Are we buying some pie?", ms[4].content);

		assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
		assertEquals("'user" + "bob".hashCode() + "'", ms[5].senderId);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
		assertEquals("'user" + "angus".hashCode() + "'", ms[6].senderId);
		assertEquals("YES! I'm the head pie eater there...", ms[6].content);
	}
	
	@Test
	public void testApplyAndWrite() throws IOException {
		//read:
		ConversationReader reader = new ConversationReader(new FileReader("chat.txt"));
		Conversation c = reader.readConversation();
		reader.close();
		
		//filter:
		new FilterObfuscateUsernames().apply(c);
		
		//write:
		ConversationWriter writer = new ConversationWriter(new FileWriter("chatObfuscationTest.json"));
		writer.writeConversation(c);
		writer.close();

		//read again:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
		Gson g = builder.create();
		c = g.fromJson(new InputStreamReader(new FileInputStream("chatObfuscationTest.json")), Conversation.class);

		//verify:
		assertNotNull(c);
		assertEquals("My Conversation", c.name);

		assertEquals(7, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
		assertEquals("'user" + "bob".hashCode() + "'",ms[0].senderId);
		assertEquals("Hello there!", ms[0].content);

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
		assertEquals("'user" + "mike".hashCode() + "'", ms[1].senderId);
		assertEquals("how are you?", ms[1].content);

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
		assertEquals("'user" + "bob".hashCode() + "'", ms[2].senderId);
		assertEquals("I'm good thanks, do you like pie?", ms[2].content);

		assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
		assertEquals("'user" + "mike".hashCode() + "'", ms[3].senderId);
		assertEquals("no, let me ask " + "'user" + "angus".hashCode() + "'" + "...", ms[3].content);

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
		assertEquals("'user" + "angus".hashCode() + "'", ms[4].senderId);
		assertEquals("Hell yes! Are we buying some pie?", ms[4].content);

		assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
		assertEquals("'user" + "bob".hashCode() + "'", ms[5].senderId);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
		assertEquals("'user" + "angus".hashCode() + "'", ms[6].senderId);
		assertEquals("YES! I'm the head pie eater there...", ms[6].content);
	}

}
