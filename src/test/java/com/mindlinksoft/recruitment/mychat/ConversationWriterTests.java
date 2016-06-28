package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.Instant;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConversationWriterTests {
	
	final String INPUT1 = "chat.txt";
	final String INPUT2 = "newchat.txt";
	final String OUTPUT = "chatWriterTests.json";
	
	final String RESTRICTED = "adminFile";
	final String INVALID = "noFileHere";

	@Test
	public void testWriteConversation() throws IOException {
		ConversationWriter conversationWriter = new ConversationWriter(new FileWriter(OUTPUT));
		ConversationReader conversationReader = new ConversationReader(new FileReader(INPUT1));
		
		conversationWriter.writeConversation(conversationReader.readConversation());
		conversationReader.close();
		conversationWriter.close();
		
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		//read content written just now:
		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(OUTPUT)), Conversation.class);
		Files.delete(FileSystems.getDefault().getPath(OUTPUT));

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
	
	@Test
	/**Test object returned by reading file procedure*/
	public void testWriteConversationNewConversation() throws IOException {
		ConversationWriter conversationWriter = new ConversationWriter(new FileWriter(OUTPUT));
		ConversationReader conversationReader = new ConversationReader(new FileReader(INPUT2));
		
		conversationWriter.writeConversation(conversationReader.readConversation());
		conversationReader.close();
		conversationWriter.close();
		
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

		Gson g = builder.create();

		//read content written just now:
		Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(OUTPUT)), Conversation.class);
		Files.delete(FileSystems.getDefault().getPath(OUTPUT));
		
		assertEquals("Channel chat", c.name);

		assertEquals(3, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1467060479), ms[0].timestamp);
		assertEquals("nic",ms[0].senderId);
		assertEquals("hello everyone", ms[0].content);

		assertEquals(Instant.ofEpochSecond(1467060489), ms[1].timestamp);
		assertEquals("fay", ms[1].senderId);
		assertEquals("hello", ms[1].content);

		assertEquals(Instant.ofEpochSecond(1467060500), ms[2].timestamp);
		assertEquals("name", ms[2].senderId);
		assertEquals("hello hello", ms[2].content);
	}
	
	@Test(expected=IOException.class)
	public void testReadConversationWithoutPermission() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader(RESTRICTED));
		Conversation c = reader.readConversation();
		reader.close();
	}
	
	@Test(expected=IOException.class)
	public void testReadConversationNoSuchFile() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader(INVALID));
		Conversation c = reader.readConversation();
		reader.close();
	}
}
