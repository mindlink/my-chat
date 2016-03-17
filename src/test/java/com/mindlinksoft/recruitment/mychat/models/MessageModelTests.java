package com.mindlinksoft.recruitment.mychat.models;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import org.junit.Test;

/**
 * Tests for the {@link Message}.
 */
public class MessageModelTests {
	
	/**
	 * Test that a {@link Message} object is created successfully.
	 */
	@Test
	public void testMessageModelCreated() {
		Message message = new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!");
    	
        assertEquals(message.getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(message.getSenderId(), "bob");
        assertEquals(message.getContent(), "Hello there!");
	}
	
	/**
	 * Test that a {@link Message} object toString method returns a
	 * valid string that can be printed.
	 */
	@Test
	public void testMessageToStringReturnsString() {
		Message message = new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!");
		
		assertEquals(message.toString().getClass(), String.class);
	}
}
