package com.mindlinksoft.recruitment.mychat.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.model.MessageSenderIdObfuscator;

public class MessageSenderIdObfuscatorTest extends ConversationTestBase {

	private MessageSenderIdObfuscator o_;

	@Before
	public void init() {
		o_ = new MessageSenderIdObfuscator();
	}

	@Test
	public void testGetNextId() {
		Assert.assertEquals("A", o_.getNextId(0));
		Assert.assertEquals("B", o_.getNextId(1));
		Assert.assertEquals("Z", o_.getNextId(25));
		Assert.assertEquals("BA", o_.getNextId(26));
		Assert.assertEquals("DW", o_.getNextId(100));
		Assert.assertEquals("BMM", o_.getNextId(1000));
		Assert.assertEquals("OUQ", o_.getNextId(10000));
	}

	@Test
	public void testObfuscate() {
		Assert.assertEquals("A", o_.obfuscate(createMessage(0, "bob", "hi")).getSenderId());
		Assert.assertEquals("B", o_.obfuscate(createMessage(0, "sally", "hi")).getSenderId());
		Assert.assertEquals("C", o_.obfuscate(createMessage(0, "john", "hi")).getSenderId());
		Assert.assertEquals("C", o_.obfuscate(createMessage(0, "john", "hi")).getSenderId());
		Assert.assertEquals("D", o_.obfuscate(createMessage(0, "jake", "hi")).getSenderId());
		Assert.assertEquals("E", o_.obfuscate(createMessage(0, "BOB", "hi")).getSenderId());
		Assert.assertEquals("A", o_.obfuscate(createMessage(0, "bob", "hi")).getSenderId());
		Assert.assertEquals("B", o_.obfuscate(createMessage(0, "sally", "hi")).getSenderId());
	}

}
