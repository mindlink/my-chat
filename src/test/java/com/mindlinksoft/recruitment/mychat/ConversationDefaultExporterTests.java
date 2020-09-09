package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.Constructs.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Constructs.ConversationReport;
import com.mindlinksoft.recruitment.mychat.Constructs.Message;
import com.mindlinksoft.recruitment.mychat.Constructs.User;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConversationDefaultExporterTests {
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "", "", "", "", "");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        ConversationDefault c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), ConversationDefault.class);

        assertEquals("My Conversation", c.name);

        assertEquals(9, c.messages.size());

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

        assertEquals(ms[7].timestamp, Instant.ofEpochSecond(1448470916));
        assertEquals(ms[7].senderId, "bob");
        assertEquals(ms[7].content, "Here's my credit card number: 1234-1234-1234-1234");

        assertEquals(ms[8].timestamp, Instant.ofEpochSecond(1448470917));
        assertEquals(ms[8].senderId, "angus");
        assertEquals(ms[8].content, "Here's my mobile number 07441231495 and my credit card number is 1234 3256 6483 1234");
    }

    @Test
    public void testExportingFilteredByUser() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "-name", "bob", "", "", "");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        ConversationDefault c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), ConversationDefault.class);

        assertEquals("My Conversation", c.name);

        assertEquals(4, c.messages.size());

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

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470916));
        assertEquals(ms[3].senderId, "bob");
        assertEquals(ms[3].content, "Here's my credit card number: 1234-1234-1234-1234");
    }

    @Test
    public void testExportingFilteredByKeyword() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "-keyword", "pie", "", "", "");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        ConversationDefault c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), ConversationDefault.class);

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

    @Test
    public void testExportingHiddenBlackListedWords() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "-hide", "hello,there,pie", "", "", "");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        ConversationDefault c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), ConversationDefault.class);

        assertEquals("My Conversation", c.name);

        assertEquals(9, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "*redacted*redacted*");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "I'm good thanks, do you like*redacted*");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some*redacted*");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if*redacted*s anybody else in the*redacted*society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head*redacted*eater*redacted*");

    }

    @Test
    public void testExportingWithHiddenDetails() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "-details", "", "", "", "");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        ConversationDefault c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), ConversationDefault.class);

        assertEquals("My Conversation", c.name);

        assertEquals(9, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[7].timestamp, Instant.ofEpochSecond(1448470916));
        assertEquals(ms[7].senderId, "bob");
        assertEquals(ms[7].content, "Here's my credit card number: *redacted*");

        assertEquals(ms[8].timestamp, Instant.ofEpochSecond(1448470917));
        assertEquals(ms[8].senderId, "angus");
        assertEquals(ms[8].content, "Here's my mobile number *redacted* and my credit card number is *redacted*");
    }

    @Test
    public void testExportingWithObfuscatedIds() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "-obf", "", "", "", "");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new ConversationDefaultExporterTests.InstantDeserializer());

        Gson g = builder.create();

        ConversationDefault c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), ConversationDefault.class);

        assertEquals("My Conversation", c.name);

        assertEquals(9, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertTrue(!ms[0].senderId.contains("[a-zA-Z]+") && ms[0].senderId.length() == 5);
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertTrue(!ms[1].senderId.contains("[a-zA-Z]+") && ms[1].senderId.length() == 5);
        assertEquals(ms[1].content, "how are you?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertTrue(!ms[2].senderId.contains("[a-zA-Z]+") && ms[2].senderId.length() == 5);
        assertEquals(ms[2].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertTrue(!ms[3].senderId.contains("[a-zA-Z]+") && ms[3].senderId.length() == 5);
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertTrue(!ms[4].senderId.contains("[a-zA-Z]+") && ms[4].senderId.length() == 5);
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertTrue(!ms[5].senderId.contains("[a-zA-Z]+") && ms[5].senderId.length() == 5);
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertTrue(!ms[6].senderId.contains("[a-zA-Z]+") && ms[6].senderId.length() == 5);
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");

        assertEquals(ms[7].timestamp, Instant.ofEpochSecond(1448470916));
        assertTrue(!ms[7].senderId.contains("[a-zA-Z]+") && ms[7].senderId.length() == 5);
        assertEquals(ms[7].content, "Here's my credit card number: 1234-1234-1234-1234");

        assertEquals(ms[8].timestamp, Instant.ofEpochSecond(1448470917));
        assertTrue(!ms[8].senderId.contains("[a-zA-Z]+") && ms[8].senderId.length() == 5);
        assertEquals(ms[8].content, "Here's my mobile number 07441231495 and my credit card number is 1234 3256 6483 1234");
    }

    @Test
    public void testExportingWithReport() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "-report", "", "", "", "");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new ConversationDefaultExporterTests.InstantDeserializer());

        Gson g = builder.create();

        ConversationReport c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), ConversationReport.class);

        assertEquals("My Conversation", c.name);

        assertEquals(9, c.messages.size());

        assertEquals(3, c.users.size());

        User[] ms = new User[c.users.size()];
        c.users.toArray(ms);

        assertEquals(ms[0].messageCount, new Integer(4));
        assertEquals(ms[0].senderId, "bob");

        assertEquals(ms[1].messageCount, new Integer(3));
        assertEquals(ms[1].senderId, "angus");

        assertEquals(ms[2].messageCount, new Integer(2));
        assertEquals(ms[2].senderId, "mike");

    }

    @Test
    public void testExportingWithRedactedDetails() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "-details", "", "", "", "");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new ConversationDefaultExporterTests.InstantDeserializer());

        Gson g = builder.create();

        ConversationReport c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), ConversationReport.class);

        assertEquals("My Conversation", c.name);

        assertEquals(9, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[7].timestamp, Instant.ofEpochSecond(1448470916));
        assertEquals(ms[7].senderId, "bob");
        assertEquals(ms[7].content, "Here's my credit card number: *redacted*");

        assertEquals(ms[8].timestamp, Instant.ofEpochSecond(1448470917));
        assertEquals(ms[8].senderId, "angus");
        assertEquals(ms[8].content, "Here's my mobile number *redacted* and my credit card number is *redacted*");

    }

    @Test
    public void testExportingAllFlags() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "-report", "-obf", "-details", "", "");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new ConversationDefaultExporterTests.InstantDeserializer());

        Gson g = builder.create();

        ConversationReport c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), ConversationReport.class);

        assertEquals("My Conversation", c.name);

        assertEquals(9, c.messages.size());

        assertEquals(3, c.users.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[7].timestamp, Instant.ofEpochSecond(1448470916));
        assertTrue(!ms[7].senderId.contains("[a-zA-Z]+") && ms[7].senderId.length() == 5);
        assertEquals(ms[7].content, "Here's my credit card number: *redacted*");

        assertEquals(ms[8].timestamp, Instant.ofEpochSecond(1448470917));
        assertTrue(!ms[8].senderId.contains("[a-zA-Z]+") && ms[8].senderId.length() == 5);
        assertEquals(ms[8].content, "Here's my mobile number *redacted* and my credit card number is *redacted*");

        User[] us = new User[c.users.size()];
        c.users.toArray(us);

        assertEquals(us[0].messageCount, new Integer(4));
        assertTrue(!us[0].senderId.contains("[a-zA-Z]+") && us[0].senderId.length() == 5);

        assertEquals(us[1].messageCount, new Integer(3));
        assertTrue(!us[1].senderId.contains("[a-zA-Z]+") && us[1].senderId.length() == 5);

        assertEquals(us[2].messageCount, new Integer(2));
        assertTrue(!us[1].senderId.contains("[a-zA-Z]+") && us[1].senderId.length() == 5);
    }

    @Test
    public void testExportingFilteredByUserAllFlags() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "-name", "bob", "-report", "-details", "-obf");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new ConversationDefaultExporterTests.InstantDeserializer());

        Gson g = builder.create();

        ConversationReport c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), ConversationReport.class);

        assertEquals("My Conversation", c.name);

        assertEquals(4, c.messages.size());

        assertEquals(1, c.users.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertTrue(!ms[0].senderId.contains("[a-zA-Z]+") && ms[1].senderId.length() == 5);
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470906));
        assertTrue(!ms[1].senderId.contains("[a-zA-Z]+") && ms[1].senderId.length() == 5);
        assertEquals(ms[1].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertTrue(!ms[2].senderId.contains("[a-zA-Z]+") && ms[2].senderId.length() == 5);
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470916));
        assertTrue(!ms[3].senderId.contains("[a-zA-Z]+") && ms[3].senderId.length() == 5);
        assertEquals(ms[3].content, "Here's my credit card number: *redacted*");

        User[] us = new User[c.users.size()];
        c.users.toArray(us);

        assertEquals(us[0].messageCount, new Integer(4));
        assertTrue(!us[0].senderId.contains("[a-zA-Z]+") && us[0].senderId.length() == 5);
    }


    static class InstantDeserializer implements JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonPrimitive()) {
                throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
            }

            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

            if (!jsonPrimitive.isNumber()) {
                throw new JsonParseException("Expected instant represented as JSON number, but different primitive found.");
            }

            return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
        }
    }
}
