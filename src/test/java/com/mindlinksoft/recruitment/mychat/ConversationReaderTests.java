package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the {@link ConversationReader}.
 */
public class ConversationReaderTests {
	/**
	 * Test that the correct conversation is returned when filtering usernames
	 */
	@Test
	public void testReadConversationWithNameFilter() {	
		String[] arguments = {"chat.txt", "output.json", "fun-bob"};
		ConversationExporterConfiguration config = CommandLineArgumentParser.parseCommandLineArguments(arguments);
		
		try {
			Conversation conversation = ConversationReader.readConversation(config.inputFilePath, config.optionalArgumentManager);
			
			assertTrue(conversation.messages.get(1).senderId.equals("bob"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test that the correct conversation is returned when filtering keywords
	 */
	@Test
	public void testReadConversationWithKeywordFilter() {	
		String[] arguments = {"chat.txt", "output.json", "fkw-pie"};
		ConversationExporterConfiguration config = CommandLineArgumentParser.parseCommandLineArguments(arguments);
		
		try {
			Conversation conversation = ConversationReader.readConversation(config.inputFilePath, config.optionalArgumentManager);
			
			assertTrue(conversation.messages.get(1).senderId.equals("angus"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
