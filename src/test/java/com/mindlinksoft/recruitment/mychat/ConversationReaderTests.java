package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;

import org.junit.Test;

public class ConversationReaderTests {

	@Test
	public void testReadConversation() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader("chat.txt"));
		Conversation c = reader.readConversation();
		reader.close();
		
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
	public void testReadConversationNewConversation() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader("newchat.txt"));
		Conversation c = reader.readConversation();
		reader.close();
		
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
		ConversationReader reader = new ConversationReader(new FileReader("adminFile"));
		Conversation c = reader.readConversation();
		reader.close();
	}
	
	@Test(expected=IOException.class)
	public void testReadConversationNoSuchFile() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader("noFileHere"));
		Conversation c = reader.readConversation();
		reader.close();
	}

}
