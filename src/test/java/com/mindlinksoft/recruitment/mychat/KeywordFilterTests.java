package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Test;

/**
 * Tests for {@link KeywordFilter}.
 */
public class KeywordFilterTests {
	 /**
     * Tests that the function correctly filters messages by keyword
     * @throws Exception When something bad happens.
     */
    @Test
    public void testFilterMessages() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] option = {"key","pie"};
        String inputFilePath = "chat.txt";
        String outputFilePath = "chat_pie.json";
        exporter.exportConversation(inputFilePath, outputFilePath, option);

        Conversation c = InstantDeserializer.createJsonDeserialized(outputFilePath);
        assertEquals("My Conversation", c.name);

        assertEquals(4, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        
        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[1].senderId, "angus");
        assertEquals(ms[1].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[3].senderId, "angus");
        assertEquals(ms[3].content, "YES! I'm the head pie eater there...");

    }
}
