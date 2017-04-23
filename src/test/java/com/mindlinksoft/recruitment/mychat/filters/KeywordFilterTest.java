package com.mindlinksoft.recruitment.mychat.filters;

import java.time.Instant;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.messages.Message;


public class KeywordFilterTest {

	private static KeywordFilter keywordFilter;
	@BeforeClass
	public static void Setup()
	{
		String keyword = "pie";
		keywordFilter = new KeywordFilter(keyword);
	}
	@Test
	public void apply_MessgaeContainingKeyWord_ReturnsTrue()
	{
		Message message = new Message(Instant.ofEpochSecond(1448470905),"bob","I'm good thanks, do you like apple pie?");
		Assert.assertTrue(keywordFilter.apply(message));
	}
	@Test
	public void apply_MessgaeNotContainingKeyWord_ReturnsFalse()
	{
		Message message = new Message(Instant.ofEpochSecond(1448470905),"bob","I'm good thanks, do you like apple ?");
		Assert.assertFalse(keywordFilter.apply(message));
	}
}
