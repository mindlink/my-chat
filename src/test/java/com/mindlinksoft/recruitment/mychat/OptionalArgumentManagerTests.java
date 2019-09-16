package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;

import org.junit.Test;

/**
 * Tests for the {@link OptionalArgumentManager}.
 */
public class OptionalArgumentManagerTests {

	/**
     * Tests that a message isn't filtered out if a username and keyword match the specified cases
     */
	@Test
	public void testMessagePassesFilters() {
		OptionalArguments oas = new OptionalArguments();
		oas.usernameToFilter = "bob";
		oas.keywordToFilter = "tree";
		OptionalArgumentManager oam = new OptionalArgumentManager(oas);
		
		Message msg = new Message(Instant.now(), "bob", "here is a tree.");
		assertTrue(oam.messagePassesFilters(msg));
	}
	
	/**
     * Test that a message is filtered out if a username or keyword don't match the specified cases
     */
	@Test
	public void testMessageFailsFilters() {
		OptionalArguments oas = new OptionalArguments();
		oas.usernameToFilter = "bob";
		oas.keywordToFilter = "tree";
		OptionalArgumentManager oam = new OptionalArgumentManager(oas);
		
		Message msg = new Message(Instant.now(), "simon", "here is a tree.");
		assertFalse(oam.messagePassesFilters(msg));
	}

}
