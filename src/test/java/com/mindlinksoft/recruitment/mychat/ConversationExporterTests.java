package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "chat.txt";
        config.outputFilePath = "chat.json";

        exporter.exportConversation(config);

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

    /**
     * Tests that exporting a conversation filtered by userName will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testUserNameFilteredConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "chat.txt";
        config.outputFilePath = "chatWithNameFilter.json";
        config.userName = "bob";

        exporter.exportConversation(config);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatWithNameFilter.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(3, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("Hello there!", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[1].timestamp);
        assertEquals("bob", ms[1].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[1].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[2].timestamp);
        assertEquals("bob", ms[2].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);
    }

    /**
     * Tests that exporting a conversation filtered by keyWord will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testKeyWordFilteredConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "chat.txt";
        config.outputFilePath = "chatWithWordFilter.json";
        config.keyWord = "pie";

        exporter.exportConversation(config);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatWithWordFilter.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(4, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals("I'm good thanks, do you like pie?", ms[0].content);
        assertEquals("Hell yes! Are we buying some pie?", ms[1].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);
        assertEquals("YES! I'm the head pie eater there...", ms[3].content);
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
