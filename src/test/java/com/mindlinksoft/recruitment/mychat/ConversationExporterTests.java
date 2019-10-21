package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
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
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "chat.json"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        assertEquals(ms[1].content, "how are you?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
    }

    @Test(expected = FileNotFoundException.class)
    public void testNonExistentOutput() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "folder/chat.json"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(args[0], args[1], configuration.options);
    }

    @Test(expected = FileNotFoundException.class)
    public void testNonExistentInput() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"doesntexistchat.txt", "chat.json"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(args[0], args[1], configuration.options);
    }

    @Test(expected = IOException.class)
    public void testWritingToProtected() throws Exception {

        File myProtectedReadFile = new File("protectedwritechat.json");

        if (!myProtectedReadFile.exists()) {
            myProtectedReadFile.createNewFile();
        }

        myProtectedReadFile.setWritable(false);

        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "protectedwritechat.json"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(args[0], args[1], configuration.options);
    }

    @Test(expected = IOException.class)
    public void testReadingFromProtected() throws Exception {
        File myProtectedReadFile = new File("protectedreadchat.txt");

        if (!myProtectedReadFile.exists()) {
            myProtectedReadFile.createNewFile();
        }

        myProtectedReadFile.setReadable(false);

        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"protectedreadchat.txt", "chat.json"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(args[0], args[1], configuration.options);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOption() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "chat.json", "-fakeOption"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(args[0], args[1], configuration.options);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOptions() throws Exception {
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(IllegalArgumentException.class);

        ConversationExporter exporter = new ConversationExporter();

        //check to see if an option that requires an argument, when not provided with an argument, throws an error
        String[] args = new String[]{"chat.txt", "chat.json", "-k:"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(args[0], args[1], configuration.options);
    }

    @Test
    public void testActiveUsers() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "chat.json", "-a"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Map<String, Integer> activeUsers = (Map<String, Integer>)c.activeUsers;

        assertEquals( (long) activeUsers.get("bob"), 3 ) ;
        assertEquals( (long) activeUsers.get("mike"), 2 ) ;
        assertEquals( (long) activeUsers.get("angus"), 2 ) ;

    }

    @Test
    public void testBlackListFilter() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "chat.json", "-b:pie"};
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
    public void testKeywordFilter() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"chat.txt", "chat.json", "-k:pie"};
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
    public void testPhoneCardFilter() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"cardTest.txt", "chat.json", "-c"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        ArrayList<String> cardsPhones = new ArrayList<>();

        cardsPhones.add("07724861735");
        cardsPhones.add("4461889910761010");
        cardsPhones.add("4461-8899-1076-1010");
        cardsPhones.add("446-889-1076-1010");

        for (Message m : c.messages) {
            for (String shouldBeHiddenNumbers : cardsPhones) {
                assertTrue( !m.content.contains( shouldBeHiddenNumbers ) );
            }
        }
    }

    @Test
    public void testUserFilter() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"cardTest.txt", "chat.json", "-u:mike"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        for (Message m : c.messages) {
            assertTrue( m.senderId.equals("mike") );
        }
    }

    @Test
    public void testUserObfuscate() throws Exception {
        //parse non-obfuscated conversation
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[]{"cardTest.txt", "chat.json"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] cMessages = new Message[c.messages.size()];
        c.messages.toArray(cMessages);

        //parse obfuscated conversation
        args = new String[]{"cardTest.txt", "chat.json", "-o"};
        configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(args[0], args[1], configuration.options);

        builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        g = builder.create();

        Conversation cObf = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        Message[] cObfMessages = new Message[c.messages.size()];
        c.messages.toArray(cObfMessages);

        //test maintenance of userID <-> message relationship
        Map<String, String> userIDToObfuscatedID = new HashMap<String, String>() {};

        for (int i = 0; i < c.messages.size(); i++) {
            assertEquals( cObfMessages[i].content, cMessages[i].content  );
            //if this obfuscated userID had already been encountered
            if (userIDToObfuscatedID.containsKey(cObfMessages[i].senderId)) {
                assertEquals( userIDToObfuscatedID.get(cObfMessages[i].senderId), cMessages[i].senderId  );
            } else {
                userIDToObfuscatedID.put(cObfMessages[i].senderId, cMessages[i].senderId);
            }
        }
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
