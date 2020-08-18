package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCommandLineArgumentParser {

	@Test
	public void testConversationExporterConfigurator() {
		String[] args = {"../chat.txt", "../chat.json", "user=angus"};
        CommandLineArgumentParser argumentParserTest = new CommandLineArgumentParser();
        ConversationExporterConfiguration configuratorTest  = argumentParserTest.parseCommandLineArguments(args);
        CommandProcesser commandProcesserTest = new CommandProcesser();
        commandProcesserTest.setCommandName("user");
        commandProcesserTest.setCommandArguments("angus");
        commandProcesserTest.commandActivation(configuratorTest);
        assertEquals("user", commandProcesserTest.getCommandName());
        assertEquals("angus", commandProcesserTest.getCommandArguments());
	}

}
