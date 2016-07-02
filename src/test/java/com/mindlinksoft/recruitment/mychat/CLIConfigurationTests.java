package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.nio.file.InvalidPathException;

import org.junit.Before;
import org.junit.Test;

public class CLIConfigurationTests {
	
	final String GOOD = "chat.txt";
	final String BAD = "\0";
	
	CLIConfiguration config;
	
	
	@Before
	public void setUp() {
		config = new CLIConfiguration(GOOD, GOOD);
	}
	
	@Test
	public void testSetInputFile() {
		config.setInputFilePath(GOOD);
		assertEquals(GOOD, config.getInputFilePath());
	}
	
	@Test(expected=InvalidPathException.class)
	public void testSetInputFileFailure() {
		config.setInputFilePath(BAD);
		fail();
	}

	@Test
	public void testSetOutputFile() {
		config.setOutputFilePath(GOOD);
		assertEquals(GOOD, config.getOutputFilePath());
	}
	
	@Test(expected=InvalidPathException.class)
	public void testSetOutputFileFailure() {
		config.setOutputFilePath(BAD);
		fail();
	}
	
	@Test
	public void testSetGetFlag() {
		config.setFlag("new flag");
		assertTrue(config.getFlags().contains("new flag"));
	}

	@Test
	public void testSetGetFilters() {
		ConversationFilter expected = new FilterUsername("the username");
		config.addFilter(expected);
		assertTrue(config.getFilters().contains(expected));
	}
	
}
