package com.mindlinksoft.recruitment.mychat.message;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Tests for the {@link UserAliasMessageFormatter}.
 */
public class UserAliasMessgeFormatterTest {

	private static final String USER_CRUYFF = "Cruyff";
	private static final String USER_RAUFFMANN = "Rauffmann";
	private static IMessageFormatter userAliasFormatter;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		Map<String, String> aliasesMap = new HashMap<String, String>();
		aliasesMap.put(USER_CRUYFF, USER_RAUFFMANN);
		userAliasFormatter = new UserAliasMessageFormatter(aliasesMap);
	}
	
	@Test
	public void shouldReplaceUserId() {
		IMessage msg = new Message(Instant.now(), USER_RAUFFMANN, "i am the best of time");
		userAliasFormatter.format(msg);
		assertEquals(USER_RAUFFMANN, msg.getSenderId());
	}

	@Test
	public void shouldNotReplaceUserId() {
		IMessage msg = new Message(Instant.now(), USER_RAUFFMANN, "i am the best of time");
		userAliasFormatter.format(msg);
		assertEquals(USER_RAUFFMANN,msg.getSenderId());
	}
}
