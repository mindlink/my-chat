package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CommandLineArgumentParserTests {

	@Test
	public void parserReturnsExpectedFileArguments() {
		CommandLineArgumentParser parser = new CommandLineArgumentParser();
		ConversationExporterConfiguration config = parser
				.generateExportOptions(new String [] {"inputfile.txt", "outputfile.txt"});
		assertEquals(config.inputFilePath, "inputfile.txt");
		assertEquals(config.outputFilePath, "outputfile.txt");
	}
	
	@Test
	public void parserReturnsExpectedFilterArguments() {
		CommandLineArgumentParser parser = new CommandLineArgumentParser();
		ConversationExporterConfiguration config = parser
				.generateExportOptions(new String[] {"inputfile.txt", "outputfile.txt", "-u", "bob" ,"-k",
						"pie", "-b", "pie,hello"});
		assertEquals(config.filter.toString(), "bob pie [pie, hello]");
		
	}
}
