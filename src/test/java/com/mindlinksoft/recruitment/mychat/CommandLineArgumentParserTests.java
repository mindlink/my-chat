package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

public class CommandLineArgumentParserTests {

	CLIConfiguration config;
	final static String BLACKLIST = "blacklist";
	
	@Test
	public void testParseCommandLineArgumentMinimal() throws InvalidConfigurationException, MalformedOptionalCLIParameterException {
		final String INPUT = "inputfile";
		final String OUTPUT = "outputfile";
		
		String[] args = {INPUT, OUTPUT};
		config = CommandLineArgumentParser.parseCommandLineArguments(args);
		
		assertEquals(INPUT, config.getInputFilePath());
		assertEquals(OUTPUT, config.getOutputFilePath());
		assertTrue(config.getFilters().isEmpty());
		assertTrue(config.getFlags().isEmpty());
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testParseCommandLineArgumentMinimalException() throws InvalidConfigurationException, MalformedOptionalCLIParameterException {
		final String INPUT = "inputfile";
		
		String[] args = {INPUT};
		config = CommandLineArgumentParser.parseCommandLineArguments(args);
		
	}
	
	@Test
	public void testParseCommandLineArgumentSingle() throws InvalidConfigurationException, MalformedOptionalCLIParameterException {
		final String INPUT = "inputfile";
		final String OUTPUT = "outputfile";
		final String VALUE = "username";
		
		String[] args = {INPUT, OUTPUT, Options.FILTER_USERNAME, VALUE};
		config = CommandLineArgumentParser.parseCommandLineArguments(args);

		assertEquals(FilterUsername.class, config.getFilters().get(0).getClass());
	}
	
	@Test
	public void testParseCommandLineArgumentSingleOther() throws InvalidConfigurationException, MalformedOptionalCLIParameterException {
		final String INPUT = "inputfile";
		final String OUTPUT = "outputfile";
		final String VALUE = "keyword";
		
		String[] args = {INPUT, OUTPUT, Options.FILTER_KEYWORD, VALUE};
		config = CommandLineArgumentParser.parseCommandLineArguments(args);

		assertEquals(FilterKeyword.class, config.getFilters().get(0).getClass());
	}
	
	@Test
	public void testParseCommandLineArgumentTwoSingles() throws InvalidConfigurationException, MalformedOptionalCLIParameterException {
		final String INPUT = "inputfile";
		final String OUTPUT = "outputfile";
		final String VALUE1 = "username";
		final String VALUE2 = "keyword";
		
		String[] args = {INPUT, OUTPUT, Options.FILTER_USERNAME, VALUE1, 
						Options.FILTER_KEYWORD, VALUE2};
		config = CommandLineArgumentParser.parseCommandLineArguments(args);
		
		assertEquals(FilterUsername.class, config.getFilters().get(0).getClass());
		assertEquals(FilterKeyword.class, config.getFilters().get(1).getClass());
	}
	
	@Test
	public void testParseCommandLineArgumentMultiple() throws InvalidConfigurationException, MalformedOptionalCLIParameterException {
		final String INPUT = "inputfile";
		final String OUTPUT = "outputfile";
		final String VALUE1 = "'username";
		final String VALUE2 = "keyword";
		final String VALUE3 = "third'";
		
		String[] args = {INPUT, OUTPUT, Options.FILTER_BLACKLIST, VALUE1, 
				VALUE2, VALUE3};
		config = CommandLineArgumentParser.parseCommandLineArguments(args);
		
		assertEquals(FilterBlacklist.class, config.getFilters().get(0).getClass());
	}
	
//	@Test
//	public void testParseCommandLineArgumentsList() {
//		String args[] = {"inputFile", "outputFile", "-blacklist", "'you" , "he",
//				"she", "they'"};
//		
//		config = CommandLineArgumentParser.parseCommandLineArguments(args);
//
//		Field privateField;
//		List<ConversationFilter> filters = config.getFilters();
//		for(ConversationFilter filter : filters) {
//			
//			privateField = filter.getClass().getDeclaredField(BLACKLIST);
//			privateField.setAccessible(true);
//			String[] blacklist = (String[]) privateField.get(filter);
//
//		}
//		assertEquals("you he she they", config.get('b'));
//	}
//	
//	@Test
//	public void testParseCommandLineArguments() {
//		String args[] = {"inputFile", "outputFile", "-u", "optionValue"};
//		
//		config = CommandLineArgumentParser.parseCommandLineArguments(args);
//
//		assertNotNull(config.get('u'));
//		assertEquals("optionValue", config.get('u'));
//	}
//	
//	@Test
//	public void testParseCommandLineArgumentsNotValid() {
//		String args[] = {"inputFile", "outputFile", "-0invalid", "optionValue"};
//		
//		config = CommandLineArgumentParser.parseCommandLineArguments(args);
//
//		assertNull(config.get('0'));
//	}
//
//	@Test(expected=IllegalArgumentException.class)
//	public void testParseCommandLineArgumentsExceptions() {
//		String args[] = null;
//		
//		config = CommandLineArgumentParser.parseCommandLineArguments(args);
//	}
	
}
