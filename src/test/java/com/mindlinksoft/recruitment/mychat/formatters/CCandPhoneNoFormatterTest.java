package com.mindlinksoft.recruitment.mychat.formatters;

import java.time.Instant;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.formatters.CCandPhoneNoFormatter;
import com.mindlinksoft.recruitment.mychat.messages.Message;

public class CCandPhoneNoFormatterTest {
	private static CCandPhoneNoFormatter ccandPhoneNoFormatter;
	
	@BeforeClass
	public static void Setup()
	{
		ccandPhoneNoFormatter = new CCandPhoneNoFormatter();
		
	}
	@Test
	public void apply_MessgaeContainingCCandPhonrNo_WordsReplaced()
	{
		Message message = new Message(Instant.ofEpochSecond(1448470905),"bob","My Credit cards number is 4123-4567-9875-3214 and my phone is 0035799876253");
		Assert.assertEquals("My Credit cards number is *redacted* and my phone is *redacted*",ccandPhoneNoFormatter.apply(message).getContent());
	}
	@Test
	public void apply_MessgaeNotContainingCCorPhonrNo_NoWordsReplaced()
	{
		Message message = new Message(Instant.ofEpochSecond(1448470905),"bob","I'm good thanks, how are you?");
		Assert.assertFalse(ccandPhoneNoFormatter.apply(message).getContent().contains("*redacted*"));
	}
}
