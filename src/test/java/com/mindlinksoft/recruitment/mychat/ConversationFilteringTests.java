package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class ConversationFilteringTests {

    @Test
    public void testConversationFiltersByUser() throws Exception{

        String[] arguments ={"chat.txt","chat.json","user=bob"};
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration(arguments);
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation(configuration);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

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

    @Test
    public void testConversationFiltersByNonexistentUserIsEmpty() throws Exception{
        String[] arguments ={"chat.txt","chat.json","user=frank"};
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration(arguments);
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation(configuration);
        
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(0, c.messages.size());
    }

}
