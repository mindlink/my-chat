package com.mindlinksoft.recruitment.mychat.message;

import static org.junit.Assert.*;

import java.time.Instant;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the {@link UserIdMessageFilter}.
 */
public class UserIdMessageFilterTest {

	private static IMessageFilter userIdMsgFilter;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		userIdMsgFilter = new UserIdMessageFilter("alex");
	}
	
	@Test
	public void shouldFilter() {
		IMessage msg = new Message(Instant.now(), "joe", "who ate all the pies?");
		assertTrue(userIdMsgFilter.filterMessage(msg));
	}

	@Test
	public void shouldNotFilter() {
		IMessage msg = new Message(Instant.now(), "alex", "Mario Breska ate all the pies.");
		assertFalse(userIdMsgFilter.filterMessage(msg));
	}

}
