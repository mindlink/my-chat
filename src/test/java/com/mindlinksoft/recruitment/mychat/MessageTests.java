package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

import org.junit.Test;

public class MessageTests {
	
	@Test
	public void testReturnMethods() {
		Instant testInstant = Instant.parse("2010-09-21T11:35:23Z");
		
		Message testMessage = new Message(testInstant, "aaron", "This is a test message!");
		
		assert(testMessage.getContent() == "This is a test message!");
		assert(testMessage.getTimestamp() == testInstant);
		assert(testMessage.getSenderId() == "aaron");
	}

}
