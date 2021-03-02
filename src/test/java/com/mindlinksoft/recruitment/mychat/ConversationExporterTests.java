package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import java.util.Collection;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import java.nio.file.Files;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {

    /**
     * Represents a synonym for "new Conversation(name, msgs)" to be consistent
     * with the makeConversation() method below, while giving each test the
     * ability to create a custom conversation.
     */
    private Conversation makeConversation(String name, Collection<Message> msgs) {
        return new Conversation(name, msgs);
    }

    /**
     * Represents a helper so that each test can access the base conversation
     * without reading it in.
     */
    private Conversation makeConversation() {
        Collection<Message> msgs = new ArrayList<Message>();
        msgs.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus..."));
        msgs.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "No, just want to know if there's anybody else in the pie society..."));
        msgs.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head pie eater there..."));

        return makeConversation("My Conversation", msgs);
    }

    /**
     * Represents a helper to clear "chat.json" between tests and prevent errors
     * during end-to-end testing.
     */
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
    * Tests that filtering for Bob's messages only produces Bob's messages.
    */
    @Test
    public void testFilterUserBob() throws Exception {
        Conversation c = makeConversation();

        Message[] ms = new Message[c.getMessagesFilteredByUser("bob").size()];
        c.getMessagesFilteredByUser("bob").toArray(ms);
        assertEquals(3, ms.length);
        assertEquals("Hello there!", ms[0].content);
        assertEquals("I'm good thanks, do you like pie?", ms[1].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);
    }

    /**
    * Tests that filtering for Angus's messages only produces Angus's messages.
    */
    @Test
    public void testFilterUserAngus() throws Exception {
        Conversation c = makeConversation();

        Message[] ms = new Message[c.getMessagesFilteredByUser("angus").size()];
        c.getMessagesFilteredByUser("angus").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("Hell yes! Are we buying some pie?", ms[0].content);
        assertEquals("YES! I'm the head pie eater there...", ms[1].content);
    }

    /**
    * Tests that filtering for Mike's messages only produces Mike's messages.
    */
    @Test
    public void testFilterUserMike() throws Exception {
        Conversation c = makeConversation();

        Message[] ms = new Message[c.getMessagesFilteredByUser("mike").size()];
        c.getMessagesFilteredByUser("mike").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("how are you?", ms[0].content);
        assertEquals("no, let me ask Angus...", ms[1].content);
    }

    /**
    * Tests that filtering for a non-existant user's messages produces no messages.
    */
    @Test
    public void testFilterUserNonUserExportsNoMessages() throws Exception {
        Conversation c = makeConversation();

        Message[] ms = new Message[c.getMessagesFilteredByUser("dude").size()];
        c.getMessagesFilteredByUser("dude").toArray(ms);
        assertEquals(0, ms.length);
    }

    /**
    * Tests that filtering for keyword "pie" only produces messages containing "pie".
    */
    @Test
    public void testFilterKeywordPie() throws Exception {
        Conversation c = makeConversation();

        Message[] ms = new Message[c.getMessagesFilteredByKeyword("pie").size()];
        c.getMessagesFilteredByKeyword("pie").toArray(ms);
        assertEquals(4, ms.length);
        assertEquals("I'm good thanks, do you like pie?", ms[0].content);
        assertEquals("Hell yes! Are we buying some pie?", ms[1].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);
        assertEquals("YES! I'm the head pie eater there...", ms[3].content);
    }

    /**
    * Tests that filtering for keyword "yes" only produces messages containing "yes",
    * in any combination of upper/lower case.
    */
    @Test
    public void testFilterKeywordYesIsCaseInsensitive() throws Exception {
        Conversation c = makeConversation();

        Message[] ms = new Message[c.getMessagesFilteredByKeyword("yes").size()];
        c.getMessagesFilteredByKeyword("yes").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("Hell yes! Are we buying some pie?", ms[0].content);
        assertEquals("YES! I'm the head pie eater there...", ms[1].content);
    }

    /**
    * Tests that filtering for keyword "no" only produces messages containing "no",
    * in any combination of upper/lower case.
    */
    @Test
    public void testFilterKeywordNoIsCaseInsensitive() throws Exception {
        Conversation c = makeConversation();

        Message[] ms = new Message[c.getMessagesFilteredByKeyword("no").size()];
        c.getMessagesFilteredByKeyword("no").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("no, let me ask Angus...", ms[0].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[1].content);
    }

    /**
    * Tests that filtering for a keyword which does not appear in the
    * conversation produces no messages.
    */
    @Test
    public void testFilterKeywordNonWordExportsNoMessages() throws Exception {
        Conversation c = makeConversation();

        Message[] ms = new Message[c.getMessagesFilteredByKeyword("dude").size()];
        c.getMessagesFilteredByKeyword("dude").toArray(ms);
        assertEquals(0, ms.length);
    }

    /**
    * Tests that blacklisting "pie" and "Angus" produces all messages
    * with those words replaced with "*redacted*".
    */
    @Test
    public void testBlacklistPieAngus() throws Exception {
        Conversation c = makeConversation();

        String[] blacklist = {"pie", "Angus"};
        Message[] ms = new Message[c.getMessagesCensored(blacklist).size()];
        c.getMessagesCensored(blacklist).toArray(ms);
        assertEquals(7, ms.length);
        assertEquals("Hello there!", ms[0].content);
        assertEquals("how are you?", ms[1].content);
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].content);
        assertEquals("no, let me ask *redacted*...", ms[3].content);
        assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].content);
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms[5].content);
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].content);
    }

    /**
    * Tests that blacklisting "angus" replaces "Angus" with "*redacted"
    */
    @Test
    public void testBlacklistAngusIsCaseInsensitive() throws Exception {
        Conversation c = makeConversation();

        String[] blacklist = {"angus"};
        Message[] ms = new Message[c.getMessagesCensored(blacklist).size()];
        c.getMessagesCensored(blacklist).toArray(ms);
        assertEquals(7, ms.length);
        assertEquals("Hello there!", ms[0].content);
        assertEquals("how are you?", ms[1].content);
        assertEquals("I'm good thanks, do you like pie?", ms[2].content);
        assertEquals("no, let me ask *redacted*...", ms[3].content);
        assertEquals("Hell yes! Are we buying some pie?", ms[4].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);
        assertEquals("YES! I'm the head pie eater there...", ms[6].content);
    }

    /**
    * Tests that composing a report on the full conversation is accurate.
    */
    @Test
    public void testBaseReportIsAccurate() throws Exception {
        Conversation c = makeConversation();
        ConversationEditor editor = new ConversationEditor(null);

        Activity[] activities = new Activity[editor.composeReport(c).size()];
        editor.composeReport(c).toArray(activities);
        assertEquals(3, activities.length);

        assertEquals("bob", activities[0].sender);
        assertEquals(3, activities[0].count);

        assertEquals("mike", activities[1].sender);
        assertEquals(2, activities[1].count);

        assertEquals("angus", activities[2].sender);
        assertEquals(2, activities[2].count);
    }

    /**
    * Tests that the Activities in the report are ordered by count.
    */
    @Test
    public void testReportOrdersByCount() throws Exception {
        Collection<Message> msgs = new ArrayList<Message>();
        msgs.add(new Message(Instant.ofEpochSecond(1448470901), "angus", "hi guys"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "sup"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470906), "mike", "something about pie"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470910), "bob", "mm yeah pie"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "hmm i dunno"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "come on man"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470915), "bob", "pie's great"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470915), "mike", "yeah, jeez"));
        msgs.add(new Message(Instant.ofEpochSecond(1448470915), "bob", "k let's go get pie"));

        Conversation c = makeConversation("My Conversation", msgs);
        ConversationEditor editor = new ConversationEditor(null);

        Activity[] activities = new Activity[editor.composeReport(c).size()];
        editor.composeReport(c).toArray(activities);
        assertEquals(3, activities.length);

        assertEquals("bob", activities[0].sender);
        assertEquals(4, activities[0].count);

        assertEquals("mike", activities[1].sender);
        assertEquals(3, activities[1].count);

        assertEquals("angus", activities[2].sender);
        assertEquals(2, activities[2].count);
    }

    /**
    * Tests that composing a report on an empty conversation gives an empty report.
    */
    @Test
    public void testEmptyConversationReportIsEmpty() throws Exception {
        Conversation c = makeConversation("My Conversation", new ArrayList<Message>());
        ConversationEditor editor = new ConversationEditor(null);

        Activity[] activities = new Activity[editor.composeReport(c).size()];
        editor.composeReport(c).toArray(activities);
        assertEquals(0, activities.length);
    }

    /**
    * Tests that composing a report on a conversation with one sender is accurate.
    */
    @Test
    public void testReportAfterFilteringForSingleSender() throws Exception {
        Conversation c = makeConversation();

        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.filterUser = "mike";

        ConversationEditor editor = new ConversationEditor(config);
        editor.editConversation(c);

        Activity[] activities = new Activity[editor.composeReport(c).size()];
        editor.composeReport(c).toArray(activities);
        assertEquals(1, activities.length);

        assertEquals("mike", activities[0].sender);
        assertEquals(2, activities[0].count);
    }

    /**
    * Tests that the exporter can filter for Bob's messages.
    */
    @Test
    public void testEndToEndFilterByUserBob() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "chat.txt";
        config.outputFilePath = "chat.json";
        config.filterUser = "bob";

        exporter.exportConversation(config);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);
        assertEquals(3, c.messages.size());

        Message[] ms = new Message[3];
        c.messages.toArray(ms);
        assertEquals("Hello there!", ms[0].content);
        assertEquals("bob", ms[0].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[1].content);
        assertEquals("bob", ms[1].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);
        assertEquals("bob", ms[2].senderId);
    }

    /**
    * Tests that the exporter can filter for messages containing "pie".
    */
    @Test
    public void testEndToEndFilterByKeywordPie() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "chat.txt";
        config.outputFilePath = "chat.json";
        config.keyword = "pie";

        exporter.exportConversation(config);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);
        assertEquals(4, c.messages.size());

        Message[] ms = new Message[4];
        c.messages.toArray(ms);
        assertEquals("I'm good thanks, do you like pie?", ms[0].content);
        assertEquals("bob", ms[0].senderId);
        assertEquals("Hell yes! Are we buying some pie?", ms[1].content);
        assertEquals("angus", ms[1].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);
        assertEquals("bob", ms[2].senderId);
        assertEquals("YES! I'm the head pie eater there...", ms[3].content);
        assertEquals("angus", ms[3].senderId);
    }

    /**
    * Tests that the exporter can filter by user and keyword and still include
    * an accurate report.
    */
    @Test
    public void testEndToEndFilterByUserMikeAndKeywordNoAndReport() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "chat.txt";
        config.outputFilePath = "chat.json";
        config.filterUser = "mike";
        config.keyword = "no";
        config.report = true;

        exporter.exportConversation(config);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);
        assertEquals(1, c.messages.size());

        Message[] ms = new Message[1];
        c.messages.toArray(ms);
        assertEquals("no, let me ask Angus...", ms[0].content);
        assertEquals("mike", ms[0].senderId);

        assertEquals(1, c.activities.size());
        Activity[] activities = new Activity[1];
        c.activities.toArray(activities);
        assertEquals("mike", activities[0].sender);
        assertEquals(1, activities[0].count);
    }

    /**
    * Tests that the exporter can redact "pie".
    */
    @Test
    public void testEndToEndBlacklistPie() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "chat.txt";
        config.outputFilePath = "chat.json";
        config.blacklist = new String[1];
        config.blacklist[0] = "pie";

        exporter.exportConversation(config);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);
        assertEquals(7, c.messages.size());

        Message[] ms = new Message[7];
        c.messages.toArray(ms);
        assertEquals("Hello there!", ms[0].content);
        assertEquals("how are you?", ms[1].content);
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].content);
        assertEquals("no, let me ask Angus...", ms[3].content);
        assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].content);
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms[5].content);
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].content);
    }

    /**
    * Tests that conversation.activities is null if no report
    * is specified in the configuration.
    */
    @Test
    public void testEndToEndNoReportActivitesIsNull() throws Exception {
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
        assertEquals(null, c.activities);
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
