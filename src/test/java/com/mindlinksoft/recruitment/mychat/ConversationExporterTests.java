package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.constructs.Conversation;
import com.mindlinksoft.recruitment.mychat.constructs.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.constructs.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests
{
    private ConversationExporterConfiguration config;
    private ConversationExporter exporter;

    @Before
    public void setUp()
    {
        config = new ConversationExporterConfiguration("", "", "", "", new String[0], false, false, false);
        exporter = new ConversationExporter();
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Creates a generic {@link Conversation} for unit testing purposes.
     *
     * @return The {@link Conversation} for testing.
     * @throws FileNotFoundException Thrown when cannot find the output file.
     */
    private Conversation createConversation() throws FileNotFoundException
    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        return g.fromJson(new InputStreamReader(new FileInputStream(config.getOutputFilePath())), Conversation.class);
    }

    /**
     * Tests that exporting a conversation will export the conversation correctly.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void test_basic() throws Exception
    {
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("bob");

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("mike");

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("angus");

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setKeyword("there");

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setKeyword("pie");

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setKeyword("yes");

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setKeyword("hell");

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("bob");
        config.setKeyword("pie");

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("mike");
        config.setKeyword("you");

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("angus");
        config.setKeyword("hell");

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setWordsToHide(new String[]{"there", "pie", "yes", "hell"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setWordsToHide(new String[0]);

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("bob");
        config.setWordsToHide(new String[]{"there", "pie", "yes", "hell"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("mike");
        config.setWordsToHide(new String[]{"angus", "no", "you"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("angus");
        config.setWordsToHide(new String[]{"there", "pie", "yes", "hell"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setKeyword("there");
        config.setWordsToHide(new String[]{"there", "pie", "yes", "hell"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setKeyword("pie");
        config.setWordsToHide(new String[]{"there", "pie", "yes", "hell"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setKeyword("yes");
        config.setWordsToHide(new String[]{"there", "pie", "yes", "hell"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setKeyword("hell");
        config.setWordsToHide(new String[]{"pie", "yes", "hell"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation", c.getName());
        assertEquals(1, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[0].getSenderId(), "angus");
        assertEquals(ms[0].getContent(), "*redacted* *redacted*! Are we buying some *redacted*?");
    }

    @Test
    public void test_keyword_the_wordsToHide() throws Exception
    {
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setKeyword("the");
        config.setWordsToHide(new String[]{"there", "pie"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation", c.getName());
        assertEquals(2, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "No, just want to know if *redacted*'s anybody else in the *redacted* society...");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[1].getSenderId(), "angus");
        assertEquals(ms[1].getContent(), "YES! I'm the head *redacted* eater *redacted*...");
    }

    @Test
    public void test_keyword_you_wordsToHide() throws Exception
    {
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setKeyword("you");
        config.setWordsToHide(new String[]{"thanks", "pie"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation", c.getName());
        assertEquals(2, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[0].getSenderId(), "mike");
        assertEquals(ms[0].getContent(), "how are you?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[1].getSenderId(), "bob");
        assertEquals(ms[1].getContent(), "I'm good *redacted*, do you like *redacted*?");
    }

    @Test
    public void test_user_bob_keyword_pie_wordsToHide() throws Exception
    {
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("bob");
        config.setKeyword("pie");
        config.setWordsToHide(new String[]{"thanks", "pie", "there"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("mike");
        config.setKeyword("you");
        config.setWordsToHide(new String[]{"how", "you"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

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
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("angus");
        config.setKeyword("hell");
        config.setWordsToHide(new String[]{"pie", "yes", "hell"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation", c.getName());
        assertEquals(1, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[0].getSenderId(), "angus");
        assertEquals(ms[0].getContent(), "*redacted* *redacted*! Are we buying some *redacted*?");
    }

    @Test
    public void test_user_mike_keyword_hell_wordsToHide() throws Exception
    {
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("mike");
        config.setKeyword("how");
        config.setWordsToHide(new String[]{"you"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation", c.getName());
        assertEquals(1, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[0].getSenderId(), "mike");
        assertEquals(ms[0].getContent(), "how are *redacted*?");
    }

    @Test
    public void test_user_bob_keyword_im_wordsToHide() throws Exception
    {
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("bob");
        config.setKeyword("i'm");
        config.setWordsToHide(new String[]{"thanks"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation", c.getName());
        assertEquals(1, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "I'm good *redacted*, do you like pie?");
    }

    @Test
    public void test_user_bob_keyword_thanks_wordsToHide() throws Exception
    {
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setUser("bob");
        config.setKeyword("thanks");
        config.setWordsToHide(new String[]{"i'm"});

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation", c.getName());
        assertEquals(1, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "*redacted* good thanks, do you like pie?");
    }

    @Test
    public void test_hideCCPN() throws Exception
    {
        config.setInputFilePath("chatDetails.txt");
        config.setOutputFilePath("chatDetails.json");
        config.setHideCCPN(true);

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation with Details", c.getName());
        assertEquals(6, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[0].getSenderId(), "tom");
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470919));
        assertEquals(ms[1].getSenderId(), "ellis");
        assertEquals(ms[1].getContent(), "How are you?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470923));
        assertEquals(ms[2].getSenderId(), "tom");
        assertEquals(ms[2].getContent(), "I'm good thanks, can send your credit card details and your phone number?");

        assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470932));
        assertEquals(ms[3].getSenderId(), "ellis");
        assertEquals(ms[3].getContent(), "My phone number is: *redacted* and my credit card number is: *redacted*");

        assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470940));
        assertEquals(ms[4].getSenderId(), "tom");
        assertEquals(ms[4].getContent(), "My number is *redacted* and my credit card is *redacted*.");

        assertEquals(ms[5].getTimestamp(), Instant.ofEpochSecond(1448470943));
        assertEquals(ms[5].getSenderId(), "ellis");
        assertEquals(ms[5].getContent(), "Thank you.");
    }

    @Test
    public void test_user_tom_hideCCPN() throws Exception
    {
        config.setInputFilePath("chatDetails.txt");
        config.setOutputFilePath("chatDetails.json");
        config.setUser("tom");
        config.setHideCCPN(true);

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation with Details", c.getName());
        assertEquals(3, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[0].getSenderId(), "tom");
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470923));
        assertEquals(ms[1].getSenderId(), "tom");
        assertEquals(ms[1].getContent(), "I'm good thanks, can send your credit card details and your phone number?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470940));
        assertEquals(ms[2].getSenderId(), "tom");
        assertEquals(ms[2].getContent(), "My number is *redacted* and my credit card is *redacted*.");
    }

    @Test
    public void test_keyword_credit_hideCCPN() throws Exception
    {
        config.setInputFilePath("chatDetails.txt");
        config.setOutputFilePath("chatDetails.json");
        config.setKeyword("credit");
        config.setHideCCPN(true);

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation with Details", c.getName());
        assertEquals(3, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470923));
        assertEquals(ms[0].getSenderId(), "tom");
        assertEquals(ms[0].getContent(), "I'm good thanks, can send your credit card details and your phone number?");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470932));
        assertEquals(ms[1].getSenderId(), "ellis");
        assertEquals(ms[1].getContent(), "My phone number is: *redacted* and my credit card number is: *redacted*");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470940));
        assertEquals(ms[2].getSenderId(), "tom");
        assertEquals(ms[2].getContent(), "My number is *redacted* and my credit card is *redacted*.");
    }

    @Test
    public void test_obfuscate() throws Exception
    {
        config.setInputFilePath("chat.txt");
        config.setOutputFilePath("chat.json");
        config.setObf(true);

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation", c.getName());
        assertEquals(7, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertTrue(ms[0].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertTrue(ms[1].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[1].getContent(), "how are you?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertTrue(ms[2].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[2].getContent(), "I'm good thanks, do you like pie?");

        assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470910));
        assertTrue(ms[3].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[3].getContent(), "no, let me ask Angus...");

        assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertTrue(ms[4].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[4].getContent(), "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertTrue(ms[5].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[5].getContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertTrue(ms[6].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[6].getContent(), "YES! I'm the head pie eater there...");
    }

    @Test
    public void test_hideCCPN_obfuscate() throws Exception
    {
        config.setInputFilePath("chatDetails.txt");
        config.setOutputFilePath("chatDetails.json");
        config.setHideCCPN(true);
        config.setObf(true);

        exporter.exportConversation(config);
        Conversation c = createConversation();

        assertEquals("My Conversation with Details", c.getName());
        assertEquals(6, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertTrue(ms[0].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470919));
        assertTrue(ms[1].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[1].getContent(), "How are you?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470923));
        assertTrue(ms[2].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[2].getContent(), "I'm good thanks, can send your credit card details and your phone number?");

        assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470932));
        assertTrue(ms[3].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[3].getContent(), "My phone number is: *redacted* and my credit card number is: *redacted*");

        assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470940));
        assertTrue(ms[4].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[4].getContent(), "My number is *redacted* and my credit card is *redacted*.");

        assertEquals(ms[5].getTimestamp(), Instant.ofEpochSecond(1448470943));
        assertTrue(ms[5].getSenderId().matches("^\\d{6}$"));
        assertEquals(ms[5].getContent(), "Thank you.");
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
