package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCommandProcesser {

	@Test
	public void testCommandActivation() {
		String inputFilePath = "../chat.txt";
		String outputFilePath = "../chat.json";
		CommandProcesser commandProcesserTest = new CommandProcesser();
		FilterByUser filterByUserTest = new FilterByUser();
		commandProcesserTest.setCommandName("user");
		commandProcesserTest.setCommandArguments("Bob");
		
		assertEquals("user", commandProcesserTest.getCommandName());
		assertEquals("Bob", commandProcesserTest.getCommandArguments());
		filterByUserTest.processParameters(commandProcesserTest.getCommandArguments());
		assertEquals("Bob", filterByUserTest.getUserToFilter());
		ConversationExporterConfiguration exporterConfigurationTest = new ConversationExporterConfiguration(inputFilePath, outputFilePath);
		exporterConfigurationTest.addFunctionality(filterByUserTest);
		assertEquals(false, exporterConfigurationTest.isFunctionalityEmpty());
	}

}
