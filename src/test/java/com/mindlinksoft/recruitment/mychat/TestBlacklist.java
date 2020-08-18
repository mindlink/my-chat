package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestBlacklist {

	Blacklist blacklistTest = new Blacklist();

	@Test
	public void testProcessParameters() {
		String arguments = "pie,just";
		blacklistTest.processParameters(arguments);
		assertEquals("pie", blacklistTest.getWordsToBan().get(0));
		assertEquals("just", blacklistTest.getWordsToBan().get(1));
	}
	
	@Test
	public void testApplyFunctionality() {
		List<ParsedLine> parsedLineList = new ArrayList<ParsedLine>();
		parsedLineList.add(new ParsedLine("123456", "Bob", "Hello there!"));
		parsedLineList.add(new ParsedLine("123456", "Frank", "Would you like some pie?"));

	
		blacklistTest.processParameters("pie, just");
		assertEquals("Hello there!", blacklistTest.applyFunctionality(parsedLineList.get(0)));
		assertEquals("Would you like some *redacted*?", blacklistTest.applyFunctionality(parsedLineList.get(1)));
	}


}
