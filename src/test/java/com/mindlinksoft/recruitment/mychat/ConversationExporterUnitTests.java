package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterUnitTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testReadCovnversation() throws FileNotFoundException, IOException {
        ConversationExporter exporter = new ConversationExporter();
        Conversation conversation = exporter.readConversation("chat.txt");

        Collection<Message> messages = conversation.messages;
        Message[] messageArray = new Message[messages.size()];
        messages.toArray(messageArray);

        assertEquals(7, messageArray.length);

        assertEquals(conversation.name, "My Conversation");

        assertEquals(Instant.ofEpochSecond(1448470901), messageArray[0].timestamp);
        assertEquals("bob", messageArray[0].senderId);
        assertEquals("Hello there!", messageArray[0].content);

        assertEquals(Instant.ofEpochSecond(1448470905), messageArray[1].timestamp);
        assertEquals("mike", messageArray[1].senderId);
        assertEquals("how are you?", messageArray[1].content);

        assertEquals(Instant.ofEpochSecond(1448470906), messageArray[2].timestamp);
        assertEquals("bob", messageArray[2].senderId);
        assertEquals("I'm good thanks, do you like pie?", messageArray[2].content);

        assertEquals(Instant.ofEpochSecond(1448470910), messageArray[3].timestamp);
        assertEquals("mike", messageArray[3].senderId);
        assertEquals("no, let me ask Angus...", messageArray[3].content);

        assertEquals(Instant.ofEpochSecond(1448470912), messageArray[4].timestamp);
        assertEquals("angus", messageArray[4].senderId);
        assertEquals("Hell yes! Are we buying some pie?", messageArray[4].content);

        assertEquals(Instant.ofEpochSecond(1448470914), messageArray[5].timestamp);
        assertEquals("bob", messageArray[5].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", messageArray[5].content);

        assertEquals(Instant.ofEpochSecond(1448470915), messageArray[6].timestamp);
        assertEquals("angus", messageArray[6].senderId);
        assertEquals("YES! I'm the head pie eater there...", messageArray[6].content);
    }
}
