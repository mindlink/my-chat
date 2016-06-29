package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

public class OptionsTests {

	@Test
	public void testNeedsSingleValue() {
		char c = Options.FILTER_USERNAME;
		
		assertTrue(Options.needsSingleValue(c));
		
		c = Options.FILTER_KEYWORD;
		
		assertTrue(Options.needsSingleValue(c));
		
		c = Options.FILTER_BLACKLIST;
		
		assertFalse(Options.needsSingleValue(c));
	}

	@Test
	public void testNeedsManyValues() {
		char c = Options.FILTER_BLACKLIST;
		
		assertTrue(Options.needsManyValues(c));
		
		c = Options.FILTER_KEYWORD;
		
		assertFalse(Options.needsManyValues(c));
	}
	
	@Test
	public void testNeedsNoValue() {
		assertFalse(Options.needsNoValue('c'));
	}
}
