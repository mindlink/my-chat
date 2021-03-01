package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import java.nio.file.Files;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {

    @Before
    public void clearOutputFile() throws Exception {
        try {
            new FileOutputStream("chat.json").close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found when cleaning 'chat.json': " + e.getMessage());
        }
    }

    /**
    * Tests that exporting a conversation will export the conversation correctly.
    * @throws Exception When something bad happens.
    */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", null, null, null);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

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
        assertEquals("I'm good thanks, do you like pie?", ms[2].content);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
        assertEquals("mike", ms[3].senderId);
        assertEquals("no, let me ask Angus...", ms[3].content);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
        assertEquals("angus", ms[4].senderId);
        assertEquals("Hell yes! Are we buying some pie?", ms[4].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
        assertEquals("bob", ms[5].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
        assertEquals("angus", ms[6].senderId);
        assertEquals("YES! I'm the head pie eater there...", ms[6].content);
    }

    @Test
    public void testFilterByUser() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", null, null, null);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.filteredByUser("bob").size()];
        c.filteredByUser("bob").toArray(ms);
        assertEquals(3, ms.length);
        assertEquals("Hello there!", ms[0].content);
        assertEquals("I'm good thanks, do you like pie?", ms[1].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);

        ms = new Message[c.filteredByUser("angus").size()];
        c.filteredByUser("angus").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("Hell yes! Are we buying some pie?", ms[0].content);
        assertEquals("YES! I'm the head pie eater there...", ms[1].content);

        ms = new Message[c.filteredByUser("mike").size()];
        c.filteredByUser("mike").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("how are you?", ms[0].content);
        assertEquals("no, let me ask Angus...", ms[1].content);

        ms = new Message[c.filteredByUser("dude").size()];
        c.filteredByUser("dude").toArray(ms);
        assertEquals(0, ms.length);
    }

    @Test
    public void testFilterByKeyword() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", null, null, null);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.filteredByKeyword("pie").size()];
        c.filteredByKeyword("pie").toArray(ms);
        assertEquals(4, ms.length);
        assertEquals("I'm good thanks, do you like pie?", ms[0].content);
        assertEquals("Hell yes! Are we buying some pie?", ms[1].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);
        assertEquals("YES! I'm the head pie eater there...", ms[3].content);

        ms = new Message[c.filteredByKeyword("yes").size()];
        c.filteredByKeyword("yes").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("Hell yes! Are we buying some pie?", ms[0].content);
        assertEquals("YES! I'm the head pie eater there...", ms[1].content);

        ms = new Message[c.filteredByKeyword("no").size()];
        c.filteredByKeyword("no").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("no, let me ask Angus...", ms[0].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[1].content);

        ms = new Message[c.filteredByKeyword("dude").size()];
        c.filteredByKeyword("dude").toArray(ms);
        assertEquals(0, ms.length);
    }

    @Test
    public void testBlacklisting() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", null, null, null);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        String[] blacklist = {"pie", "Angus"};
        Message[] ms = new Message[c.censored(blacklist).size()];
        c.censored(blacklist).toArray(ms);
        assertEquals(7, ms.length);
        assertEquals("Hello there!", ms[0].content);
        assertEquals("how are you?", ms[1].content);
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].content);
        assertEquals("no, let me ask *redacted*...", ms[3].content);
        assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].content);
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms[5].content);
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].content);

        blacklist = new String[1];
        blacklist[0] = "angus";
        ms = new Message[c.censored(blacklist).size()];
        c.censored(blacklist).toArray(ms);
        assertEquals(7, ms.length);
        assertEquals("no, let me ask *redacted*...", ms[3].content);

        blacklist[0] = " ";
        ms = new Message[c.censored(blacklist).size()];
        c.censored(blacklist).toArray(ms);
        assertEquals(7, ms.length);
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
