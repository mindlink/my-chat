package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for the {@link CommandLineArgumentParser}.
 */
public class CommandLineArgumentParserTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	/**
	 * Tests that a config object is successfully created when supplied with the correct parameters
	 */
	@Test
	public void testReturnsValidConversationExporterConfigurationFromArgumentsWithNoOptionals() {
		String[] arguments = {"input.txt", "output.json"};
		ConversationExporterConfiguration config = CommandLineArgumentParser.parseCommandLineArguments(arguments);
		
		assertEquals("input.txt", config.inputFilePath);
		assertEquals("output.json", config.outputFilePath);
	}

	/**
	 * Tests that a file type is deemed valid if its type matches the given string
	 */
	@Test
	public void testFileTypeIdentifiedAsValidWithValidType() {
		String fileName = "test.txt";
		String fileType = ".txt";
		
		assertTrue(CommandLineArgumentParser.fileTypeIsValid(fileName, fileType));
	}
	
	/**
	 * Tests that a file type is deemed invalid if its type doesnt match the given string
	 */
	@Test
	public void testFileTypeIdentifiedAsInvalidWithInvalidType() {
		String fileName = "test.txt";
		String fileType = ".json";
		
		assertFalse(CommandLineArgumentParser.fileTypeIsValid(fileName, fileType));
	}
	
	/**
	 * Tests that an error is thrown if too few arguments are supplied
	 */
	@Test
	public void testArgumentExceptionThrownWhenMissingParameters() {
		String[] arguments = {};
		
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("ConversationExporter must be run as: java ConversationExporter 'input.txt' 'output.json' 'optional arguements...'");
		
		CommandLineArgumentParser.parseCommandLineArguments(arguments);
	}
	
	/**
	 * Tests that an error is thrown if the input file supplied is not of type .txt
	 */
	@Test
	public void testArgumentExceptionThrownWhenInputFileIsNotOfTypeTxt() {
		String[] arguments = {"input.wrong", "output.json"};
		
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("First command-line argument must be an input file of type .txt");
		
		CommandLineArgumentParser.parseCommandLineArguments(arguments);
	}
	
	/**
	 * Tests that an error is thrown if the output file supplied is not of type .json
	 */
	@Test
	public void testArgumentExceptionThrownWhenOutputFileIsNotOfTypeJson() {
		String[] arguments = {"input.txt", "output.wrong"};
		
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Second command-line argument must be an output file of type .json");
		
		CommandLineArgumentParser.parseCommandLineArguments(arguments);
	}
}
