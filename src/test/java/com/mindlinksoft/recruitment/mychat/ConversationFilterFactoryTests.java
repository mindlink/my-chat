package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

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
		Field field = FilterKeyword.class.getDeclaredField("keyword");
		field.setAccessible(true);
		assertEquals(field.get(expected), field.get(actual));
		field.setAccessible(false);
	}
	
	@Test
	public void testCreateFilterStringArray() throws UnrecognizedCLIOptionException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ConversationFilter expected = new FilterBlacklist(BLACKLIST);
		ConversationFilter actual = ConversationFilterFactory.createFilter(
											Options.FILTER_BLACKLIST, BLACKLIST);
		
		//access private field:
		Field field = FilterBlacklist.class.getDeclaredField("blacklist");
		field.setAccessible(true);
		assertEquals(field.get(expected), field.get(actual));
		field.setAccessible(false);
	}
	
	

}
