package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for the {@link MessageFilter}.
 */
public class MessageFilterTests {	
	/**
     * Tests that keywords are correctly redacted, taking varying cases into account.
     */
	@Test
	public void testRedactBlacklistedKeywordsWithDifferingCases() {
		String msg = "NotRedacted redacted, StillNotRedacted StillRedacted.";
		Set<String> blacklistedWords = new HashSet<String>();
		blacklistedWords.add("Redacted");
		blacklistedWords.add("Stillredacted");
		String filtered = MessageFilter.redactBlacklistedKeywords(msg, blacklistedWords);
		
		assertTrue(filtered.contentEquals("NotRedacted *redacted*, StillNotRedacted *redacted*."));
	}
	
	/**
     * Tests that a phone number is correctly redacted.
     */
	@Test
	public void testRedactValidPhoneNumber() {
		String test = "my +447951245659number is +447951245659";
		String filtered = MessageFilter.redactPhoneNumbers(test);

		assertTrue(filtered.contentEquals("my *redacted*number is *redacted*"));
	}
	
	/**
     * Tests that a credit card number is correctly redacted.
     */
	@Test
	public void testRedactValiCCNumber() {
		String test = "my 1234-4321-1234-4213number is 4524-2145-2321-2423";
		String filtered = MessageFilter.redactCreditCards((test));

		assertTrue(filtered.contentEquals("my *redacted*number is *redacted*"));
	}
	
	/**
     * Tests that a username is correctly encrypted using ROT13.
     */
	@Test
	public void testObfuscateUsername() {
		String test = "bobby";
		String obfuscated = MessageFilter.obfuscateUsername(test);
		
		assertTrue(obfuscated.contentEquals("obool"));
	}

}
