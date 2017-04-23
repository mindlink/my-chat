package com.mindlinksoft.recruitment.mychat.formatters;
import java.time.Instant;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.formatters.BlacklistedWordsFormatter;
import com.mindlinksoft.recruitment.mychat.messages.Message;
public class BlacklistedWordsFormatterTest {

	private static BlacklistedWordsFormatter blacklistedWordsFromatter;
	
	@BeforeClass
	public static void Setup()
	{
		String[] blacklistWords = {"apple","pie"};
		blacklistedWordsFromatter = new BlacklistedWordsFormatter(blacklistWords);
	}
	@Test
	public void apply_MessgaeContainingBLWords_WordsReplaced()
	{
		Message message = new Message(Instant.ofEpochSecond(1448470905),"bob","I'm good thanks, do you like apple pie?");
		Assert.assertEquals("I'm good thanks, do you like *redacted* *redacted*?",blacklistedWordsFromatter.apply(message).getContent());
	}
	@Test
	public void apply_MessgaeNotContainingBLWords_NoWordsReplaced()
	{
		Message message = new Message(Instant.ofEpochSecond(1448470905),"bob","I'm good thanks, how are you?");
		Assert.assertFalse(blacklistedWordsFromatter.apply(message).getContent().contains("*redacted*"));
	}
}
