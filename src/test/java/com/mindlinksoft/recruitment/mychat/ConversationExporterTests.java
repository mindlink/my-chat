package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() {
        ConversationExporter exporter = new ConversationExporter();

        Map<String, String> args = new HashMap<>();

        try {
            exporter.exportConversation("chat.txt", "chat.json", args);
        } catch (exportException e) {
            System.out.println("exporter encountered an error");
            e.printStackTrace();
            fail();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = null;
        try {
            c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
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
        } catch (FileNotFoundException e) {
            System.out.println("file chat.json not found");
            fail();
        }
    }

    @Test
    public void testFilerMessagesByUser() {

        Map<String, String> args = new HashMap<>();

        args.put("user", "bob");

        ConversationExporter exporter = new ConversationExporter();

        try {
            exporter.exportConversation("chat.txt", "bob's_chat.json", args);
        } catch (exportException e) {
            System.out.println("exporter encountered an error");
            e.printStackTrace();
            fail();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = null;
        try {
            c = g.fromJson(new InputStreamReader(new FileInputStream("bob's_chat.json")), Conversation.class);

            assertEquals("My Conversation", c.name);

            assertEquals(3, c.messages.size());

            Message[] ms = new Message[c.messages.size()];
            c.messages.toArray(ms);

            for (Message m : ms) {
                assertEquals("bob", m.senderId);
            }

            assertEquals("Hello there!", ms[0].content);
            assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);

            assertEquals(Instant.ofEpochSecond(1448470906), ms[1].timestamp);
            assertEquals("I'm good thanks, do you like pie?", ms[1].content);

            assertEquals(Instant.ofEpochSecond(1448470914), ms[2].timestamp);
            assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);

        } catch (FileNotFoundException e) {
            System.out.println("could not find bob's_chat.json");
            fail();
        }

    }

    @Test
    public void testReadInBlackList() {
        ConversationExporter exporter = new ConversationExporter();
        try {
            List<String> blacklisteditems = exporter.readInBlacklist("blacklisted.txt");
            assertEquals(3, blacklisteditems.size());
            assertEquals("pie", blacklisteditems.get(0));
            assertEquals("Angus", blacklisteditems.get(1));
            assertEquals("well", blacklisteditems.get(2));


        } catch (readException e) {
            System.out.println("error reading in blacklist file");
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSort() {
        Map<String, String> args = new HashMap<>();
        args.put("sorted", "true");

        ConversationExporter exporter = new ConversationExporter();

        try {
            exporter.exportConversation("chat.txt", "sorted_chat.json", args);
        } catch (exportException e) {
            System.out.println("exporter encountered an error");
            e.printStackTrace();
            fail();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = null;

        try {
            c = g.fromJson(new InputStreamReader(new FileInputStream("sorted_chat.json")), Conversation.class);

            assertEquals("My Conversation", c.name);
            assertEquals(7, c.messages.size());

            assertEquals("bob", c.mostactiveUsers.get(0).get(0));
            assertEquals("3", c.mostactiveUsers.get(0).get(1));

            assertEquals("angus", c.mostactiveUsers.get(1).get(0));
            assertEquals("2", c.mostactiveUsers.get(1).get(1));

            assertEquals("mike", c.mostactiveUsers.get(2).get(0));
            assertEquals("2", c.mostactiveUsers.get(2).get(1));

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

            assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470912));
            assertEquals(ms[3].senderId, "angus");
            assertEquals(ms[3].content, "Hell yes! Are we buying some pie?");

            assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470915));
            assertEquals(ms[4].senderId, "angus");
            assertEquals(ms[4].content, "YES! I'm the head pie eater there...");


            assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470905));
            assertEquals(ms[5].senderId, "mike");
            assertEquals(ms[5].content, "how are you?");

            assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470910));
            assertEquals(ms[6].senderId, "mike");
            assertEquals(ms[6].content, "no, let me ask Angus...");


        } catch (FileNotFoundException e) {
            System.out.println("could not find sorted_chat.json");
            fail();
        }
    }

    @Test
    public void testFilterMessagesByKeyword() {

        Map<String, String> args = new HashMap<>();

        args.put("keyword", "pie");

        ConversationExporter exporter = new ConversationExporter();

        try {
            exporter.exportConversation("chat.txt", "pie_chat.json", args);
        } catch (exportException e) {
            System.out.println("exporter encountered an error");
            e.printStackTrace();
            fail();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = null;
        try {
            c = g.fromJson(new InputStreamReader(new FileInputStream("pie_chat.json")), Conversation.class);

            assertEquals("My Conversation", c.name);
            assertEquals(4, c.messages.size());

        } catch (FileNotFoundException e) {
            System.out.println("could not find pie_chat.json");
            fail();
        }


    }

    @Test
    public void testHideWords() {

        Map<String, String> args = new HashMap<>();

        args.put("blacklist", "blacklisted.txt");

        ConversationExporter exporter = new ConversationExporter();

        try {
            exporter.exportConversation("chat.txt", "pie_angus_hidden.json", args);
        } catch (exportException e) {
            System.out.println("exporter encountered an error");
            e.printStackTrace();
            fail();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = null;
        try {
            c = g.fromJson(new InputStreamReader(new FileInputStream("pie_angus_hidden.json")), Conversation.class);

            assertEquals("My Conversation", c.name);

            assertEquals(7, c.messages.size());

            Message[] ms = new Message[c.messages.size()];
            c.messages.toArray(ms);

            assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
            assertEquals("bob", ms[0].senderId);
            assertEquals("Hello there!", ms[0].content);

            assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
            assertEquals("mike", ms[1].senderId);
            assertEquals("how are you?", ms[1].content);

            assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
            assertEquals("bob", ms[2].senderId);
            assertEquals("I'm good thanks, do you like *redacted*?", ms[2].content);

            assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
            assertEquals("mike", ms[3].senderId);
            assertEquals("no, let me ask *redacted*...", ms[3].content);

            assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
            assertEquals("angus", ms[4].senderId);
            assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].content);

            assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
            assertEquals("bob", ms[5].senderId);
            assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms[5].content);

            assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
            assertEquals("angus", ms[6].senderId);
            assertEquals("YES! I'm the head *redacted* eater there...", ms[6].content);

        } catch (FileNotFoundException e) {
            System.out.println("could not find pie_angus_hidden.json");
        }

    }

    @Test
    public void testbadInput() {
        Map<String, String> args = new HashMap<>();
        ConversationExporter exporter = new ConversationExporter();
        args.put("hidesensitiveinfo", "1272323421308");
        try {
            exporter.exportConversation("cardchat.txt", "carddetails_hidden.json", args);
        } catch (exportException e) {
            assertEquals("hidesensitiveinfo flag must be true or false",e.getMessage());
        }

        exporter = new ConversationExporter();
        args.remove("hidesensitiveinfo");
        args.put("blacklist", "somenonexistentfile");
        try {
            exporter.exportConversation("chat.txt", "chat.json", args);
        } catch (exportException e) {
            assertEquals("error when trying to read in blacklist",e.getMessage());
        }

        exporter = new ConversationExporter();
        args.remove("blacklist");
        args.put("keyword", "somenonexistentword");
        try {
            exporter.exportConversation("chat.txt", "chat.json", args);
        } catch (exportException e) {
            assertEquals("could not find any items by keyword:somenonexistentword",e.getMessage());
        }

        exporter = new ConversationExporter();
        args.remove("keyword");
        args.put("user", "somenonexistentuser");
        try {
            exporter.exportConversation("chat.txt", "chat.json", args);
        } catch (exportException e) {
            assertEquals("could not find any items by user:somenonexistentuser",e.getMessage());
        }

        exporter = new ConversationExporter();
        args.remove("user");
        args.put("sorted", "blahblahblah");
        try {
            exporter.exportConversation("chat.txt", "chat.json", args);
        } catch (exportException e) {
            assertEquals("sorted flag must be true or false",e.getMessage());
        }

        exporter = new ConversationExporter();
        args.remove("sorted");
        try {
            exporter.exportConversation("invalidfile.txt", "chat.json", args);
        } catch (exportException e) {
            assertEquals("encountered an issue when reading input file:invalidfile.txt",e.getMessage());
        }

        exporter = new ConversationExporter();
        try {
            exporter.exportConversation("invalidfile.txt", "chat.json", args);
        } catch (exportException e) {
            assertEquals("encountered an issue when reading input file:invalidfile.txt",e.getMessage());
        }

    }

    @Test
    public void testhidesensitive() {
        Map<String, String> args = new HashMap<>();
        args.put("hidesensitiveinfo", "true");

        ConversationExporter exporter = new ConversationExporter();
        try {
            exporter.exportConversation("cardchat.txt", "carddetails_hidden.json", args);
        } catch (exportException e) {
            System.out.println("exporter encountered an error");
            e.printStackTrace();
            fail();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        try {
            Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("carddetails_hidden.json")), Conversation.class);
            assertEquals(2, c.messages.size());
            assertEquals("Dangerous Conversation", c.name);
            Message[] ms = new Message[c.messages.size()];
            c.messages.toArray(ms);
            assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
            assertEquals("bob", ms[0].senderId);
            assertEquals("Hello there! My credit card number is *redacted*", ms[0].content);
            assertEquals(Instant.ofEpochSecond(1448470902), ms[1].timestamp);
            assertEquals("mike", ms[1].senderId);
            assertEquals("how interesting! my telephone number is *redacted*", ms[1].content);

        } catch (FileNotFoundException e) {
            System.out.println("could not find carddetails_hidden.json");
            fail();

        }


    }

    class InstantDeserializer implements JsonDeserializer<Instant> {

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
