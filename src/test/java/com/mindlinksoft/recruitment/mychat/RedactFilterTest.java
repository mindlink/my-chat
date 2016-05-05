package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RedactFilterTest {
	
	private static final String[] messages = {
			"1448470901 bob Hello there!",
			"1448470905 mike how are you?",
			"1448470906 bob I'm good thanks, do you like pie?",
			"1448470909 bob lol my card is 4444 4444 4444 4444 by the way...",
			"1448470909 bob that number sucks... 4646595925251414 is cooler",
			"1448470912 mike ok call me on 02088745521 please",
			"1448470914 Guy I'm here too... My card is 4455-4455-4455-4455 and my mobile is 07777777777!"
	};
	
	private Conversation c;

	@Before
	public void beforeEach() {
		c = TestUtilities.getSampleConversation(messages);
	}
	
	@Test
	public void testCorrectlyRedactsByWord() throws Exception {
		
		String word = "you";
		RedactFilter f = new RedactFilter(word);
		
		c = f.apply(c);

		Message[] ms = new Message[c.getMessages().size()];
	    c.getMessages().toArray(ms);
	    
	    assertEquals("how are *redacted*?", ms[1].getContent());
	    assertEquals("I'm good thanks, do *redacted* like pie?", ms[2].getContent());
	}
	
	@Test
	public void testCorrectlyRedactsCreditCardNumbers() throws Exception {
		
		String regex = Const.CARD_REGEX;
		RedactFilter f = new RedactFilter(regex);
		
		c = f.apply(c);

		Message[] ms = new Message[c.getMessages().size()];
	    c.getMessages().toArray(ms);
	    
	    assertEquals("lol my card is *redacted* by the way...", ms[3].getContent());
	    assertEquals("that number sucks... *redacted* is cooler", ms[4].getContent());
	    assertEquals("I'm here too... My card is *redacted* and my mobile is 07777777777!", ms[6].getContent());
	}
	
	@Test
	public void testCorrectlyRedactsPhoneNumbers() throws Exception {

		String regex = Const.PHONE_REGEX;
		RedactFilter f = new RedactFilter(regex);
		
		c = f.apply(c);

		Message[] ms = new Message[c.getMessages().size()];
	    c.getMessages().toArray(ms);
	    
	    assertEquals("ok call me on *redacted* please", ms[5].getContent());
	    assertEquals("I'm here too... My card is 4455-4455-4455-4455 and my mobile is *redacted*!", ms[6].getContent());
	}
	
	@Test
	public void testCorrectlyObfuscatesSenderId() throws Exception {

		ObfuscateUserFilter f = new ObfuscateUserFilter();
		
		c = f.apply(c);

		Message[] ms = new Message[c.getMessages().size()];
	    c.getMessages().toArray(ms);
	    
	    assertEquals("User 1", ms[0].getSenderId());
	    assertEquals("User 2", ms[1].getSenderId());
	    assertEquals("User 1", ms[2].getSenderId());
	    assertEquals("User 1", ms[3].getSenderId());
	    assertEquals("User 1", ms[4].getSenderId());
	    assertEquals("User 2", ms[5].getSenderId());
	    assertEquals("User 3", ms[6].getSenderId());
	}

}
