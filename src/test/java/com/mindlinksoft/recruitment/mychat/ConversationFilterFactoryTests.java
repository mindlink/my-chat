package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.Test;

public class ConversationFilterFactoryTests {

	final String KEYWORD = "keyword value";
	final String[] BLACKLIST = { "black", "list", "value"};
	
	@Test
	public void testCreateFilterString() throws UnrecognizedCLIOptionException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ConversationFilter expected = new FilterKeyword(KEYWORD);
		ConversationFilter actual = ConversationFilterFactory.createFilter(
											Options.FILTER_KEYWORD, KEYWORD);
		
		//access private field:
		Field keywordField = FilterKeyword.class.getDeclaredField("keyword");
		keywordField.setAccessible(true);
		
		String expectedKeywordValue = (String) keywordField.get(expected);
		String actualKeywordValue = (String) keywordField.get(actual);
		
		assertTrue(expectedKeywordValue.compareTo(actualKeywordValue) == 0);
		assertFalse(expectedKeywordValue.compareTo((String) keywordField.get(new FilterKeyword("nonsense"))) == 0);
	}
	
	@Test
	public void testCreateFilterStringArray() throws UnrecognizedCLIOptionException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ConversationFilter expected = new FilterBlacklist(BLACKLIST);
		ConversationFilter actual = ConversationFilterFactory.createFilter(
											Options.FILTER_BLACKLIST, BLACKLIST);
		
		//access private field:
		Field blacklistField = FilterBlacklist.class.getDeclaredField("blacklist");
		blacklistField.setAccessible(true);
		
		String[] expectedBlacklistValue = (String[]) blacklistField.get(expected);
		String[] actualBlacklistValue = (String[]) blacklistField.get(actual);
		
		assertArrayEquals(expectedBlacklistValue, actualBlacklistValue);
		assertFalse(Arrays.equals(new String[] {"nonsense"}, expectedBlacklistValue));
//		for(String e : expectedBlacklistValue)
//		System.out.println(e + " ");
	
	}
	
	

}
