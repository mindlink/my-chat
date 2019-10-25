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
    public void testFilterMessagesByKeyword() throws Exception {
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

        
        assertEquals(Instant.ofEpochSecond(1448470906), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[1].timestamp);
        assertEquals("angus", ms[1].senderId);
        assertEquals("Hell yes! Are we buying some pie?", ms[1].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[2].timestamp);
        assertEquals("bob", ms[2].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[3].timestamp);
        assertEquals("angus", ms[3].senderId);
        assertEquals("YES! I'm the head pie eater there...", ms[3].content);

    }
}
