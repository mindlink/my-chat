package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class ConversationTests {
	
	Conversation c;
	Message[] ms;

	
	private void resetConversation() throws IOException {
		c = new ConversationExporter().readConversation("chat.txt");
		
	}
	
			
	@Test
	public void testFilterByUserId() throws IOException {
		//ask for "bob"
		resetConversation();
		c.filterByUserId("bob");
		
		assertEquals(3, c.messages.size());
		ms = new Message[c.messages.size()];
		c.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
		assertEquals("bob",ms[0].senderId);
		assertEquals("Hello there!", ms[0].content);

		assertEquals(Instant.ofEpochSecond(1448470906), ms[1].timestamp);
		assertEquals("bob", ms[1].senderId);
		assertEquals("I'm good thanks, do you like pie?", ms[1].content);

		assertEquals(Instant.ofEpochSecond(1448470914), ms[2].timestamp);
		assertEquals("bob", ms[2].senderId);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);

		//ask for "angus"
		resetConversation();
		c.filterByUserId("angus");
		
		assertEquals(2, c.messages.size());
		ms = new Message[c.messages.size()];
		c.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470912), ms[0].timestamp);
		assertEquals("angus", ms[0].senderId);
		assertEquals("Hell yes! Are we buying some pie?", ms[0].content);
		
		assertEquals(Instant.ofEpochSecond(1448470915), ms[1].timestamp);
		assertEquals("angus", ms[1].senderId);
		assertEquals("YES! I'm the head pie eater there...", ms[1].content);
		
		//ask for "nobody"
		resetConversation();
		c.filterByUserId("nobody");

		assertEquals(0, c.messages.size());
		
		//ask for both "bob" and "angus" (not required by specification)
//		resetConversation();
//		c.filterByUserId("bob", "angus");
//		
//		assertEquals(5, c.messages.size());
//		ms = new Message[c.messages.size()];
//		c.messages.toArray(ms);
//		
//		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
//		assertEquals("bob",ms[0].senderId);
//		assertEquals("Hello there!", ms[0].content);
//
//		assertEquals(Instant.ofEpochSecond(1448470906), ms[1].timestamp);
//		assertEquals("bob", ms[1].senderId);
//		assertEquals("I'm good thanks, do you like pie?", ms[1].content);
//		
//		assertEquals(Instant.ofEpochSecond(1448470912), ms[2].timestamp);
//		assertEquals("angus", ms[2].senderId);
//		assertEquals("Hell yes! Are we buying some pie?", ms[2].content);
//
//		assertEquals(Instant.ofEpochSecond(1448470914), ms[3].timestamp);
//		assertEquals("bob", ms[3].senderId);
//		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[3].content);
//
//		assertEquals(Instant.ofEpochSecond(1448470915), ms[4].timestamp);
//		assertEquals("angus", ms[4].senderId);
//		assertEquals("YES! I'm the head pie eater there...", ms[4].content);
	}

}
