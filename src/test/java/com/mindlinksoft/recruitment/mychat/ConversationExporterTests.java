package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.constructs.Conversation;
import com.mindlinksoft.recruitment.mychat.constructs.Message;
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
    private String user;
    private String keyword;
    private String[] wordsToHide;

    @Before
    public void setUp()
    {
        exporter = new ConversationExporter();
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
    public void test_basic() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        exporter.exportConversation(inputPath, outputPath, null, null, null);
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
        exporter.exportConversation(inputPath, outputPath, user, null, null);
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
        exporter.exportConversation(inputPath, outputPath, user, null, null);
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
        exporter.exportConversation(inputPath, outputPath, user, null, null);
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
        exporter.exportConversation(inputPath, outputPath, null, keyword, null);
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
        exporter.exportConversation(inputPath, outputPath, null, keyword, null);
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
        exporter.exportConversation(inputPath, outputPath, null, keyword, null);
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
        exporter.exportConversation(inputPath, outputPath, null, keyword, null);
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
        exporter.exportConversation(inputPath, outputPath, user, keyword, null);
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
        exporter.exportConversation(inputPath, outputPath, user, keyword, null);
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
        exporter.exportConversation(inputPath, outputPath, user, keyword, null);
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
    public void test_wordsToHide() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        wordsToHide = new String[]{"there", "pie", "yes", "hell"};
        exporter.exportConversation(inputPath, outputPath, null, null, wordsToHide);
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
        assertEquals(ms[0].getContent(), "Hello *redacted*!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].getSenderId(), "mike");
        assertEquals(ms[1].getContent(), "how are you?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].getSenderId(), "bob");
        assertEquals(ms[2].getContent(), "I'm good thanks, do you like *redacted*?");

        assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].getSenderId(), "mike");
        assertEquals(ms[3].getContent(), "no, let me ask Angus...");

        assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].getSenderId(), "angus");
        assertEquals(ms[4].getContent(), "*redacted* *redacted*! Are we buying some *redacted*?");

        assertEquals(ms[5].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].getSenderId(), "bob");
        assertEquals(ms[5].getContent(), "No, just want to know if *redacted*'s anybody else in the *redacted* society...");

        assertEquals(ms[6].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].getSenderId(), "angus");
        assertEquals(ms[6].getContent(), "*redacted*! I'm the head *redacted* eater *redacted*...");
    }

    @Test
    public void test_wordsToHide_empty() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        wordsToHide = new String[0];
        exporter.exportConversation(inputPath, outputPath, null, null, wordsToHide);
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
    public void test_user_bob_wordsToHide() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "bob";
        wordsToHide = new String[]{"there", "pie", "yes", "hell"};
        exporter.exportConversation(inputPath, outputPath, user, null, wordsToHide);
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
        assertEquals(ms[0].getContent(), "Hello *redacted*!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[1].getSenderId(), "bob");
        assertEquals(ms[1].getContent(), "I'm good thanks, do you like *redacted*?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].getSenderId(), "bob");
        assertEquals(ms[2].getContent(), "No, just want to know if *redacted*'s anybody else in the *redacted* society...");
    }

    @Test
    public void test_user_mike_wordsToHide() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "mike";
        wordsToHide = new String[]{"angus", "no", "you"};
        exporter.exportConversation(inputPath, outputPath, user, null, wordsToHide);
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
        assertEquals(ms[0].getContent(), "how are *redacted*?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(ms[1].getSenderId(), "mike");
        assertEquals(ms[1].getContent(), "*redacted*, let me ask *redacted*...");
    }

    @Test
    public void test_user_angus_wordsToHide() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "angus";
        wordsToHide = new String[]{"there", "pie", "yes", "hell"};
        exporter.exportConversation(inputPath, outputPath, user, null, wordsToHide);
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
        assertEquals(ms[0].getContent(), "*redacted* *redacted*! Are we buying some *redacted*?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[1].getSenderId(), "angus");
        assertEquals(ms[1].getContent(), "*redacted*! I'm the head *redacted* eater *redacted*...");
    }

    @Test
    public void test_keyword_there_wordsToHide() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        keyword = "there";
        wordsToHide = new String[]{"there", "pie", "yes", "hell"};
        exporter.exportConversation(inputPath, outputPath, null, keyword, wordsToHide);
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
        assertEquals(ms[0].getContent(), "Hello *redacted*!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[1].getSenderId(), "bob");
        assertEquals(ms[1].getContent(), "No, just want to know if *redacted*'s anybody else in the *redacted* society...");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[2].getSenderId(), "angus");
        assertEquals(ms[2].getContent(), "*redacted*! I'm the head *redacted* eater *redacted*...");
    }

    @Test
    public void test_keyword_pie_wordsToHide() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        keyword = "pie";
        wordsToHide = new String[]{"there", "pie", "yes", "hell"};
        exporter.exportConversation(inputPath, outputPath, null, keyword, wordsToHide);
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
        assertEquals(ms[0].getContent(), "I'm good thanks, do you like *redacted*?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[1].getSenderId(), "angus");
        assertEquals(ms[1].getContent(), "*redacted* *redacted*! Are we buying some *redacted*?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].getSenderId(), "bob");
        assertEquals(ms[2].getContent(), "No, just want to know if *redacted*'s anybody else in the *redacted* society...");

        assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[3].getSenderId(), "angus");
        assertEquals(ms[3].getContent(), "*redacted*! I'm the head *redacted* eater *redacted*...");
    }

    @Test
    public void test_keyword_yes_wordsToHide() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        keyword = "yes";
        wordsToHide = new String[]{"there", "pie", "yes", "hell"};
        exporter.exportConversation(inputPath, outputPath, null, keyword, wordsToHide);
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
        assertEquals(ms[0].getContent(), "*redacted* *redacted*! Are we buying some *redacted*?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[1].getSenderId(), "angus");
        assertEquals(ms[1].getContent(), "*redacted*! I'm the head *redacted* eater *redacted*...");
    }

    @Test
    public void test_keyword_hell_wordsToHide() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        keyword = "hell";
        wordsToHide = new String[]{"pie", "yes", "hell"};
        exporter.exportConversation(inputPath, outputPath, null, keyword, wordsToHide);
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
        assertEquals(ms[0].getContent(), "*redacted* *redacted*! Are we buying some *redacted*?");
    }

    // TODO: Fix handling keyword filters and blacklisted words with apostrophes (e.g. I'm)
    @Test
    public void test_user_bob_keyword_pie_wordsToHide() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "bob";
        keyword = "pie";
        wordsToHide = new String[]{"thanks", "pie", "there"};
        exporter.exportConversation(inputPath, outputPath, user, keyword, wordsToHide);
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
        assertEquals(ms[0].getContent(), "I'm good *redacted*, do you like *redacted*?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[1].getSenderId(), "bob");
        assertEquals(ms[1].getContent(), "No, just want to know if *redacted*'s anybody else in the *redacted* society...");
    }

    @Test
    public void test_user_mike_keyword_you_wordsToHide() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "mike";
        keyword = "you";
        wordsToHide = new String[]{"how", "you"};
        exporter.exportConversation(inputPath, outputPath, user, keyword, wordsToHide);
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
        assertEquals(ms[0].getContent(), "*redacted* are *redacted*?");
    }

    @Test
    public void test_user_angus_keyword_hell_wordsToHide() throws Exception
    {
        inputPath = "chat.txt";
        outputPath = "chat.json";
        user = "angus";
        keyword = "hell";
        wordsToHide = new String[]{"pie", "yes", "hell"};
        exporter.exportConversation(inputPath, outputPath, user, keyword, wordsToHide);
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
        assertEquals(ms[0].getContent(), "*redacted* *redacted*! Are we buying some *redacted*?");
    }

    static class InstantDeserializer implements JsonDeserializer<Instant>
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
