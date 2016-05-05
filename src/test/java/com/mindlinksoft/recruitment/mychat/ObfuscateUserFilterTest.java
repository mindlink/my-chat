package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

public class ObfuscateUserFilterTest {

	private static final String[] sampleConversation = {
			"1448470901 mike Hello there!",
			"1448470905 mike how are you?",
			"1448470906 bob I'm good thanks, do you like pie?",
			"1448470910 mike no, let me ask Angus...",
			"1448470912 angus Hell yes! Are we buying some pie?",
			"1448470914 bob No, just want to know if there's anybody else in the pie society..."
	};
	
	@Test
	public void testSenderIdObfuscatedCorrectly() {
		Conversation c = TestUtilities.getSampleConversation(sampleConversation);
		
		ObfuscateUserFilter f = new ObfuscateUserFilter();
		
		c = f.apply(c);
		
		Message[] ms = new Message[c.getMessages().size()];
	    c.getMessages().toArray(ms);
	    
	    assertEquals("User 1", ms[0].getSenderId());
	    assertEquals("User 1", ms[1].getSenderId());
	    assertEquals("User 2", ms[2].getSenderId());
	    assertEquals("User 1", ms[3].getSenderId());
	    assertEquals("User 3", ms[4].getSenderId());
	    assertEquals("User 2", ms[5].getSenderId());
	}

}
