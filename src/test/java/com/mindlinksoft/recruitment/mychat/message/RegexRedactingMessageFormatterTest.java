package com.mindlinksoft.recruitment.mychat.message;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the {@link RegexRedactingMessageFormatter}.
 */
public class RegexRedactingMessageFormatterTest {

	private static IMessageFormatter regexRedactingFormatter;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		Collection<String> regexList = new ArrayList<String>();
		regexList.add("abc");
		regexRedactingFormatter = new RegexRedactingMessageFormatter(regexList);
	}

	@Test
	public void shouldRedact() {
		IMessage msg = new Message(Instant.now(), "alex", "abc");
		regexRedactingFormatter.format(msg);
		assertEquals("*redacted*",msg.getContent());
	}
	
	@Test
	public void shouldNotRedact() {
		IMessage msg = new Message(Instant.now(), "alex", "123 abd");
		regexRedactingFormatter.format(msg);
		assertEquals("123 abd",msg.getContent());
	}
}
