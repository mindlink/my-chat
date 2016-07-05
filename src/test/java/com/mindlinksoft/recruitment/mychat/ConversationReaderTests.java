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
		
		assertEquals("My Conversation", c.getName());

		assertEquals(7, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
		assertEquals("bob",ms[0].getSenderId());
		assertEquals("Hello there!", ms[0].getContent());

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
		assertEquals("mike", ms[1].getSenderId());
		assertEquals("how are you?", ms[1].getContent());

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
		assertEquals("bob", ms[2].getSenderId());
		assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

		assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
		assertEquals("mike", ms[3].getSenderId());
		assertEquals("no, let me ask Angus...", ms[3].getContent());

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
		assertEquals("angus", ms[4].getSenderId());
		assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

		assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
		assertEquals("bob", ms[5].getSenderId());
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
		assertEquals("angus", ms[6].getSenderId());
		assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());
	}
	
	@Test
	/**Test object returned by reading file procedure*/
	public void testReadConversationNewConversation() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader("newchat.txt"));
		Conversation c = reader.readConversation();
		reader.close();
		
		assertEquals("Channel chat", c.getName());

		assertEquals(3, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1467060479), ms[0].getTimestamp());
		assertEquals("nic",ms[0].getSenderId());
		assertEquals("hello everyone", ms[0].getContent());

		assertEquals(Instant.ofEpochSecond(1467060489), ms[1].getTimestamp());
		assertEquals("fay", ms[1].getSenderId());
		assertEquals("hello", ms[1].getContent());

		assertEquals(Instant.ofEpochSecond(1467060500), ms[2].getTimestamp());
		assertEquals("name", ms[2].getSenderId());
		assertEquals("hello hello", ms[2].getContent());
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
