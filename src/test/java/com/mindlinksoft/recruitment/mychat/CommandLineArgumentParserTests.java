package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandLineArgumentParserTests {

	ConversationExporterConfiguration config;
	
	@Test
	public void testParseCommandLineArguments() {
		String args[] = {"inputFile", "outputFile", "-option", "optionValue"};
		
		config = CommandLineArgumentParser.parseCommandLineArguments(args);

		assertNotNull(config.get("-o"));
		assertEquals("optionValue", config.get("-o"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testParseCommandLineArgumentsExceptions() {
		String args[] = null;
		
		config = CommandLineArgumentParser.parseCommandLineArguments(args);
	}
}
