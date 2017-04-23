package com.mindlinksoft.recruitment.mychat.filters;

import java.time.Instant;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.messages.Message;

public class SenderIDFilterTest {
	private static SenderIDFilter senderIDFilter;
	@BeforeClass
	public static void Setup()
	{
		String senderID = "john";
		senderIDFilter = new SenderIDFilter(senderID);
	}
	@Test
	public void apply_MessgaeBySenderID_ReturnsTrue()
	{
		Message message = new Message(Instant.ofEpochSecond(1448470905),"john","I'm good thanks, do you like apple pie?");
		Assert.assertTrue(senderIDFilter.apply(message));
	}
	@Test
	public void apply_MessgaeNotBySenderID_ReturnsFalse()
	{
		Message message = new Message(Instant.ofEpochSecond(1448470905),"bob","I'm good thanks, do you like apple ?");
		Assert.assertFalse(senderIDFilter.apply(message));
	}
}
