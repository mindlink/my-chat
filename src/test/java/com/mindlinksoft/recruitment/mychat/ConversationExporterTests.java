package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.report.ReportGenerator;
import org.junit.Test;

import java.io.File;
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
        File chat = new File("chat.json");
        chat.delete();

        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json");

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
    public void testExportFilteredConversationFiltersUser() throws Exception {
        File chat2 = new File("chat2.json");
        chat2.delete();

        ConversationExporter exporter = new ConversationExporter();

        exporter.exportFilteredConversation("chat.txt", "chat2.json", "--filterByUser=bob");


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat2.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals("bob", ms[0].senderId);
        assertEquals("Hello there!", ms[0].content);

        assertEquals("bob", ms[1].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[1].content);

        assertEquals("bob", ms[2].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);



    }

    @Test
    public void testExportFilteredConversationFiltersKeyword() throws Exception {
        File chat3 = new File("chat3.json");
        chat3.delete();
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportFilteredConversation("chat.txt", "chat3.json", "--filterByKeyword=pie");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat3.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals("bob", ms[0].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[0].content);

        assertEquals("angus", ms[1].senderId);
        assertEquals("Hell yes! Are we buying some pie?", ms[1].content);

        assertEquals("bob", ms[2].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);

        assertEquals("angus", ms[3].senderId);
        assertEquals("YES! I'm the head pie eater there...", ms[3].content);


    }

    @Test
    public void testExportFilteredConversationBlackList() throws Exception {
        File chat4 = new File("chat4.json");
        chat4.delete();

        ConversationExporter exporter = new ConversationExporter();

        exporter.exportFilteredConversation("chat.txt", "chat4.json", "--blacklist=pie --blacklist=are --blacklist=yes");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat4.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals("bob", ms[0].senderId);
        assertEquals("Hello there!", ms[0].content);

        assertEquals("mike", ms[1].senderId);
        assertEquals("how *redacted* you?", ms[1].content);

        assertEquals("bob", ms[2].senderId);
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].content);

        assertEquals("mike", ms[3].senderId);
        assertEquals("no, let me ask Angus...", ms[3].content);

        assertEquals("angus", ms[4].senderId);
        assertEquals("Hell *redacted*! Are we buying some *redacted*?", ms[4].content);

        assertEquals("bob", ms[5].senderId);
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms[5].content);

        assertEquals("angus", ms[6].senderId);
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].content);


    }

    @Test
    public void testExportFilteredConversationReport() throws Exception {
        File chat5 = new File("chat5.json");
        chat5.delete();

        ConversationExporter exporter = new ConversationExporter();

        exporter.exportFilteredConversation("chat.txt", "chat5.json", "--report");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        ReportGenerator rg = g.fromJson(new InputStreamReader(new FileInputStream("chat5.json")), ReportGenerator.class);

        assertEquals("My Report", rg.name);

        Message[] ms = new Message[rg.messages.size()];
        rg.messages.toArray(ms);

        assertEquals("bob", ms[0].senderId);
        assertEquals("Hello there!", ms[0].content);

        assertEquals("mike", ms[1].senderId);
        assertEquals("how are you?", ms[1].content);

        assertEquals("bob", ms[2].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[2].content);

        assertEquals("mike", ms[3].senderId);
        assertEquals("no, let me ask Angus...", ms[3].content);

        assertEquals("angus", ms[4].senderId);
        assertEquals("Hell yes! Are we buying some pie?", ms[4].content);

        assertEquals("bob", ms[5].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);

        assertEquals("angus", ms[6].senderId);
        assertEquals("YES! I'm the head pie eater there...", ms[6].content);


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
