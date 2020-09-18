package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Features;
import com.mindlinksoft.recruitment.mychat.utils.GsonConverter;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;
import org.junit.Assert;
import org.junit.Test;

import javax.security.auth.login.Configuration;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Tests for the Conversation
 */
public class ConversationExportTests {

    private final String inputFilePath = "chat.txt";
    private final String outputFilePath = "chat.json";

    public Conversation initConversation(String args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation(new CommandLineArgumentParser().parseCommandLineArguments(new String[]{inputFilePath, outputFilePath,args}));
        Gson g = GsonConverter.buildGsonDeserializer();
        return g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
    }

    /**
     * Tests that exporting a conversation will export the conversation correctly.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {

        Conversation c = initConversation("");

        assertEquals("My Conversation", c.name);
        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        assertEquals(ms[1].content, "how are you?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
    }



}
