package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests
{
    private ConversationExporter exporter;
    private String inputPath;
    private String outputPath;
    private String redact;
    private String user;
    private String keyword;
    private String[] wordsToHide;

    @Before
    public void setUp()
    {
        exporter = new ConversationExporter();
        redact = "*redacted*";
        user = null;
        keyword = null;
        wordsToHide = null;
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Tests that exporting a conversation will export the conversation correctly.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        exporter.exportConversation(inputPath, outputPath, null, null, null, redact);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputPath)), Conversation.class);

        assertEquals("My Conversation", c.getName());
        assertEquals(7, c.getMessages().size());

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

    @Test
    public void test_user_bob() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "bob";
        exporter.exportConversation(inputPath, outputPath, user, null, null, redact);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputPath)), Conversation.class);

        assertEquals("My Conversation", c.getName());
        assertEquals(3, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[1].getSenderId(), "bob");
        assertEquals(ms[1].getContent(), "I'm good thanks, do you like pie?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].getSenderId(), "bob");
        assertEquals(ms[2].getContent(), "No, just want to know if there's anybody else in the pie society...");
    }

    @Test
    public void test_user_mike() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "mike";
        exporter.exportConversation(inputPath, outputPath, user, null, null, redact);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputPath)), Conversation.class);

        assertEquals("My Conversation", c.getName());
        assertEquals(2, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[0].getSenderId(), "mike");
        assertEquals(ms[0].getContent(), "how are you?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(ms[1].getSenderId(), "mike");
        assertEquals(ms[1].getContent(), "no, let me ask Angus...");
    }

    @Test
    public void test_user_angus() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "angus";
        exporter.exportConversation(inputPath, outputPath, user, null, null, redact);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputPath)), Conversation.class);

        assertEquals("My Conversation", c.getName());
        assertEquals(2, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[0].getSenderId(), "angus");
        assertEquals(ms[0].getContent(), "Hell yes! Are we buying some pie?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[1].getSenderId(), "angus");
        assertEquals(ms[1].getContent(), "YES! I'm the head pie eater there...");
    }

    @Test
    public void test_keyword_there() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        keyword = "there";
        exporter.exportConversation(inputPath, outputPath, null, keyword, null, redact);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputPath)), Conversation.class);

        assertEquals("My Conversation", c.getName());
        assertEquals(3, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[1].getSenderId(), "bob");
        assertEquals(ms[1].getContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[2].getSenderId(), "angus");
        assertEquals(ms[2].getContent(), "YES! I'm the head pie eater there...");
    }

    @Test
    public void test_keyword_pie() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        keyword = "pie";
        exporter.exportConversation(inputPath, outputPath, null, keyword, null, redact);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputPath)), Conversation.class);

        assertEquals("My Conversation", c.getName());
        assertEquals(4, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "I'm good thanks, do you like pie?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[1].getSenderId(), "angus");
        assertEquals(ms[1].getContent(), "Hell yes! Are we buying some pie?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].getSenderId(), "bob");
        assertEquals(ms[2].getContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[3].getSenderId(), "angus");
        assertEquals(ms[3].getContent(), "YES! I'm the head pie eater there...");
    }

    @Test
    public void test_keyword_yes() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        keyword = "yes";
        exporter.exportConversation(inputPath, outputPath, null, keyword, null, redact);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputPath)), Conversation.class);

        assertEquals("My Conversation", c.getName());
        assertEquals(2, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[0].getSenderId(), "angus");
        assertEquals(ms[0].getContent(), "Hell yes! Are we buying some pie?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[1].getSenderId(), "angus");
        assertEquals(ms[1].getContent(), "YES! I'm the head pie eater there...");
    }

    @Test
    public void test_keyword_hell() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        keyword = "hell";
        exporter.exportConversation(inputPath, outputPath, null, keyword, null, redact);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputPath)), Conversation.class);

        assertEquals("My Conversation", c.getName());
        assertEquals(1, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[0].getSenderId(), "angus");
        assertEquals(ms[0].getContent(), "Hell yes! Are we buying some pie?");
    }

    @Test
    public void test_user_bob_keyword_pie() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "bob";
        keyword = "pie";
        exporter.exportConversation(inputPath, outputPath, user, keyword, null, redact);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputPath)), Conversation.class);

        assertEquals("My Conversation", c.getName());
        assertEquals(2, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "I'm good thanks, do you like pie?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[1].getSenderId(), "bob");
        assertEquals(ms[1].getContent(), "No, just want to know if there's anybody else in the pie society...");
    }

    @Test
    public void test_user_mike_keyword_you() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "mike";
        keyword = "you";
        exporter.exportConversation(inputPath, outputPath, user, keyword, null, redact);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputPath)), Conversation.class);

        assertEquals("My Conversation", c.getName());
        assertEquals(1, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[0].getSenderId(), "mike");
        assertEquals(ms[0].getContent(), "how are you?");
    }

    @Test
    public void test_user_angus_keyword_hell() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "angus";
        keyword = "hell";
        exporter.exportConversation(inputPath, outputPath, user, keyword, null, redact);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputPath)), Conversation.class);

        assertEquals("My Conversation", c.getName());
        assertEquals(1, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[0].getSenderId(), "angus");
        assertEquals(ms[0].getContent(), "Hell yes! Are we buying some pie?");
    }

    class InstantDeserializer implements JsonDeserializer<Instant>
    {
        @Override
        public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
        {
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
