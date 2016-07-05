package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;

import org.junit.Test;

public class FilterBlacklistTests {

	final String[] justOne = {"yes"};
	final String[] two = {"yes", "\tYOU\n"};
	
	@Test
	public void testConstructor() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		FilterBlacklist f = new FilterBlacklist(two);
		
		Field blacklistField = FilterBlacklist.class.getDeclaredField("blacklist");
		blacklistField.setAccessible(true);
		String[] blacklistValue = (String[]) blacklistField.get(f);
		
		assertArrayEquals(two, blacklistValue);
	}
	
	@Test
	public void testApplySingle() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader("chat.txt"));
		Conversation conversation = reader.readConversation();
		reader.close();
		
		new FilterBlacklist(justOne).apply(conversation);
		
		List<Message> messages = conversation.messages;
		assertEquals(7, messages.size());

		Message[] ms = new Message[messages.size()];
		messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
		assertEquals("angus", ms[4].getSenderId());
		assertEquals("Hell *redacted*! Are we buying some pie?", ms[4].getContent());

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
		assertEquals("angus", ms[6].getSenderId());
		assertEquals("*redacted*! I'm the head pie eater there...", ms[6].getContent());

	}
	
	@Test
	public void testApplyTwo() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader("chat.txt"));
		Conversation conversation = reader.readConversation();
		reader.close();
		
		new FilterBlacklist(two).apply(conversation);
		
		List<Message> messages = conversation.messages;
		assertEquals(7, messages.size());

		Message[] ms = new Message[messages.size()];
		messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
		assertEquals("mike", ms[1].getSenderId());
		assertEquals("how are *redacted*?", ms[1].getContent());

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
		assertEquals("bob", ms[2].getSenderId());
		assertEquals("I'm good thanks, do *redacted* like pie?", ms[2].getContent());
		
		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
		assertEquals("angus", ms[4].getSenderId());
		assertEquals("Hell *redacted*! Are we buying some pie?", ms[4].getContent());

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
		assertEquals("angus", ms[6].getSenderId());
		assertEquals("*redacted*! I'm the head pie eater there...", ms[6].getContent());

	}

}
