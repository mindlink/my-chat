package com.mindlinksoft.recruitment.mychat.models;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tests for the {@link Conversation}.
 */
public class ConversationModelTests {

	/**
	 * Test that a {@link Conversation} object is created successfully.
	 */
	@Test
	public void testConversationModelCreated() {
		List<Message> stubMessages = new ArrayList<Message>();
		stubMessages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
		stubMessages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
    	
		Conversation conversation = new Conversation("My Conversation", stubMessages);
		
		Message[] messages = new Message[conversation.getMessages().size()];
    	conversation.getMessages().toArray(messages);
    	
		assertEquals(conversation.getName(), "My Conversation");
        assertEquals(conversation.getMessages().size(), 2);

        assertEquals(messages[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(messages[0].getSenderId(), "bob");
        assertEquals(messages[0].getContent(), "Hello there!");

        assertEquals(messages[1].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(messages[1].getSenderId(), "mike");
        assertEquals(messages[1].getContent(), "how are you?");
	}
	
	/**
	 * Test that a {@link Conversation} object toString method returns a
	 * valid string that can be printed.
	 */
	@Test
	public void testConversationToStringReturnsString() {
		List<Message> stubMessages = new ArrayList<Message>();
		stubMessages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
		stubMessages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
    	
		Conversation conversation = new Conversation("My Conversation", stubMessages);
		
		assertEquals(conversation.toString().getClass(), String.class);
	}
}
