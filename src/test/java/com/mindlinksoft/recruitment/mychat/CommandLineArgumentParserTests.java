package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandLineArgumentParserTests {

	ConversationExporterConfiguration config;
	
	@Test
	public void testParseCommandLineArgumentsList() {
		String args[] = {"inputFile", "outputFile", "-blacklist", "'you" , "he",
				"she", "they'"};
		
		config = CommandLineArgumentParser.parseCommandLineArguments(args);

		assertNotNull(config.get('b'));
		assertEquals("you he she they", config.get('b'));
	}
	
	@Test
	public void testParseCommandLineArguments() {
		String args[] = {"inputFile", "outputFile", "-u", "optionValue"};
		
		config = CommandLineArgumentParser.parseCommandLineArguments(args);

		assertNotNull(config.get('u'));
		assertEquals("optionValue", config.get('u'));
	}
	
	@Test
	public void testParseCommandLineArgumentsNotValid() {
		String args[] = {"inputFile", "outputFile", "-0invalid", "optionValue"};
		
		config = CommandLineArgumentParser.parseCommandLineArguments(args);

		assertNull(config.get('0'));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testParseCommandLineArgumentsExceptions() {
		String args[] = null;
		
		config = CommandLineArgumentParser.parseCommandLineArguments(args);
	}
}
