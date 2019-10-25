package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.Instant;

import org.junit.Test;

public class BlacklistFilterTests {
	/**
     * Tests that the words from the black list will be replaced with *redacted*
     * @throws IOException Failed to read in or write file.
     */
    @Test
    public void testHideMessageContentUsingBlacklist() throws IOException {
        ConversationExporter exporter = new ConversationExporter();

        String[] option = {"hidewords", "pie", "how", "are", "you"};
        String inputFilePath = "chat.txt";
        String outputFilePath = "chat_hide.json";
        exporter.exportConversation(inputFilePath, outputFilePath, option);

        Conversation c = InstantDeserializer.createJsonDeserialized(outputFilePath);
        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("Hello there!", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
        assertEquals("mike", ms[1].senderId);
        assertEquals("*redacted* *redacted* *redacted*?", ms[1].content);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
        assertEquals("bob", ms[2].senderId);
        assertEquals("I'm good thanks, do *redacted* like *redacted*?", ms[2].content);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
        assertEquals("mike", ms[3].senderId);
        assertEquals("no, let me ask Angus...", ms[3].content);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
        assertEquals("angus", ms[4].senderId);
        assertEquals("Hell yes! *redacted* we buying some *redacted*?", ms[4].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
        assertEquals("bob", ms[5].senderId);
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms[5].content);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
        assertEquals("angus", ms[6].senderId);
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].content);
    }
}
