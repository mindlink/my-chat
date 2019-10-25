package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.Instant;

import org.junit.Test;

public class ObfuscateIDFilterTests {
	 /**
     * Tests that the function correctly substitutes senderIds with more anonymous names
     * @throws IOException Failed to read in or write file.
     */
    @Test
    public void testSenderIdsAreCorrectlySubstituted() throws IOException {
        ConversationExporter exporter = new ConversationExporter();

        String[] option = {"obf", ""};
        String inputFilePath = "chat.txt";
        String outputFilePath = "chat_obf.json";
        exporter.exportConversation(inputFilePath, outputFilePath, option);

        Conversation c = InstantDeserializer.createJsonDeserialized(outputFilePath);
        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("User0", ms[0].senderId);
        assertEquals("Hello there!", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
        assertEquals("User1", ms[1].senderId);
        assertEquals("how are you?", ms[1].content);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
        assertEquals("User0", ms[2].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[2].content);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
        assertEquals("User1", ms[3].senderId);
        assertEquals("no, let me ask Angus...", ms[3].content);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
        assertEquals("User2", ms[4].senderId);
        assertEquals("Hell yes! Are we buying some pie?", ms[4].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
        assertEquals("User0", ms[5].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
        assertEquals("User2", ms[6].senderId);
        assertEquals("YES! I'm the head pie eater there...", ms[6].content);

    }
}
