package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

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
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "", "", "", false, false, false);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

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

    /**
     * Test for flag -u for filtering by user
     * 
     * @throws Exception
     */
    @Test
    public void testExportingConversationExportsConversationU() throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation("chats/chatu.txt", "chats/chatu.json", "bob", "", "", false, false, false);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chats/chatu.json")), Conversation.class);

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

    /**
     * Test for flag -w for filtering by keyword
     * 
     * @throws Exception
     */
    @Test
    public void testExportingConversationExportsConversationW() throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation("chats/chatw.txt", "chats/chatw.json", "", "Hello", "", false, false, false);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chats/chatw.json")), Conversation.class);

        assertEquals("My Conversation", c.name);
        assertEquals(1, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

    }

    /**
     * Test for flag -b for redacting blacklisted words
     * 
     * @throws Exception
     */
    @Test
    public void testExportingConversationExportsConversationB() throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation("chats/chatb.txt", "chats/chatb.json", "", "", "Hello,there", false, false, false);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chats/chatb.json")), Conversation.class);

        assertEquals("My Conversation", c.name);
        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "*redacted* *redacted*!");

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
        assertEquals(ms[5].content, "No, just want to know if *redacted*'s anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater *redacted*...");
    }

    /**
     * Test for flag -c for redacting credit cards and phone numbers
     * 
     * @throws Exception
     */
    @Test
    public void testExportingConversationExportsConversationC() throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation("chats/chatc.txt", "chats/chatc.json", "", "", "", true, false, false);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chats/chatc.json")), Conversation.class);

        assertEquals("My Conversation", c.name);
        assertEquals(2, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there my card number is *redacted* !");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        assertEquals(ms[1].content, "My phone number is *redacted* !");
    }

    /**
     * Test for flag -o for obfuscating user ids
     * 
     * @throws Exception
     */
    @Test
    public void testExportingConversationExportsConversationO() throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation("chats/chato.txt", "chats/chato.json", "", "", "", false, true, false);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chats/chato.json")), Conversation.class);

        assertEquals("My Conversation", c.name);
        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "User0");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "User1");
        assertEquals(ms[1].content, "how are you?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "User0");
        assertEquals(ms[2].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "User1");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "User2");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "User0");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "User2");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
    }

    /**
     * Test for flag -a for counting number of posts and giving a leader board
     * 
     * @throws Exception
     */
    @Test
    public void testExportingConversationExportsConversationA() throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation("chats/chata.txt", "chats/chata.json", "", "", "", false, false, true);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chats/chata.json")), Conversation.class);

        assertEquals("My Conversation", c.name);
        assertEquals(7, c.messages.size());
        ArrayList<String> users = new ArrayList<>();
        users.add("bob");
        users.add("angus");
        users.add("mike");
        assertEquals(users, c.sortedMostActiveUsers);
    }

    class InstantDeserializer implements JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonPrimitive()) {
                throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
            }

            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

            if (!jsonPrimitive.isNumber()) {
                throw new JsonParseException(
                        "Expected instant represented as JSON number, but different primitive found.");
            }

            return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
        }
    }
}
