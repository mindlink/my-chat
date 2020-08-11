package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

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
        ConversationExporter exporter = new ConversationExporter();
        String[] args = {"../chat.txt", "../chat.json", "user=ben"};
        ConversationExporterConfiguration testConfiguration = new ConversationExporterConfiguration(args[0], args[1]);
        exporter.exportConversation(testConfiguration);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("../chat.json")), Conversation.class);

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
   
    
    @Test
    public void testParseCommandLineArguments() throws Exception{
    		String[] args = {"../chat.txt", "../chat.json", "user=angus"};
        CommandLineArgumentParser argumentParserTest = new CommandLineArgumentParser();
        ConversationExporterConfiguration configuratorTest  = argumentParserTest.parseCommandLineArguments(args);
        FilterByUser testFilterByUser = new FilterByUser();
        assertEquals("../chat.txt", configuratorTest.inputFilePath);
        assertEquals("../chat.json", configuratorTest.outputFilePath);
        assertEquals(1, testFilterByUser.getRequireParameters());
        assertEquals("angus", testFilterByUser.getUserToFilter());
        assertEquals(testFilterByUser, configuratorTest.functionality);
     
    }
    
    @Test
    public void  testFilterByUser() throws Exception{
    		String[] args = {"../chat.txt", "../chat.json", "user=angus"};
        CommandLineArgumentParser argumentParserTest = new CommandLineArgumentParser();
        ConversationExporterConfiguration configuratorTest  = argumentParserTest.parseCommandLineArguments(args);
        FilterByUser testFilterByUser = new FilterByUser();
       
        testFilterByUser.processParameters("angus");
        assertEquals("angus", testFilterByUser.getUserToFilter());
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("../chat.json")), Conversation.class);
        
        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        
        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(false, testFilterByUser.applyFunctionality("bob"));
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(false, testFilterByUser.applyFunctionality("mike"));
        assertEquals(ms[1].content, "how are you?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(false, testFilterByUser.applyFunctionality("bob"));
        assertEquals(ms[2].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(false, testFilterByUser.applyFunctionality("mike"));
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(true, testFilterByUser.applyFunctionality("angus"));
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(false, testFilterByUser.applyFunctionality("bob"));
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(true, testFilterByUser.applyFunctionality("angus"));
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");

    }
    
    @Test
    public void  testFilterByKeyword() throws Exception{
    		String[] args = {"../chat.txt", "../chat.json", "keyword=pie"};
        CommandLineArgumentParser argumentParserTest = new CommandLineArgumentParser();
        ConversationExporterConfiguration configuratorTest  = argumentParserTest.parseCommandLineArguments(args);
        FilterByKeyword testFilterByKeyword = new FilterByKeyword();
       
        testFilterByKeyword.processParameters("pie");
        assertEquals("angus", testFilterByKeyword.getKeywordToFilter());
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("../chat.json")), Conversation.class);
        
        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        
        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(false, testFilterByKeyword.applyFunctionality("Hello there!"));

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        assertEquals(false, testFilterByKeyword.applyFunctionality("how are you?"));
        

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(true, testFilterByKeyword.applyFunctionality("I'm good thanks, do you like pie?"));

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(false, testFilterByKeyword.applyFunctionality("no, let me ask Angus..."));

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(true, testFilterByKeyword.applyFunctionality("Hell yes! Are we buying some pie?"));

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(true, testFilterByKeyword.applyFunctionality("No, just want to know if there's anybody else in the pie society..."));

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(true, testFilterByKeyword.applyFunctionality("YES! I'm the head pie eater there..."));

    }
    
    
    @Test
    public void  testBlacklist() throws Exception{
    	String[] args = {"../chat.txt", "../chat.json", "blacklist=pie"};
        CommandLineArgumentParser argumentParserTest = new CommandLineArgumentParser();
        ConversationExporterConfiguration configuratorTest  = argumentParserTest.parseCommandLineArguments(args);
        Blacklist testBlacklist = new Blacklist();
       
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("../chat.json")), Conversation.class);
        
        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        
        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        testBlacklist.applyFunctionality("Hello there!");
        assertEquals("Hello there!", testBlacklist.message);

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        testBlacklist.applyFunctionality("how are you?");
        assertEquals("how are you?", testBlacklist.message);
        

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        testBlacklist.applyFunctionality("I'm good thanks, do you like pie?");
        assertEquals("I'm good thanks, do you like *redacted*?", testBlacklist.message);

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        testBlacklist.applyFunctionality("no, let me ask Angus...");
        assertEquals("no, let me ask Angus...", testBlacklist.message);

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        testBlacklist.applyFunctionality("Hell yes! Are we buying some pie?");
        assertEquals("Hell yes! Are we buying some *redacted*?", testBlacklist.message); 

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        testBlacklist.applyFunctionality("No, just want to know if there's anybody else in the pie society...");
        assertEquals("No, just want to know if there's anybody else in the *redacted* society..." , testBlacklist.message);

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        testBlacklist.applyFunctionality("YES! I'm the head pie eater there...");
        assertEquals("YES! I'm the head *redacted* eater there...", testBlacklist.message);

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
