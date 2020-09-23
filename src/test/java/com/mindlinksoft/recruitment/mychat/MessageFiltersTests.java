package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MessageFiltersTests {

	/**
	 * Tests that the filtering of messages by user works.
	 */
	@Test
	public void testFilteringMessageByUser() throws Exception {
		String userId = "bob";
		ConversationExporterConfiguration conversationExporterConfiguration = new ConversationExporterConfiguration(
				"chat.txt", "chat.json");
		conversationExporterConfiguration.setUserFilter(userId);
		List<Message> messages = new ArrayList<>();

		Message tmpMessage = new Message(Instant.ofEpochSecond(1448470901), userId, "Hello there!");
		messages.add(tmpMessage);
		tmpMessage = new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?");
		messages.add(tmpMessage);
		tmpMessage = new Message(Instant.ofEpochSecond(1448470906), userId, "I'm good thanks, do you like pie?");
		messages.add(tmpMessage);
		tmpMessage = new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus...");
		messages.add(tmpMessage);

		MessageFilters messageFilter = new MessageFilters();
		List<Message> filteredMessages = messageFilter.filterMessages(messages, conversationExporterConfiguration);
		for (int i = 0; i < filteredMessages.size(); i++) {
			assertEquals(filteredMessages.get(i).senderId, userId);
		}
	}

	/**
	 * Tests that the filtering of messages by a keyword works.
	 */
	@Test
	public void testFilteringMessageByKeyword() throws Exception {
		String keyword = "you";
		ConversationExporterConfiguration conversationExporterConfiguration = new ConversationExporterConfiguration(
				"chat.txt", "chat.json");
		conversationExporterConfiguration.setKeywordFilter(keyword);
		List<Message> messages = new ArrayList<>();

		Message message1 = new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!");
		messages.add(message1);
		Message message2 = new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?");
		messages.add(message2);
		Message message3 = new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?");
		messages.add(message3);
		Message message4 = new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus...");
		messages.add(message4);

		MessageFilters messageFilter = new MessageFilters();
		List<Message> filteredMessages = messageFilter.filterMessages(messages, conversationExporterConfiguration);
		assertEquals(filteredMessages.get(0), message2);
		assertEquals(filteredMessages.get(1), message3);
	}

	/**
	 * Tests that messages can be filtered to redact a certain word.
	 */
	@Test
	public void testFilteringMessageByBlacklist() throws Exception {
		String blacklist = "I'm";
		ConversationExporterConfiguration conversationExporterConfiguration = new ConversationExporterConfiguration(
				"chat.txt", "chat.json");
		conversationExporterConfiguration.setBlacklist(blacklist);
		List<Message> messages = new ArrayList<>();

		Message message1 = new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!");
		messages.add(message1);
		Message message2 = new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?");
		messages.add(message2);
		Message message3 = new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?");
		messages.add(message3);
		Message message4 = new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus...");
		messages.add(message4);
		Message message5 = new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?");
		messages.add(message5);
		Message message6 = new Message(Instant.ofEpochSecond(1448470914), "bob",
				"No, just want to know if there's anybody else in the pie society...");
		messages.add(message6);
		Message message7 = new Message(Instant.ofEpochSecond(1448470915), "angus",
				"YES! I'm the head pie eater there...");
		messages.add(message7);

		MessageFilters messageFilter = new MessageFilters();
		List<Message> filteredMessages = messageFilter.filterMessages(messages, conversationExporterConfiguration);
		assertEquals(filteredMessages.get(0), message1);
		assertEquals(filteredMessages.get(1), message2);
		assertEquals(filteredMessages.get(2).content, "*redacted* good thanks, do you like pie?");
		assertEquals(filteredMessages.get(3), message4);
		assertEquals(filteredMessages.get(4), message5);
		assertEquals(filteredMessages.get(5), message6);
		assertEquals(filteredMessages.get(6).content, "YES! *redacted* the head pie eater there...");
	}

	/**
	 * Tests that credit card numbers and UK mobile numbers can be identified and
	 * redacted.
	 */
	@Test
	public void testHidePersonalDetails() throws Exception {
		ConversationExporterConfiguration conversationExporterConfiguration = new ConversationExporterConfiguration(
				"chat.txt", "chat.json");
		conversationExporterConfiguration.setHidePersonalDeatils(true);
		List<Message> messages = new ArrayList<>();

		Message message1 = new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!");
		messages.add(message1);
		Message message2 = new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?");
		messages.add(message2);
		Message message3 = new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?");
		messages.add(message3);
		Message message4 = new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus...");
		messages.add(message4);
		Message message5 = new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?");
		messages.add(message5);
		Message message6 = new Message(Instant.ofEpochSecond(1448470914), "bob",
				"Yes, what's your phone number so I can call you when I pick them up?");
		messages.add(message6);
		Message message7 = new Message(Instant.ofEpochSecond(1448470915), "angus",
				"YES! My number's 07789332556 and I can pay for them using my credit card, it's 4556045185406720");
		messages.add(message7);

		MessageFilters messageFilter = new MessageFilters();
		List<Message> filteredMessages = messageFilter.filterMessages(messages, conversationExporterConfiguration);
		assertEquals(filteredMessages.get(0), message1);
		assertEquals(filteredMessages.get(1), message2);
		assertEquals(filteredMessages.get(2), message3);
		assertEquals(filteredMessages.get(3), message4);
		assertEquals(filteredMessages.get(4), message5);
		assertEquals(filteredMessages.get(5), message6);
		assertEquals(filteredMessages.get(6).content,
				"YES! My number's *redacted* and I can pay for them using my credit card, it's *redacted*");
	}

	/**
	 * Tests that obfuscating user Id's can be applied to every user in a
	 * conversation .
	 */
	@Test
	public void testObfuscatingUsers() throws Exception {
		ConversationExporterConfiguration conversationExporterConfiguration = new ConversationExporterConfiguration(
				"chat.txt", "chat.json");
		conversationExporterConfiguration.setObfuscateUsers(true);
		List<Message> messages = new ArrayList<>();

		Message tmpMessage = new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!");
		messages.add(tmpMessage);
		tmpMessage = new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?");
		messages.add(tmpMessage);
		tmpMessage = new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?");
		messages.add(tmpMessage);
		tmpMessage = new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus...");
		messages.add(tmpMessage);
		tmpMessage = new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?");
		messages.add(tmpMessage);
		tmpMessage = new Message(Instant.ofEpochSecond(1448470914), "bob",
				"No, just want to know if there's anybody else in the pie society...");
		messages.add(tmpMessage);
		tmpMessage = new Message(Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head pie eater there...");
		messages.add(tmpMessage);

		MessageFilters messageFilter = new MessageFilters();
		List<Message> filteredMessages = messageFilter.filterMessages(messages, conversationExporterConfiguration);
		assertEquals(filteredMessages.get(0).senderId, "Ym9i");
		assertEquals(filteredMessages.get(1).senderId, "bWlrZQ==");
		assertEquals(filteredMessages.get(2).senderId, "Ym9i");
		assertEquals(filteredMessages.get(3).senderId, "bWlrZQ==");
		assertEquals(filteredMessages.get(4).senderId, "YW5ndXM=");
		assertEquals(filteredMessages.get(5).senderId, "Ym9i");
		assertEquals(filteredMessages.get(6).senderId, "YW5ndXM=");
	}

}
