package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

public class ConversationTest {
	/**
	 * Tests for the {@link Conversation}.
	 */
	Conversation conversation;
	
	/**
	 * Initialise the conversation before tests
	 */
	@Before
	public void setUp() {
		Collection<Message> messages = new ArrayList<Message>();
		messages.add(new Message(Instant.EPOCH, "bob", "Hello!"));
		messages.add(new Message(Instant.EPOCH, "john", "Hello!"));
		messages.add(new Message(Instant.EPOCH, "bob", "How are you?"));
		messages.add(new Message(Instant.EPOCH, "marcel", "Here is my credit card: 2124.3133.4323.2453."));
		messages.add(new Message(Instant.EPOCH, "maria", "My credit card: 2124-3133-4323-2453 details."));
		messages.add(new Message(Instant.EPOCH, "leo", "2124 3133 4323 2453 my credit card details."));
		messages.add(new Message(Instant.EPOCH, "edward", "My name is edward."));
		messages.add(new Message(Instant.EPOCH, "edward", "My name is edward."));

		conversation = new Conversation("Bob's conversation", messages);
	}
	
	@Test
	public void testApplyUsernameFilter() throws Exception {
		String filter = AppConstant.USERNAME;
		conversation.applyFilter(filter, "bob");
		assertEquals(conversation.messages.size(), 2);
		
		Message[] ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
		assertEquals(ms[0].senderId, "bob");
		assertEquals(ms[1].senderId, "bob");
	}
	
	@Test
	public void testApplyKeywordFilter() throws Exception {
		String filter = AppConstant.KEYWORD;
		conversation.applyFilter(filter, "Hello");
		assertEquals(conversation.messages.size(), 2);
		
		Message[] ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
		assertEquals(ms[0].senderId, "bob");
		assertEquals(ms[0].content, "Hello!");
		
		assertEquals(ms[1].senderId, "john");
		assertEquals(ms[1].content, "Hello!");
	}
	
	@Test
	public void testBlacklistWord() throws Exception {
		String blacklistValue = "details";
		
		conversation.blacklistKeyword(blacklistValue);
		assertEquals(conversation.messages.size(), 8);
		
		Message[] ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
		assertEquals(ms[4].senderId, "maria");
		assertEquals(ms[4].content, "My credit card: 2124-3133-4323-2453 *redacted*.");
		
		assertEquals(ms[5].senderId, "leo");
		assertEquals(ms[5].content, "2124 3133 4323 2453 my credit card *redacted*.");
	}
	
	@Test
	public void testHideCardNumber() throws Exception {
		conversation.hideCreditCard();

		Message[] ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
		assertEquals(ms[3].senderId, "marcel");
		assertEquals(ms[3].content, "Here is my credit card: *redacted*.");
		
		assertEquals(ms[4].senderId, "maria");
		assertEquals(ms[4].content, "My credit card: *redacted* details.");
		
		assertEquals(ms[5].senderId, "leo");
		assertEquals(ms[5].content, "*redacted* my credit card details.");
	}
	
	@Test
	public void testObfuscateUserId() throws Exception {
		Message[] ms = new Message[conversation.messages.size()];
		conversation.messages.toArray(ms);
		
		conversation.obfuscateId();
		
		assertTrue(!ms[6].content.contains(ms[6].senderId));
		assertEquals(ms[6].content, ms[7].content);
	}
}
