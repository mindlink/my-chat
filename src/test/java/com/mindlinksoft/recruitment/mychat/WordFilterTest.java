package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

public class WordFilterTest {

	private static final String[] testMessages = {
			"1448470901 bob Hello there!",
			"1448470905 mike how are you?",
			"1448470906 bob I'm good thanks, do you like pie?"	
		};
	
	@Test
	public void testCorrectlyFiltersByWord() throws Exception {
		Conversation c = TestUtilities.getSampleConversation(testMessages);
		
		String keyword = "you";
		WordFilter f = new WordFilter(keyword);
		
		c = f.apply(c);	
		
		Message[] ms = new Message[c.getMessages().size()];
	    c.getMessages().toArray(ms);
		
		assertEquals(2, ms.length);
		assertEquals("how are you?", ms[0].getContent());
		assertEquals("I'm good thanks, do you like pie?", ms[1].getContent());
	}

}
