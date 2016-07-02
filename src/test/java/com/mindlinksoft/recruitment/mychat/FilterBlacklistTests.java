package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;

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
		
		assertEquals(7, conversation.messages.size());

		Message[] ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
		assertEquals("angus", ms[4].senderId);
		assertEquals("Hell *redacted*! Are we buying some pie?", ms[4].content);

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
		assertEquals("angus", ms[6].senderId);
		assertEquals("*redacted*! I'm the head pie eater there...", ms[6].content);

	}
	
	@Test
	public void testApplyTwo() throws IOException {
		ConversationReader reader = new ConversationReader(new FileReader("chat.txt"));
		Conversation conversation = reader.readConversation();
		reader.close();
		
		new FilterBlacklist(two).apply(conversation);
		
		assertEquals(7, conversation.messages.size());

		Message[] ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);

		assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
		assertEquals("mike", ms[1].senderId);
		assertEquals("how are *redacted*?", ms[1].content);

		assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
		assertEquals("bob", ms[2].senderId);
		assertEquals("I'm good thanks, do *redacted* like pie?", ms[2].content);
		
		assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
		assertEquals("angus", ms[4].senderId);
		assertEquals("Hell *redacted*! Are we buying some pie?", ms[4].content);

		assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
		assertEquals("angus", ms[6].senderId);
		assertEquals("*redacted*! I'm the head pie eater there...", ms[6].content);

	}

}
