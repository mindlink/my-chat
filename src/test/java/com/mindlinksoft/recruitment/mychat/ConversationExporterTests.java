package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;

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

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals("I'm good thanks, do you like pie?", ms[0].content);
        assertEquals("Hell yes! Are we buying some pie?", ms[1].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);
        assertEquals("YES! I'm the head pie eater there...", ms[3].content);
    }

    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingRedactedConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "chat.txt";
        config.outputFilePath = "chatRedacted.json";
        String[] redactedWords = {"pie", "Angus"};
        config.redactedWords = redactedWords;

        exporter.exportConversation(config);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatRedacted.json")), Conversation.class);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals("Hello there!", ms[0].content);
        assertEquals("how are you?", ms[1].content);
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].content);
        assertEquals("no, let me ask *redacted*...", ms[3].content);
        assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].content);
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms[5].content);
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].content);
    }

    /**
     * Test for correct number of senders in report and correct report order
     */
    @Test
    public void testConversationGenerateReport() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "chat.txt";
        config.outputFilePath = "chatWithReport.json";
        config.reportRequested = true;

        exporter.exportConversation(config);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatWithReport.json")), Conversation.class);
        List<Log> report = c.generateReport();
        c = new Conversation(c, report);

        int[] messageCounts = new int[c.report.size()];
        for (int i = 0; i < report.size(); i++) {
            messageCounts[i] = report.get(i).getMessageCount();
        }
        int[] expectedMessageCounts = {3, 2, 2};

        assertEquals(3, report.size());
        Assert.assertArrayEquals(expectedMessageCounts,messageCounts);
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
