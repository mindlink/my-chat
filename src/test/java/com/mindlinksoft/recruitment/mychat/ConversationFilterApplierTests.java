package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;

import org.junit.Test;

public class ConversationFilterApplierTests {
	

	@Test
	public void testApplyFilters() throws FileNotFoundException, IOException {
		CLIConfiguration config = new CLIConfiguration("chat.txt", "chat.txt");
		config.addFilter(new FilterKeyword("pie"));
		config.addFilter(new FilterUsername("bob"));
		
		ConversationReader r = new ConversationReader(new FileReader("chat.txt"));
		Conversation c = r.readConversation();
		r.close();
		
		ConversationFilterApplier.applyFilters(config.getFilters(), c);
		
		assertEquals(2, c.messages.size());

		Message ms [] = new Message[c.messages.size()];
		c.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470906), ms[0].timestamp);
		assertEquals("bob", ms[0].senderId);
		assertEquals("I'm good thanks, do you like pie?", ms[0].content);
		
		assertEquals(Instant.ofEpochSecond(1448470914), ms[1].timestamp);
		assertEquals("bob", ms[1].senderId);
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[1].content);
		
	}

}
