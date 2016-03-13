package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

public class ReportTest {

Conversation conversation;
	Collection<Message> messages;
	
	/**
	 * Initialise the collection before tests
	 */
	@Before
	public void setUp() {
		messages = new ArrayList<Message>();
		messages.add(new Message(Instant.EPOCH, "bob", "Hello!"));
		messages.add(new Message(Instant.EPOCH, "john", "Hello!"));
		messages.add(new Message(Instant.EPOCH, "bob", "How are you?"));
		messages.add(new Message(Instant.EPOCH, "marcel", "Here is my credit card: 2124.3133.4323.2453."));
		messages.add(new Message(Instant.EPOCH, "maria", "My credit card: 2124-3133-4323-2453 details."));
		messages.add(new Message(Instant.EPOCH, "leo", "2124 3133 4323 2453 my credit card details."));
		messages.add(new Message(Instant.EPOCH, "edward", "My name is edward."));
		messages.add(new Message(Instant.EPOCH, "edward", "My name is edward."));
	}
	
	@Test
	public void testIncludeReport() throws Exception {
		List<TreeMap<String, Integer>> report = Report.getByUsername(messages);
		assertEquals(report.size(), 1);
		
		HashMap<String, Integer> reportValues = new HashMap<>(report.get(0));
		
		assertEquals(reportValues.size(), 6);
		assertTrue(reportValues.containsKey("bob"));
		assertTrue(reportValues.containsKey("edward"));
		assertEquals(reportValues.get("bob").intValue(), 2);
		assertEquals(reportValues.get("leo").intValue(), 1);
	}
}
