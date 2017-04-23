package com.mindlinksoft.recruitment.mychat.messages;

import java.time.Instant;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class MessageParserTest {
	private static MessageParser messageParser;
	
	
	@BeforeClass
	public static void Setup()
	{
		messageParser = new MessageParser();
	}
	
	@Test
	public void parseMessage_ValidMessage_MessageParsed() throws NumberFormatException, InvalidMessageException
	{
		Message message = messageParser.parse("1448470905 bob I'm good thanks, do you like apple pie?");
		Assert.assertEquals(Instant.ofEpochSecond(1448470905),message.getTimestamp());
		Assert.assertEquals("bob",message.getSenderId());
		Assert.assertEquals("I'm good thanks, do you like apple pie?",message.getContent());
	}
	
	@Test(expected=InvalidMessageException.class)
	public void parseMessage_MessageWithTwoParts_ThrowsException() throws NumberFormatException, InvalidMessageException
	{
		messageParser.parse("1448470905 bob");
	}
}
