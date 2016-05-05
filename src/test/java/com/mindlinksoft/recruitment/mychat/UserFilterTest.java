package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserFilterTest {
	
	private static final String[] messages = {
			"1448470901 bob Hello there!",
			"1448470905 mike how are you?",
			"1448470906 bob I'm good thanks, do you like pie?"	
	};

	@Test
	public void testCorrectlyFiltersByUser() throws Exception {
		Conversation c = TestUtilities.getSampleConversation(messages);
		
		String user = "bob";
		UserFilter f = new UserFilter(user);
		
		c = f.apply(c);
		
		Message[] ms = new Message[c.getMessages().size()];
	    c.getMessages().toArray(ms);

	    assertEquals(2, ms.length);
		assertEquals(user, ms[0].getSenderId());
		assertEquals(user, ms[1].getSenderId());
	}

}
