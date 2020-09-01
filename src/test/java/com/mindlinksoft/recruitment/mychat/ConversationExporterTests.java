package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        String[] arguments ={"chat.txt","chat.json"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(arguments);

        //exporter.exportConversation("chat.txt", "chat.json", configuration.options);

        String[] args = new String[]{"chat.txt", "chat.json"};
        ConversationExporterConfiguration Configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(8, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].userID, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].userID, "mike");
        assertEquals(ms[1].content, "how are you?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].userID, "bob");
        assertEquals(ms[2].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].userID, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].userID, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].userID, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].userID, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");

        assertEquals(ms[7].timestamp, Instant.ofEpochSecond(1448470916));
        assertEquals(ms[7].userID, "bob");
        assertEquals(ms[7].content, "My number is 07711846868, and card number is 3638 5367 7522 1116");
    }

    @Test(expected = FileNotFoundException.class)
    public void outputNoneExistentTest() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "folder/chat.json"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(args[0], args[1], configuration.options);
    }

    @Test(expected = FileNotFoundException.class)
    public void inputNoneExistentTest() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"nonexistent.txt", "chat.json"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(args[0], args[1], configuration.options);
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidOptionFlagTest() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        //checks if an error is thrown when an invalid optionSetting is selected (incorrect flag).
        String[] args = new String[]{"chat.txt", "chat.json", "**p"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(args[0], args[1], configuration.options);
    }

    @Test(expected = IllegalArgumentException.class)
    public void argumentRequiredTest() throws Exception {
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(IllegalArgumentException.class);

        ConversationExporter exporter = new ConversationExporter();

        //checks if an optionSetting that requires an argument throws an error when no argument is given.
        String[] args = new String[]{"chat.txt", "chat.json", "**B"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(args[0], args[1], configuration.options);
    }

    @Test
    public void UserFilterTest() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "chat.json", "**Umike"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        for (Message m : c.messages) {
            assertTrue( m.userID.equals("mike") );
        }
    }


    @Test
    public void keywordFilterTest() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "chat.json", "**Kpie"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        for (Message m : c.messages) {
            assertTrue( m.content.contains("pie") );
        }
    }

    @Test
    public void blackListFilterTest() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "chat.json", "**Bpie"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        for (Message m : c.messages) {
            assertTrue( !m.content.contains("pie") );
        }
    }

    @Test
    public void numbersRedactorTest() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "chat.json", "**N"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        ArrayList<String> numbers = new ArrayList<>();

        numbers.add("07724861735");
        numbers.add("4461889910761010");
        numbers.add("4461-8899-1076-1010");
        numbers.add("446-889-1076-1010");
        numbers.add("4461 8899 1076 1010");
        numbers.add("446 889 1076 1010");

        for (Message m : c.messages) {
            for (String shouldBeHiddenNumbers : numbers) {
                assertTrue( !m.content.contains( shouldBeHiddenNumbers ) );
            }
        }
    }

    @Test
    public void obfuscateTest() throws Exception {
        //parse non-obfuscated conversation
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "chat.json"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] cMessages = new Message[c.messages.size()];
        c.messages.toArray(cMessages);

        //parse obfuscated conversation
        args = new String[]{"chat.txt", "chat.json", "**O"};
        configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        g = builder.create();

        Conversation cObf = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        Message[] cObfMessages = new Message[c.messages.size()];
        c.messages.toArray(cObfMessages);

        Map<String, String> userIDToObfuscatedID = new HashMap<String, String>() {};

        for (int i = 0; i < c.messages.size(); i++) {
            assertEquals( cObfMessages[i].content, cMessages[i].content  );

            //if this obfuscated userID had already been encountered
            if (userIDToObfuscatedID.containsKey(cObfMessages[i].userID)) {
                assertEquals( userIDToObfuscatedID.get(cObfMessages[i].userID), cMessages[i].userID  );
            } else {
                userIDToObfuscatedID.put(cObfMessages[i].userID, cMessages[i].userID);
            }
        }
    }

    //Unfortunately did not manage to complete this test but left the code in as I believe I'm almost at the right stage to have it working
    /*@Test
    public void activityReportTest() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "chat.json", "**A"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Map<String, Integer> activeUsers = c.activityReport;

        assertEquals((long) activeUsers.get("bob"), 4);
        assertEquals((long) activeUsers.get("mike"), 2);
        assertEquals((long) activeUsers.get("angus"), 2);

    }*/

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
