package com.mindlinksoft.recruitment.mychat.models;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tests for the {@link ConversationExporterConfiguration}.
 */
public class ConversationExporterConfigurationModelTests {

	/**
	 * Test that a {@link ConversationExporterConfiguration} object is created successfully.
	 */
	@Test
	public void testConversationExporterConfigurationModelCreated() {
		List<String> stubBlacklist = new ArrayList<String>();
		stubBlacklist.add("cat");
		stubBlacklist.add("dog");
		stubBlacklist.add("cat dog");
		
		ConversationExporterConfiguration config = new ConversationExporterConfiguration(
				"input.txt", "output.txt", "bob", "pie", stubBlacklist);
    	
        assertEquals(config.getInputFilePath(), "input.txt");
        assertEquals(config.getOutputFilePath(), "output.txt");
        assertEquals(config.getUser(), "bob");
        assertEquals(config.getKeyword(), "pie");
        
        String[] blacklist = new String[3];
    	config.getBlacklist().toArray(blacklist);
    	
    	assertEquals(blacklist[0], "cat");
    	assertEquals(blacklist[1], "dog");
    	assertEquals(blacklist[2], "cat dog");
	}
	
	/**
	 * Test that a {@link ConversationExporterConfiguration} object toString method returns a
	 * valid string that can be printed.
	 */
	@Test
	public void testConversationExporterConfigurationToStringReturnsString() {
		List<String> stubBlacklist = new ArrayList<String>();
		stubBlacklist.add("cat");
		
		ConversationExporterConfiguration config = new ConversationExporterConfiguration(
				"input.txt", "output.txt", "bob", "pie", stubBlacklist);
		
		assertEquals(config.toString().getClass(), String.class);
	}
}
