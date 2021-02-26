package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;
import picocli.CommandLine;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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

        exporter.exportConversation("chat.txt", "chat.json");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.getName());

        assertEquals(7, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals("Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
        assertEquals("mike", ms[1].getSenderId());
        assertEquals("how are you?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
        assertEquals("mike", ms[3].getSenderId());
        assertEquals("no, let me ask Angus...", ms[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
        assertEquals("angus", ms[4].getSenderId());
        assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
        assertEquals("bob", ms[5].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
        assertEquals("angus", ms[6].getSenderId());
        assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());
    }

    /**
     * Tests that filtering by username functions correctly
     */
    @Test
    public void testFilterConversationByUser() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        Conversation c;

        // - BOB ---------------------------------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setUser("bob");
        exporter.filterConversation(c);

        for (Message m : c.getMessages()) {
            assertEquals("bob", m.getSenderId());
        }

        assertEquals(3, c.getMessages().size());

        // - BOB (CAPITALISED) -----------------------------------------
        c = exporter.readConversation("chat.txt");
        // should work the same way
        exporter.setUser("BOB");
        exporter.filterConversation(c);

        for (Message m : c.getMessages()) {
            assertEquals("bob", m.getSenderId());
        }

        assertEquals(3, c.getMessages().size());

        // - MIKE --------------------------------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setUser("mike");
        exporter.filterConversation(c);

        for (Message m : c.getMessages()) {
            assertEquals("mike", m.getSenderId());
        }

        assertEquals(2, c.getMessages().size());

        // - ALICE -------------------------------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setUser("alice");
        exporter.filterConversation(c);

        assertEquals(0, c.getMessages().size());
    }


    /**
     * Tests that filtering for keywords functions correctly
     */
    @Test
    public void testFilterConversationByKeyword() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        Conversation c;
        List<Message> ms;

        // - PIE ---------------------------------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setKeyword("pie");
        exporter.filterConversation(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals("I'm good thanks, do you like pie?", ms.get(0).getContent());
        assertEquals("Hell yes! Are we buying some pie?", ms.get(1).getContent());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms.get(2).getContent());
        assertEquals("YES! I'm the head pie eater there...", ms.get(3).getContent());

        // - HELL ---------------------------------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setKeyword("Hell");
        exporter.filterConversation(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals("Hello there!", ms.get(0).getContent());
        assertEquals("Hell yes! Are we buying some pie?", ms.get(1).getContent());

        // - NOPE ---------------------------------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setKeyword("nope");
        exporter.filterConversation(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals(0, ms.size());
    }


    /**
     * Tests that redacting blacklisted words functions correctly
     */
    @Test
    public void testRedactBlacklistedWords() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        Conversation originalConversation;
        Conversation c;
        List<Message> originalMessages;
        List<Message> ms;

        // gets the original conversation (unfiltered)
        originalConversation = exporter.readConversation("chat.txt");
        originalMessages = (ArrayList<Message>) originalConversation.getMessages();

        // - [PIE] ---------------------------------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setBlacklistedWords(new String[]{"pie"});
        exporter.filterConversation(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals("Hello there!", ms.get(0).getContent());
        assertEquals("how are you?", ms.get(1).getContent());
        assertEquals("I'm good thanks, do you like *redacted*?", ms.get(2).getContent());
        assertEquals("no, let me ask Angus...", ms.get(3).getContent());
        assertEquals("Hell yes! Are we buying some *redacted*?", ms.get(4).getContent());
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms.get(5).getContent());
        assertEquals("YES! I'm the head *redacted* eater there...", ms.get(6).getContent());

        // - [I'M, YOU] -----------------------------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setBlacklistedWords(new String[]{"I'm", "you"});
        exporter.filterConversation(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals("Hello there!", ms.get(0).getContent());
        assertEquals("how are *redacted*?", ms.get(1).getContent());
        assertEquals("*redacted* good thanks, do *redacted* like pie?", ms.get(2).getContent());
        assertEquals("no, let me ask Angus...", ms.get(3).getContent());
        assertEquals("Hell yes! Are we buying some pie?", ms.get(4).getContent());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms.get(5).getContent());
        assertEquals("YES! *redacted* the head pie eater there...", ms.get(6).getContent());

        // - [NON-EXISTENT] -----------------------------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setBlacklistedWords(new String[]{"non-existent"});
        exporter.filterConversation(c);
        ms = (ArrayList<Message>) c.getMessages();

        // messages should be the same as the original as the "non-existent" string has no occurrences in the conversation
        for (int i = 0; i < ms.size(); i++) {
            assertEquals(originalMessages.get(i).getContent(), ms.get(i).getContent());
        }
    }


    /**
     * Tests that multiple filters concurrently function correctly
     */
    @Test
    public void testMultipleFilters() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        Conversation c;
        List<Message> ms;

        // - USER : BOB, KEYWORD : THERE ---------------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setUser("bob");
        exporter.setKeyword("there");
        exporter.setBlacklistedWords(null);

        exporter.filterConversation(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals(Instant.ofEpochSecond(1448470901), ms.get(0).getTimestamp());
        assertEquals("bob", ms.get(0).getSenderId());
        assertEquals("Hello there!", ms.get(0).getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms.get(1).getTimestamp());
        assertEquals("bob", ms.get(1).getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms.get(1).getContent());

        assertEquals(2, ms.size());


        // - USER : MIKE, BLACKLIST : [YOU, ANGUS] ------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setUser("mike");
        exporter.setKeyword(null);
        exporter.setBlacklistedWords(new String[]{"you", "Angus"});

        exporter.filterConversation(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals(Instant.ofEpochSecond(1448470905), ms.get(0).getTimestamp());
        assertEquals("mike", ms.get(0).getSenderId());
        assertEquals("how are *redacted*?", ms.get(0).getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms.get(1).getTimestamp());
        assertEquals("mike", ms.get(1).getSenderId());
        assertEquals("no, let me ask *redacted*...", ms.get(1).getContent());

        assertEquals(2, ms.size());


        // - KEYWORD : PIE, BLACKLIST : [PIE] -----------------------------------
        c = exporter.readConversation("chat.txt");

        exporter.setUser(null);
        exporter.setKeyword("pie");
        exporter.setBlacklistedWords(new String[]{"pie"});

        exporter.filterConversation(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals(Instant.ofEpochSecond(1448470906), ms.get(0).getTimestamp());
        assertEquals("bob", ms.get(0).getSenderId());
        assertEquals("I'm good thanks, do you like *redacted*?", ms.get(0).getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms.get(1).getTimestamp());
        assertEquals("angus", ms.get(1).getSenderId());
        assertEquals("Hell yes! Are we buying some *redacted*?", ms.get(1).getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms.get(2).getTimestamp());
        assertEquals("bob", ms.get(2).getSenderId());
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms.get(2).getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms.get(3).getTimestamp());
        assertEquals("angus", ms.get(3).getSenderId());
        assertEquals("YES! I'm the head *redacted* eater there...", ms.get(3).getContent());

        assertEquals(4, ms.size());


        // - USER : ANGUS, KEYWORD : YES, BLACKLIST : [SOME] --------------------
        c = exporter.readConversation("chat.txt");

        exporter.setUser("angus");
        exporter.setKeyword("yes");
        exporter.setBlacklistedWords(new String[]{"some"});

        exporter.filterConversation(c);
        ms = (ArrayList<Message>) c.getMessages();

        assertEquals(Instant.ofEpochSecond(1448470912), ms.get(0).getTimestamp());
        assertEquals("angus", ms.get(0).getSenderId());
        assertEquals("Hell yes! Are we buying *redacted* pie?", ms.get(0).getContent());

        assertEquals(1, ms.size());

    }


    /**
     * Tests that parameters are correctly parsed into the exporter's parameter variables
     */
    @Test
    public void testParameterParsing() {
        ConversationExporter exporter;
        ConversationExporterConfiguration configuration;
        CommandLine cmd;
        String[] args;

        // - JUST INPUT AND OUTPUT FILES -----------------------------------------------------
        args = new String[]{"-i", "chat.txt", "-o", "out.json"};

        exporter = new ConversationExporter();
        configuration = new ConversationExporterConfiguration();
        cmd = new CommandLine(configuration);
        cmd.parseArgs(args);
        exporter.getCLIParameters(configuration);

        assertNull(exporter.getUser());
        assertNull(exporter.getKeyword());
        assertNull(exporter.getBlacklistedWords());
        assertFalse(exporter.isReport());


        // - EXTRA PARAMETERS ----------------------------------------------------------------
        args = new String[]{"-i", "chat2.txt", "-o", "out2.json", "-b", "you", "-u", "mike", "-k", "pie", "-b", "there", "-r"};

        exporter = new ConversationExporter();
        configuration = new ConversationExporterConfiguration();
        cmd = new CommandLine(configuration);
        cmd.parseArgs(args);
        exporter.getCLIParameters(configuration);

        assertEquals("mike", exporter.getUser());
        assertEquals("pie", exporter.getKeyword());
        assertEquals(Arrays.toString(new String[]{"you, there"}), Arrays.toString(exporter.getBlacklistedWords()));
        assertTrue(exporter.isReport());
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
