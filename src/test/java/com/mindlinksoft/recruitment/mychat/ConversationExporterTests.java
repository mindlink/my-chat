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
     * Tests that multiple filters concurrently function correctly
     */
    @Test
    public void testMultipleFilters() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterIO exporterIO = new ConversationExporterIO();
        Conversation c;
        List<Message> ms;

        // - USER : BOB, KEYWORD : THERE ---------------------------------------
        c = exporterIO.readConversation("chat.txt");

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
        c = exporterIO.readConversation("chat.txt");

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
        c = exporterIO.readConversation("chat.txt");

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
        c = exporterIO.readConversation("chat.txt");

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
     * Tests that exporter correctly reports on a conversation when asked to
     */
    @Test
    public void testConversationReporting() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterIO exporterIO = new ConversationExporterIO();
        Conversation c;

        // - do not report ---------------------------------------------------------
        c = exporterIO.readConversation("chat.txt");
        exporter.setReport(false);
        exporter.checkReport(c);

        assertNull(c.getActivity());

        // - report on conversation ------------------------------------------------
        c = exporterIO.readConversation("chat.txt");
        exporter.setReport(true);
        exporter.checkReport(c);

        assertEquals("bob", c.getActivity().get(0).getName());
        assertEquals(3, c.getActivity().get(0).getCount());

        assertEquals("mike", c.getActivity().get(1).getName());
        assertEquals(2, c.getActivity().get(1).getCount());

        assertEquals("angus", c.getActivity().get(2).getName());
        assertEquals(2, c.getActivity().get(2).getCount());
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
