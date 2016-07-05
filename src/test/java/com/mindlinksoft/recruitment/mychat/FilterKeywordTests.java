package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;

import org.junit.Test;

public class FilterKeywordTests {

	@Test
	public void testConstructor() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		FilterKeyword f = new FilterKeyword("word");
		
		Field keywordField = FilterKeyword.class.getDeclaredField("keyword");
		keywordField.setAccessible(true);
		String keywordValue = (String) keywordField.get(f);
		
		assertTrue(keywordValue.compareTo("word") == 0);
		assertFalse(keywordValue.compareTo("nonsense") == 0);
	}
	
	@Test
	public void testApply() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader("chat.txt"));
		Conversation conversation = reader.readConversation();
		reader.close();
		
		new FilterKeyword("pie").apply(conversation);
		
		assertEquals(4, conversation.messages.size());
		Message[] ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
		assertEquals(Instant.ofEpochSecond(1448470906), ms[0].getTimestamp());
		assertEquals("bob", ms[0].getSenderId());
		assertEquals("I'm good thanks, do you like pie?", ms[0].getContent());
		
		assertEquals(Instant.ofEpochSecond(1448470912), ms[1].getTimestamp());
		assertEquals("angus", ms[1].getSenderId());
		assertEquals("Hell yes! Are we buying some pie?", ms[1].getContent());

		assertEquals(Instant.ofEpochSecond(1448470914), ms[2].getTimestamp());
		assertEquals("bob", ms[2].getSenderId());
		assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].getContent());

		assertEquals(Instant.ofEpochSecond(1448470915), ms[3].getTimestamp());
		assertEquals("angus", ms[3].getSenderId());
		assertEquals("YES! I'm the head pie eater there...", ms[3].getContent());
		

	}

}
