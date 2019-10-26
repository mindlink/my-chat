package com.mindlinksoft.recruitment.mychat.FilterTests;

import com.mindlinksoft.recruitment.mychat.*;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.time.Instant;
import org.junit.Test;

/**
 * Tests for {@link NumberFilter}.
 */
public class NumberFilterTests {
	/**
	 * Tests that the {@code filterMessages()} function will remove any large
	 * numbers (card and mobile).
	 * 
	 * @throws IOException Failed to read in or write file.
	 */
	@Test
	public void testHideMobileAndCardNumbers() throws IOException {
		ConversationExporter exporter = new ConversationExporter();

		String[] option = { "-hidenum", "" };
		String inputFilePath = "chat_numbers.txt";
		String outputFilePath = "chat_numbers.json";
		exporter.exportConversation(inputFilePath, outputFilePath, option);

		Conversation c = InstantDeserializer.createJsonDeserialized(outputFilePath);
		assertEquals("My Strange Encounter", c.name);

		assertEquals(5, c.messages.size());

		Message[] ms = new Message[c.messages.size()];
		c.messages.toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("Hey you!", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
        assertEquals("mike", ms[1].senderId);
        assertEquals("Who, me?", ms[1].content);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
        assertEquals("bob", ms[2].senderId);
        assertEquals("Quick! Take my phone number! *redacted*", ms[2].content);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
        assertEquals("mike", ms[3].senderId);
        assertEquals("Wait, what are you doing!?", ms[3].content);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
        assertEquals("bob", ms[4].senderId);
        assertEquals("Take my card number too! *redacted*", ms[4].content);

    }
}
