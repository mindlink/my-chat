package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class CommandLineArgumentParserTest {

	/**
	 * Tests for the {@link CommandLineArgumentParser}.
	 */
	@Test
	public void testParserReturnsValidObjectWithParameters() {
		
		String[] args = {"file_read.txt","file_write.json","-u", "bob", "-w", "you", "-r", "pie", "-secure", "-anonymous", "-report"};
		
		ConversationExporterConfiguration c = CommandLineArgumentParser.parseCommandLineArguments(args);
			
		assertEquals(args[0], c.getInputFilePath());
		assertEquals(args[1], c.getOutputFilePath());
		
		List<Filter> filters = c.getFilters();
		
		assertEquals(6, filters.size());
		
		boolean hasUserFilter=false;
		boolean hasWordFilter=false;
		boolean hasObfuscateFilter=false;
		int redactFilter=0;
		
		for(Filter f: filters) {
			if(f instanceof UserFilter) {
				hasUserFilter=true;
			}
			else if(f instanceof WordFilter) {
				hasWordFilter=true;
			}
			else if(f instanceof RedactFilter) {
				++redactFilter;
			}
			else if(f instanceof ObfuscateUserFilter) {
				hasObfuscateFilter=true;
			}
		}
		
		assertTrue(hasUserFilter && hasWordFilter && hasObfuscateFilter);
		assertEquals(3, redactFilter);  // 3 Filters: "pie", card numbers, phone numbers
		assertTrue(c.useReport());
	}
	
	@Test (expected = RuntimeException.class)
	public void testBadParameters_Exception() {
		
		String[] args = {"file_read.txt","file_write.json","-X", "word"};
		
		@SuppressWarnings("unused")
		ConversationExporterConfiguration c = CommandLineArgumentParser.parseCommandLineArguments(args);
	}
	
	@Test (expected = RuntimeException.class)
	public void testInsufficientParameters_Exception() {

		String[] args = {"file_read.txt"};
		
		@SuppressWarnings("unused")
		ConversationExporterConfiguration c = CommandLineArgumentParser.parseCommandLineArguments(args);
	}
}
