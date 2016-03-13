package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests that arguments are correctly parsed.
 * @throws Exception When something bad happens.
 */
public class CommandLineArgumentParserTest {
	ConversationExporterConfiguration configuration;
	/**
	 * If there are no arguments it'll throw InvalidArgumentsException
	 * @throws Exception
	 */
	@Test(expected=InvalidArgumentsException.class)
	public void testNoCommandLineArguments() throws Exception {
		String[] args = new String[0]; 
		ConversationExporterConfiguration configuration = 
        		new CommandLineArgumentParser().parseCommandLineArguments(args);
	}
	
	/**
	 * If is there only one argument, it'll throw InvalidArgumentException
	 * @throws Exception
	 */
	@Test(expected=InvalidArgumentsException.class)
	public void testOneCommandLineArgument() throws Exception {
		String[] args = {"chat.txt"}; 
		ConversationExporterConfiguration configuration = 
        		new CommandLineArgumentParser().parseCommandLineArguments(args);
	}
	
	@Test
	public void testInputAndOutputArguments() throws Exception {
		String[] args = {"chat.txt", "output.json"}; 
		ConversationExporterConfiguration configuration = 
        		new CommandLineArgumentParser().parseCommandLineArguments(args);
		
		assertEquals(configuration.inputFilePath, args[0]);
		assertEquals(configuration.outputFilePath, args[1]);
	}
	
	@Test
	public void testInputOutputFilterArguments() throws Exception {
		String[] args = {"chat.txt", "output.json", "username=bob"}; 
		ConversationExporterConfiguration configuration = 
        		new CommandLineArgumentParser().parseCommandLineArguments(args);
		
		assertEquals(configuration.inputFilePath, args[0]);
		assertEquals(configuration.outputFilePath, args[1]);
		assertEquals(configuration.username, args[2].split(AppConstant.PARAMETER_SEPARATOR)[1]);
	}
	
	@Test
	public void testKeywordParameter() throws Exception {
		String[] args = {"chat.txt", "output.json", "keyword=details"}; 
		ConversationExporterConfiguration configuration = 
        		new CommandLineArgumentParser().parseCommandLineArguments(args);
		
		assertEquals(configuration.inputFilePath, args[0]);
		assertEquals(configuration.outputFilePath, args[1]);
		assertEquals(configuration.keyword, args[2].split(AppConstant.PARAMETER_SEPARATOR)[1]);
	}
	
	@Test
	public void testKeywordAndObfuscation() throws Exception {
		String[] args = {"chat.txt", "output.json", "keyword=details", "hide-id"}; 
		ConversationExporterConfiguration configuration = 
        		new CommandLineArgumentParser().parseCommandLineArguments(args);
		
		assertEquals(configuration.inputFilePath, args[0]);
		assertEquals(configuration.outputFilePath, args[1]);
		assertEquals(configuration.keyword, args[2].split(AppConstant.PARAMETER_SEPARATOR)[1]);
		assertEquals(configuration.hideId, true);
		assertEquals(configuration.hideCC, false);
		assertEquals(configuration.blacklist, null);
	}
	
	@Test
	public void testAllParameters() throws Exception {
		String[] args = {"chat.txt", "output.json", "username=bob", "keyword=details",
				"blacklist=Hello", "hide-cc", "hide-id", "include-report"};
		
		ConversationExporterConfiguration configuration = 
        		new CommandLineArgumentParser().parseCommandLineArguments(args);
		
		assertEquals(configuration.inputFilePath, args[0]);
		assertEquals(configuration.outputFilePath, args[1]);
		assertEquals(configuration.username, args[2].split(AppConstant.PARAMETER_SEPARATOR)[1]);
		assertEquals(configuration.keyword, args[3].split(AppConstant.PARAMETER_SEPARATOR)[1]);
		assertEquals(configuration.blacklist, args[4].split(AppConstant.PARAMETER_SEPARATOR)[1]);
		
		assertEquals(configuration.hideCC, true);
		assertEquals(configuration.hideId, true);
		assertEquals(configuration.includeReport, true);		
	}
}
