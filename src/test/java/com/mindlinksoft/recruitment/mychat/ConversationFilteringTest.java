package com.mindlinksoft.recruitment.mychat;
import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tests for the {@link Filtering}.
 */

public class ConversationFilteringTest {
	
	/**
	 * tests that the filtering is applied correctly
	 *
	 */
	
	@Test
	public void userTest(){
		// only show the messages from Angus
		
		Filtering fl = new Filtering();
		Conversation c = convoCopy.copy();
		Conversation filtered = fl.byUser(c, "angus");

		Message[] fil = new Message[filtered.getMsg().size()];
		filtered.getMsg().toArray(fil);
		assertEquals(filtered.getMsg().size(), 2);

		assertEquals(fil[0].getSenderID(), "angus");
		assertEquals(fil[1].getSenderID(), "angus");

		System.out.println("Filtered by user ID: " + filtered);
	}
	
	
	@Test
	public void keywordTest(){
		
		// only show the messages that have the word "are"
		
		Filtering fl = new Filtering();
		Conversation c = convoCopy.copy();
		Conversation filtered = fl.byKeyword(c, "are");

		Message[] fil = new Message[filtered.getMsg().size()];
		filtered.getMsg().toArray(fil);
		assertEquals(filtered.getMsg().size(), 2);

		assertEquals(fil[0].getContent(), "how are you?");
		assertEquals(fil[1].getContent(), "Hell yes! Are we buying some pie?");

		System.out.println("Filtered by keyword: " + filtered);
	}

	
}
