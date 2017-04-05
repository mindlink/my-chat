package com.mindlinksoft.recruitment.mychat.message;

import static org.junit.Assert.*;

import java.time.Instant;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the {@link KeywordMessageFilter}.
 */
public class KeywordMessageFilterTest {

	private static IMessageFilter keywordMsgFilter;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		keywordMsgFilter = new KeywordMessageFilter("Breska");
	}
	
	@Test
	public void shouldFilter() {
		IMessage msg = new Message(Instant.now(), "alex", "who ate all the pies?");
		assertTrue(keywordMsgFilter.filterMessage(msg));
	}

	@Test
	public void shouldNotFilter() {
		IMessage msg = new Message(Instant.now(), "alex", "Mario Breska ate all the pies.");
		assertFalse(keywordMsgFilter.filterMessage(msg));
	}
}
