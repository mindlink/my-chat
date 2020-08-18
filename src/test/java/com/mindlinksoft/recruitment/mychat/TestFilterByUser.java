package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestFilterByUser {
	
	FilterByUser filterByUserTest = new FilterByUser();

	@Test
	public void testProcessParameters() {
		String arguments = "angus";
		filterByUserTest.processParameters(arguments);
		assertEquals("angus", filterByUserTest.getUserToFilter());
	}
	
	@Test
	public void testApplyFunctionality() {
		ParsedLine parsedLineTest = new ParsedLine("123456", "Bob", "Hello there!");
		filterByUserTest.processParameters("John");
		assertEquals(false, filterByUserTest.applyFunctionality(parsedLineTest));
	}

}
