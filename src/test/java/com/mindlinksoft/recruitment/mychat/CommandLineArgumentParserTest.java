package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.conversation.exporter.ConversationExporterConfiguration;

public class CommandLineArgumentParserTest {

	@Test
	public void testCommandLineParser() throws Exception {
		String arguments = "chat.txt chat.json -n" ;
		
		CommandLineArgumentParser parser = new CommandLineArgumentParser();
		
		ConversationExporterConfiguration config = parser.parseCommandLineArguments(arguments.split(" "));
		
		//TODO: assert if the correct formatters are created
		assertEquals("chat.txt", config.getInputFilePath());
		assertEquals("chat.json", config.getOutputFilePath());
		assertNotNull(config.getConversationFormatter());
	}

}
