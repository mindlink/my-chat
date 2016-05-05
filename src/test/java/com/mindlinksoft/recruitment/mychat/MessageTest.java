package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;

import org.junit.Test;

public class MessageTest {

    /**
     * Tests that parsing a message String will return valid Message object.
     */
	@Test
	public void testParseMessageReturnsMessage() throws Exception {	
		Message message = Message.parseMessage("1448470901 bob Hello there!");
		
        assertEquals(message.getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(message.getSenderId(), "bob");
        assertEquals(message.getContent(), "Hello there!");
	}
	
	@Test (expected = RuntimeException.class)
	public void testParseMessageInvalidMessage_Exception() {
		
		@SuppressWarnings("unused")
		Message message = Message.parseMessage("1448470901there!");
	}
	
	@Test (expected = RuntimeException.class)
	public void testParseMessageInvalidTimestamp_Exception() {
		
		@SuppressWarnings("unused")
		Message message = Message.parseMessage("14484xx70901 bob Hello there!");
	}
}
