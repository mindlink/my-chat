package com.mindlinksoft.recruitment.mychat.exporter.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.InvalidPathException;
import java.time.Instant;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;

import org.junit.Before;
import org.junit.Test;

public class ConversationReaderTests {
    
    ConversationReader reader;

    List<Message> exampleMessages;

    @Before
    public void setUp() {
        reader = new ConversationReader();
        reader.setInputFilePath("chat.txt");
        reader.setConversation(new Conversation());
    }

    @Test
    public void read() {
        // reader should be able to find the file if it exists
        Conversation conv;
        try {
            conv = reader.read();

            // resultant conversation should match file title
            assertEquals("My Conversation", conv.getName());
            
            // should match list of messages
            assertEquals(conv.getMessage(0).getTimestamp(), Instant.ofEpochSecond(1448470901));
            assertEquals(conv.getMessage(0).getSender().getSenderText(), "bob");
            assertEquals(conv.getMessage(0).getContent(), "Hello there!");

            assertEquals(conv.getMessage(1).getTimestamp(), Instant.ofEpochSecond(1448470905));
            assertEquals(conv.getMessage(1).getSender().getSenderText(), "mike");
            assertEquals(conv.getMessage(1).getContent(), "how are you?");

            assertEquals(conv.getMessage(2).getTimestamp(), Instant.ofEpochSecond(1448470906));
            assertEquals(conv.getMessage(2).getSender().getSenderText(), "bob");
            assertEquals(conv.getMessage(3).getContent(), "I'm good thanks, do you like pie?");

            assertEquals(conv.getMessage(3).getTimestamp(), Instant.ofEpochSecond(1448470910));
            assertEquals(conv.getMessage(3).getSender().getSenderText(), "mike");
            assertEquals(conv.getMessage(3).getContent(), "no, let me ask Angus...");

            assertEquals(conv.getMessage(4).getTimestamp(), Instant.ofEpochSecond(1448470912));
            assertEquals(conv.getMessage(4).getSender().getSenderText(), "angus");
            assertEquals(conv.getMessage(4).getContent(), "Hell yes! Are we buying some pie?");

            assertEquals(conv.getMessage(5).getTimestamp(), Instant.ofEpochSecond(1448470914));
            assertEquals(conv.getMessage(5).getSender().getSenderText(), "bob");
            assertEquals(conv.getMessage(5).getContent(), "No, just want to know if there's anybody else in the pie society...");

            assertEquals(conv.getMessage(6).getTimestamp(), Instant.ofEpochSecond(1448470915));
            assertEquals(conv.getMessage(6).getSender().getSenderText(), "angus");
            assertEquals(conv.getMessage(6).getContent(), "YES! I'm the head pie eater there...");

            // number of messages should by 7
            assertEquals(7, conv.getMessages().size());

            // should match senderMap
            assertTrue(conv.hasSender("bob"));
            assertTrue(conv.hasSender("mike"));
            assertTrue(conv.hasSender("angus"));
            
            // senderMap size should be 3, no. of active users
            assertEquals(3, conv.getMessages());
        } catch (InvalidPathException e) {
            // if file path is invalid, throw InvalidPathException
            e.printStackTrace();
        } // FIXME: add more exception checks
    }
}