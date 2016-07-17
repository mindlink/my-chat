package com.mindlinksoft.recruitment.mychat.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;

import com.mindlinksoft.recruitment.mychat.bean.Conversation;
import com.mindlinksoft.recruitment.mychat.bean.Message;

public class ConversationTestBase {

	protected Conversation createConversation(String title, Message... messages) {
		return new Conversation(title, Arrays.asList(messages));
	}

	protected Message createMessage(int ts, String user, String content) {
		return new Message(Instant.ofEpochSecond(ts), user, content);
	}

	protected void assertConversationEquals(Conversation c1, Conversation c2) {
		Assert.assertEquals(c1.getName(), c2.getName());
		assertMessageListEqual(c1.getMessages(), c2.getMessages());
	}

	protected void assertMessageListEqual(Collection<Message> m1, Collection<Message> m2) {
		if (m1 == null) {
			Assert.assertNull(m2);
			return;
		}
		if (m2 == null)
			Assert.assertNull(m1);
		Assert.assertEquals(m1.size(), m2.size());
		List<Message> l1 = new ArrayList<>(m1);
		List<Message> l2 = new ArrayList<>(m2);
		for (int i = 0; i < m1.size(); i++)
			assertMessageEquals(l1.get(i), l2.get(i));
	}

	protected void assertMessageEquals(Message m1, Message m2) {
		Assert.assertEquals(m1.getTimestamp(), m2.getTimestamp());
		Assert.assertEquals(m1.getSenderId(), m2.getSenderId());
		Assert.assertEquals(m1.getContent(), m2.getContent());
	}

}
