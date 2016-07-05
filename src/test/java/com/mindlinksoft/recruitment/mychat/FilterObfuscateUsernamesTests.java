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
		assertEquals("My Conversation", c.getName());

		assertEquals(7, c.messages.size());
		
		new FilterObfuscateUsernames().apply(c);

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
		assertEquals("'user" + "bob".hashCode() + "'",ms[0].getSenderId());
		assertEquals("Hello there!", ms[0].getContent());

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
		assertEquals("'user" + "mike".hashCode() + "'", ms[1].getSenderId());
		assertEquals("how are you?", ms[1].getContent());

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
		assertEquals("'user" + "bob".hashCode() + "'", ms[2].getSenderId());
		assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

		assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
		assertEquals("'user" + "mike".hashCode() + "'", ms[3].getSenderId());
		assertEquals("no, let me ask " + "'user" + "angus".hashCode() + "'" + "...", ms[3].getContent());

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
		assertEquals("'user" + "angus".hashCode() + "'", ms[4].getSenderId());
		assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

		assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
		assertEquals("'user" + "bob".hashCode() + "'", ms[5].getSenderId());
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
		assertEquals("'user" + "angus".hashCode() + "'", ms[6].getSenderId());
		assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());
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
		assertEquals("My Conversation", c.getName());

		assertEquals(7, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
		assertEquals("'user" + "bob".hashCode() + "'",ms[0].getSenderId());
		assertEquals("Hello there!", ms[0].getContent());

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
		assertEquals("'user" + "mike".hashCode() + "'", ms[1].getSenderId());
		assertEquals("how are you?", ms[1].getContent());

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
		assertEquals("'user" + "bob".hashCode() + "'", ms[2].getSenderId());
		assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

		assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
		assertEquals("'user" + "mike".hashCode() + "'", ms[3].getSenderId());
		assertEquals("no, let me ask " + "'user" + "angus".hashCode() + "'" + "...", ms[3].getContent());

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
		assertEquals("'user" + "angus".hashCode() + "'", ms[4].getSenderId());
		assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

		assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
		assertEquals("'user" + "bob".hashCode() + "'", ms[5].getSenderId());
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
		assertEquals("'user" + "angus".hashCode() + "'", ms[6].getSenderId());
		assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());
	}

}
