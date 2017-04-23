package com.mindlinksoft.recruitment.mychat.formatters;

import java.time.Instant;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.formatters.ObfuscateUserIDFormatter;
import com.mindlinksoft.recruitment.mychat.messages.Message;


public class ObfuscateUserIDFormatterTest {
	private static ObfuscateUserIDFormatter obfuscateUserIDFormatter;
	
	@BeforeClass
	public static void Setup()
	{

		obfuscateUserIDFormatter = new ObfuscateUserIDFormatter();
		
	}
	@Test
	public void apply_MessgaesWithEqualAndDifferentSenderIDs_SenderIDsObfuscateAccordingly()
	{
		Message message1 = new Message(Instant.ofEpochSecond(1448470905),"bob","Hey there!");
		Message message2 = new Message(Instant.ofEpochSecond(1448470905),"bob","How are you?");
		Message message3 = new Message(Instant.ofEpochSecond(1448470905),"john","Hi");
		Message message4 = new Message(Instant.ofEpochSecond(1448470905),"bob","Where have you been?");
		Message message5 = new Message(Instant.ofEpochSecond(1448470905),"mike","Hey guys");
		
		obfuscateUserIDFormatter.apply(message1).getSenderId();
		obfuscateUserIDFormatter.apply(message2).getSenderId();
		obfuscateUserIDFormatter.apply(message3).getSenderId();
		obfuscateUserIDFormatter.apply(message4).getSenderId();
		obfuscateUserIDFormatter.apply(message5).getSenderId();
		
		Assert.assertEquals(message1.getSenderId(),message2.getSenderId(),message4.getSenderId());
		
		Assert.assertNotEquals(message1.getSenderId(),message3.getSenderId());
		Assert.assertNotEquals(message3.getSenderId(),message5.getSenderId());
		Assert.assertNotEquals(message1.getSenderId(),message5.getSenderId());
		

	}
}
