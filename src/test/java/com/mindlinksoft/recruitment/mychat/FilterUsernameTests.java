package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;

import org.junit.Test;

public class FilterUsernameTests {

	@Test
	public void testConstructor() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		FilterUsername f = new FilterUsername("bob");
		
		Field usernameField = FilterUsername.class.getDeclaredField("username");
		usernameField.setAccessible(true);
		String usernameValue = (String) usernameField.get(f);
		
		assertTrue(usernameValue.compareTo("bob") == 0);
		assertFalse(usernameValue.compareTo("nonsense") == 0);
	}
	
	@Test
	public void testApply() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader("chat.txt"));
		Conversation conversation = reader.readConversation();
		reader.close();
		
		new FilterUsername("bob").apply(conversation);
		
		assertEquals(3, conversation.messages.size());
		Message[] ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
		assertEquals("bob",ms[0].getSenderId());
		assertEquals("Hello there!", ms[0].getContent());

		assertEquals(Instant.ofEpochSecond(1448470906), ms[1].getTimestamp());
		assertEquals("bob", ms[1].getSenderId());
		assertEquals("I'm good thanks, do you like pie?", ms[1].getContent());

		assertEquals(Instant.ofEpochSecond(1448470914), ms[2].getTimestamp());
		assertEquals("bob", ms[2].getSenderId());
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].getContent());

	}

}
