package com.mindlinksoft.recruitment.mychat.conversation.exporter;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.conversation.ConversationFormatter;
import com.mindlinksoft.recruitment.mychat.conversation.exporter.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.conversation.exporter.ConversationImporter;
import com.mindlinksoft.recruitment.mychat.conversation.serialization.ConversationDeserializer;
import com.mindlinksoft.recruitment.mychat.conversation.serialization.InstantDeserializer;
import com.mindlinksoft.recruitment.mychat.conversation.serialization.JSONSerializer;
import com.mindlinksoft.recruitment.mychat.message.Message;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter(new JSONSerializer());
        ConversationImporter importer = new ConversationImporter();
        
        exporter.exportConversation(importer.importConversation("chat.txt"), "chat.json", new ConversationFormatter());

        GsonBuilder builder = new GsonBuilder();
        
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        builder.registerTypeAdapter(Conversation.class, new ConversationDeserializer());
        
        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.getName());

        assertEquals(13, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].getSenderId(), "mike");
        assertEquals(ms[1].getContent(), "how are you?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].getSenderId(), "bob");
        assertEquals(ms[2].getContent(), "I'm good thanks, do you like pie?");

        assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].getSenderId(), "mike");
        assertEquals(ms[3].getContent(), "no, let me ask Angus...");

        assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].getSenderId(), "angus");
        assertEquals(ms[4].getContent(), "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].getSenderId(), "bob");
        assertEquals(ms[5].getContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].getSenderId(), "angus");
        assertEquals(ms[6].getContent(), "YES! I'm the head pie eater there...");
    }

}
