package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class FiltererTest {
	
	Filterer filterer;
	Conversation conversation;
	Message[] ms;

	
	private void resetConversation() throws IOException {
		conversation = new ConversationExporter().readConversation("chat.txt");
		filterer = new Filterer(conversation);
		
	}
	
			
	@Test
	public void testFilterByUserId() throws IOException {
		//ask for "bob"
		resetConversation();
		filterer.filterByUserId("bob");
		
		assertEquals(3, conversation.messages.size());
		ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
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
		filterer.filterByUserId("angus");
		
		assertEquals(2, conversation.messages.size());
		ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470912), ms[0].timestamp);
		assertEquals("angus", ms[0].senderId);
		assertEquals("Hell yes! Are we buying some pie?", ms[0].content);
		
		assertEquals(Instant.ofEpochSecond(1448470915), ms[1].timestamp);
		assertEquals("angus", ms[1].senderId);
		assertEquals("YES! I'm the head pie eater there...", ms[1].content);
		
		//ask for "nobody"
		resetConversation();
		filterer.filterByUserId("nobody");

		assertEquals(0, conversation.messages.size());
		
		//ask for both "bob" and "angus" (not required by specification)
//		resetConversation();
//		filterer.filterByUserId("bob", "angus");
//		
//		assertEquals(5, conversation.messages.size());
//		ms = new Message[conversation.messages.size()];
//		conversation.messages.toArray(ms);
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
	
	@Test
	public void testFilterBySubstring() throws IOException {
		//ask for "pie"
		resetConversation();
		filterer.filterBySubstring("pie");
		
		assertEquals(4, conversation.messages.size());
		ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470906), ms[0].timestamp);
		assertEquals("bob", ms[0].senderId);
		assertEquals("I'm good thanks, do you like pie?", ms[0].content);
		
		assertEquals(Instant.ofEpochSecond(1448470912), ms[1].timestamp);
		assertEquals("angus", ms[1].senderId);
		assertEquals("Hell yes! Are we buying some pie?", ms[1].content);

		assertEquals(Instant.ofEpochSecond(1448470914), ms[2].timestamp);
		assertEquals("bob", ms[2].senderId);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);

		assertEquals(Instant.ofEpochSecond(1448470915), ms[3].timestamp);
		assertEquals("angus", ms[3].senderId);
		assertEquals("YES! I'm the head pie eater there...", ms[3].content);
		
		//ask for "Angus"
		resetConversation();
		filterer.filterBySubstring("Angus");
		
		assertEquals(1, conversation.messages.size());
		ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470910), ms[0].timestamp);
		assertEquals("mike", ms[0].senderId);
		assertEquals("no, let me ask Angus...", ms[0].content);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testBlacklistException() throws IOException, 
													IllegalArgumentException {
		resetConversation();
		
		filterer.blacklist("not alphanumeric!");
	}
	
	@Test
	public void testBlacklist() throws Exception {
		//ask for "yes"
		resetConversation();
		filterer.blacklist("yes");
		
		assertEquals(7, conversation.messages.size());

		ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
		assertEquals("angus", ms[4].senderId);
		assertEquals("Hell *redacted*! Are we buying some pie?", ms[4].content);

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
		assertEquals("angus", ms[6].senderId);
		assertEquals("*redacted*! I'm the head pie eater there...", ms[6].content);
		
		//ask for "\t YOU  \n"
		resetConversation();
		filterer.blacklist("\t YOU  \n");

		assertEquals(7, conversation.messages.size());

		ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
		assertEquals("mike", ms[1].senderId);
		assertEquals("how are *redacted*?", ms[1].content);

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
		assertEquals("bob", ms[2].senderId);
		assertEquals("I'm good thanks, do *redacted* like pie?", ms[2].content);
		
	}
}
