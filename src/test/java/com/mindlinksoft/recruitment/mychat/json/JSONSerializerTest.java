package com.mindlinksoft.recruitment.mychat.json;

import java.time.Instant;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.messages.InvalidMessageException;
import com.mindlinksoft.recruitment.mychat.messages.Message;


import org.junit.Assert;



public class JSONSerializerTest {
	private static JSONSerializer jsonSerializer;
	
	
	@BeforeClass
	public static void Setup()
	{
		jsonSerializer = new JSONSerializer();
	}
	
	@Test
	public void toJSON_Message_MessageConverted() throws NumberFormatException, InvalidMessageException
	{
		Message message = new Message(Instant.ofEpochSecond(1448470905),"bob","Hi there");

		Assert.assertEquals("{\n  \"content\": \"Hi there\",\n  \"timestamp\": 1448470905,\n  \"senderId\": \"bob\"\n}", jsonSerializer.toJSON(message));
	}
}
