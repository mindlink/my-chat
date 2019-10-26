package com.mindlinksoft.recruitment.mychat.FilterTests;

import com.mindlinksoft.recruitment.mychat.*;
import com.mindlinksoft.recruitment.mychat.Filters.UserFilter;

import org.junit.Test;
import java.io.IOException;
import java.time.Instant;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link UserFilter}.
 */
public class UserFilterTests {
	/**
	 * Tests that the function correctly filters messages by senderId.
	 * 
	 * @throws IOException Failed to read in or write file.
	 */
	@Test
	public void testFilterMessagesBySenderId() throws IOException {
		ConversationExporter exporter = new ConversationExporter();

		String[] option = { "-user", "bob" };
		String inputFilePath = "chat.txt";
		String outputFilePath = "chat_bob.json";
		exporter.exportConversation(inputFilePath, outputFilePath, option);

		Conversation c = InstantDeserializer.createJsonDeserialized(outputFilePath);
		assertEquals("My Conversation", c.name);

		assertEquals(3, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");

	}

	/**
	 * Tests that the {@code getSenderId()} function returns the correct value.
	 */
	@Test
	public void testGetSenderId() {
		String[] options = { "-user", "bob" };
		UserFilter kf = new UserFilter(options);

		assertEquals("bob", kf.getSenderId());
	}

	/**
	 * Tests that the {@code getOption()} function inherited from {@link Filter}
	 * returns the correct value.
	 */
	@Test
	public void testGetOption() {
		String[] options = { "-user", "bob" };
		UserFilter kf = new UserFilter(options);

		assertEquals("-user", kf.getOption());
	}
}
