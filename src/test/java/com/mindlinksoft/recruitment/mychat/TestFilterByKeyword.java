package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestFilterByKeyword {

	FilterByKeyword filterByKeywordTest = new FilterByKeyword();

	@Test
	public void testProcessParameters() {
		String arguments = "pie";
		filterByKeywordTest.processParameters(arguments);
		assertEquals("pie", filterByKeywordTest.getKeywordToFilter());
	}
	
	@Test
	public void testApplyFunctionality() {
		List<ParsedLine> parsedLineList = new ArrayList<ParsedLine>();
		parsedLineList.add(new ParsedLine("123456", "Bob", "Hello there!"));
		parsedLineList.add(new ParsedLine("123456", "Bob", "I would like some pie"));

	
		filterByKeywordTest.processParameters("pie");
		assertEquals(false, filterByKeywordTest.applyFunctionality(parsedLineList.get(0)));
		assertEquals(true, filterByKeywordTest.applyFunctionality(parsedLineList.get(1)));
	}


}
