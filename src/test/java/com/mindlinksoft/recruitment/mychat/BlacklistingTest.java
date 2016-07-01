package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tests for the {@link Blacklisting}.
 */

public class BlacklistingTest {
	
	/**
	 * tests that the correct words are hidden from the conversation
	 *
	 */
	
	@Test
	public void hidingTest(){
		Blacklisting bl = new Blacklisting();
		Conversation c = convoCopy.copy();
		
		List<String> hideWords = new ArrayList<String>();
		hideWords.add("Hello");
		hideWords.add("like");
		hideWords.add("pie");
    	
    	
		Conversation hidden = bl.hideWord(c, hideWords);

		Message[] redactedmsg = new Message[hidden.getMsg().size()];
		hidden.getMsg().toArray(redactedmsg);
		
		
		assertEquals(hidden.getMsg().size(), 7);

		assertEquals(redactedmsg[0].getContent(), "*redacted* there!");
		assertEquals(redactedmsg[2].getContent(), "I'm good thanks, do you *redacted* *redacted*?");
		assertEquals(redactedmsg[4].getContent(), "Hell yes! Are we buying some *redacted*?");
		assertEquals(redactedmsg[5].getContent(), "No, just want to know if there's anybody else in the *redacted* society...");
		assertEquals(redactedmsg[6].getContent(), "YES! I'm the head *redacted* eater there...");


		System.out.println("Filtered by user ID: " + hidden);
	}
}
