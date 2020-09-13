package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;

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
    	
    	//Test with no additonal parameters
        ConversationExporter exporter = new ConversationExporter();
        
        String[] arguments = {"chat.txt", "chat.json"};

        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(arguments);
        
        ArrayList<ArgumentData> packagedArguments = exporter.packageArguments(configuration.getFilterSettings(), configuration.getFilterValues());


        exporter.exportConversation(arguments[0], arguments[1], packagedArguments);

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
        
        System.out.println("----------");
    }   
        
//The following tests test for an incomplete chat log
//My theory with this is so long as the program doesnt crash when encountering a broken chat log it should be viable

    /**
     * Test with chat log in incorrect format
     * @throws Exception
     */
    @Test
    public void testExportingConversationIncorrectLog() throws Exception {
    	
    	ConversationExporter exporter = new ConversationExporter();
    	
        
        String[] argumentsIncorrectFormat = {"chatIncorrectFormat.txt", "chatIncorrectFormat.json"};

        ConversationExporterConfiguration configurationIncorrectFormat = new CommandLineArgumentParser().parseCommandLineArguments(argumentsIncorrectFormat);
        
        ArrayList<ArgumentData> packagedArgumentsIncorrectFormat = exporter.packageArguments(configurationIncorrectFormat.getFilterSettings(), configurationIncorrectFormat.getFilterValues());

        
        exporter.exportConversation(argumentsIncorrectFormat[0], argumentsIncorrectFormat[1], packagedArgumentsIncorrectFormat);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatIncorrectFormat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());
        
        System.out.println("----------");
    }
    
    /**
     * Test with chat missing a username
     * @throws Exception
     */
    @Test
    public void testExportingConversationMissingUsername() throws Exception {
    	
    	ConversationExporter exporter = new ConversationExporter();
        
        String[] argumentsWithoutUser = {"chatWithoutUser.txt", "chatWithoutUser.json"};

        ConversationExporterConfiguration configurationWithoutUser = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithoutUser);
        
        ArrayList<ArgumentData> packagedArgumentsWithoutUser = exporter.packageArguments(configurationWithoutUser.getFilterSettings(), configurationWithoutUser.getFilterValues());

        
        exporter.exportConversation(argumentsWithoutUser[0], argumentsWithoutUser[1], packagedArgumentsWithoutUser);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatWithoutUser.json")), Conversation.class);

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
        assertEquals(ms[3].senderId, "no,");
        assertEquals(ms[3].content, "let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
        
        System.out.println("----------");
    }
    
    /**
     * Test with chat missing a time
     * @throws Exception
     */
    @Test
    public void testExportingConversationMissingTime() throws Exception {
    	ConversationExporter exporter = new ConversationExporter();
        
        String[] argumentsWithoutTime = {"chatWithoutTime.txt", "chatWithoutTime.json"};

        ConversationExporterConfiguration configurationWithoutTime = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithoutTime);
        
        ArrayList<ArgumentData> packagedArgumentsWithoutTime = exporter.packageArguments(configurationWithoutTime.getFilterSettings(), configurationWithoutTime.getFilterValues());

        
        exporter.exportConversation(argumentsWithoutTime[0], argumentsWithoutTime[1], packagedArgumentsWithoutTime);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatWithoutTime.json")), Conversation.class);

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

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(0000000000));
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
        
        System.out.println("----------");   
    }
    
    /**
     * Test with chat missing a message
     * @throws Exception
     */
    @Test
    public void testExportingConversationMissingMessage() throws Exception {
    	ConversationExporter exporter = new ConversationExporter();
    	
        String[] argumentsWithoutMessage = {"chatWithoutMessage.txt", "chatWithoutMessage.json"};

        ConversationExporterConfiguration configurationWithoutMessage = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithoutMessage);
        
        ArrayList<ArgumentData> packagedArgumentsWithoutMessage = exporter.packageArguments(configurationWithoutMessage.getFilterSettings(), configurationWithoutMessage.getFilterValues());

        
        exporter.exportConversation(argumentsWithoutMessage[0], argumentsWithoutMessage[1], packagedArgumentsWithoutMessage);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatWithoutMessage.json")), Conversation.class);

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
        assertEquals(ms[3].content, "");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
        
        System.out.println("----------");
    }
    
    /**
     * Test with chat missing a name
     * @throws Exception
     */
    @Test
    public void testExportingConversationMissingName() throws Exception {
    	ConversationExporter exporter = new ConversationExporter();
    	
        String[] argumentsWithoutName = {"chatWithoutName.txt", "chatWithoutName.json"};

        ConversationExporterConfiguration configurationWithoutName = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithoutName);
        
        ArrayList<ArgumentData> packagedArgumentsWithoutName = exporter.packageArguments(configurationWithoutName.getFilterSettings(), configurationWithoutName.getFilterValues());

        
        exporter.exportConversation(argumentsWithoutName[0], argumentsWithoutName[1], packagedArgumentsWithoutName);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatWithoutName.json")), Conversation.class);

        assertEquals("1448470901 bob Hello there!", c.name);

        assertEquals(6, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[0].senderId, "mike");
        assertEquals(ms[0].content, "how are you?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[2].senderId, "mike");
        assertEquals(ms[2].content, "no, let me ask Angus...");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[3].senderId, "angus");
        assertEquals(ms[3].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[4].senderId, "bob");
        assertEquals(ms[4].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[5].senderId, "angus");
        assertEquals(ms[5].content, "YES! I'm the head pie eater there...");
        
        System.out.println("----------");
    }
    
    /**
     * Test filtering names by bob only
     * @throws Exception
     */
    @Test
    public void testExportingConversationBobUserFilter() throws Exception {

    	ConversationExporter exporter = new ConversationExporter();
        
        String[] argumentsWithBobFilter = {"chat.txt", "chatBob.json", "/fuser", "bob"};

        ConversationExporterConfiguration configurationBobFilter = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithBobFilter);
        
        ArrayList<ArgumentData> packagedArgumentsBobFilter = exporter.packageArguments(configurationBobFilter.getFilterSettings(), configurationBobFilter.getFilterValues());


        exporter.exportConversation(argumentsWithBobFilter[0], argumentsWithBobFilter[1], packagedArgumentsBobFilter);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatBob.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(3, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");
        
        System.out.println("----------");
    }
    
    /**
     * Test filtering messages by the pie keyword only
     * @throws Exception
     */
    @Test
    public void testExportingConversationPieKeywordFilter() throws Exception {

    	ConversationExporter exporter = new ConversationExporter();
        
        String[] argumentsWithPieFilter = {"chat.txt", "chatPie.json", "/fword", "pie"};

        ConversationExporterConfiguration configurationPieFilter = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithPieFilter);
        
        ArrayList<ArgumentData> packagedArgumentsPieFilter = exporter.packageArguments(configurationPieFilter.getFilterSettings(), configurationPieFilter.getFilterValues());

        
        exporter.exportConversation(argumentsWithPieFilter[0], argumentsWithPieFilter[1], packagedArgumentsPieFilter);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatPie.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(4, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[1].senderId, "angus");
        assertEquals(ms[1].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[3].senderId, "angus");
        assertEquals(ms[3].content, "YES! I'm the head pie eater there...");
        
        System.out.println("----------");
    }
    
    /**
     * Test filtering messages with "pie" redacted
     * @throws Exception
     */
    @Test
    public void testExportingConversationPieRedacted() throws Exception {

    	ConversationExporter exporter = new ConversationExporter();
        
        String[] argumentsWithPieRedacted = {"chat.txt", "chatRedactedPie.json", "/blist", "pie"};

        ConversationExporterConfiguration configurationPieRedacted = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithPieRedacted);
        
        ArrayList<ArgumentData> packagedArgumentsPieRedacted = exporter.packageArguments(configurationPieRedacted.getFilterSettings(), configurationPieRedacted.getFilterValues());

        
        exporter.exportConversation(argumentsWithPieRedacted[0], argumentsWithPieRedacted[1], packagedArgumentsPieRedacted);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatRedactedPie.json")), Conversation.class);

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
        assertEquals(ms[2].content, "I'm good thanks, do you like *REDACTED*?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some *REDACTED*?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the *REDACTED* society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head *REDACTED* eater there...");
        
        System.out.println("----------");
    }
    
    /**
     * Test filtering messages by the pie keyword and with pie redacted
     * @throws Exception
     */
    @Test
    public void testExportingConversationPieKeywordPieRedacted() throws Exception {

    	ConversationExporter exporter = new ConversationExporter();
        
        String[] argumentsWithPieFilterPieRedaction = {"chat.txt", "chatPieRedactedPie.json", "/fword", "pie", "/blist", "pie"};

        ConversationExporterConfiguration configurationPieFilterPieRedaction = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithPieFilterPieRedaction);
        
        ArrayList<ArgumentData> packagedArgumentsPieFilterPieRedaction = exporter.packageArguments(configurationPieFilterPieRedaction.getFilterSettings(), configurationPieFilterPieRedaction.getFilterValues());

        
        exporter.exportConversation(argumentsWithPieFilterPieRedaction[0], argumentsWithPieFilterPieRedaction[1], packagedArgumentsPieFilterPieRedaction);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatPieRedactedPie.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(4, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "I'm good thanks, do you like *REDACTED*?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[1].senderId, "angus");
        assertEquals(ms[1].content, "Hell yes! Are we buying some *REDACTED*?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the *REDACTED* society...");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[3].senderId, "angus");
        assertEquals(ms[3].content, "YES! I'm the head *REDACTED* eater there...");
        
        System.out.println("----------");
    }
    
    /**
     * Test filtering names by bob with the pie keyword and pie redacted
     * @throws Exception
     */
    @Test
    public void testExportingConversationBobUserFilterPieKeywordPieRedacted() throws Exception {

    	ConversationExporter exporter = new ConversationExporter();
        
        String[] argumentsWithPieFilterBobFilterPieRedaction = {"chat.txt", "chatPieBobRedactedPie.json", "/fword", "pie", "/blist", "pie", "/fuser", "bob"};

        ConversationExporterConfiguration configurationPieFilterBobFilterPieRedaction = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithPieFilterBobFilterPieRedaction);
        
        ArrayList<ArgumentData> packagedArgumentsPieFilterBobFilterPieRedaction = exporter.packageArguments(configurationPieFilterBobFilterPieRedaction.getFilterSettings(), configurationPieFilterBobFilterPieRedaction.getFilterValues());

        
        exporter.exportConversation(argumentsWithPieFilterBobFilterPieRedaction[0], argumentsWithPieFilterBobFilterPieRedaction[1], packagedArgumentsPieFilterBobFilterPieRedaction);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatPieBobRedactedPie.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(2, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "I'm good thanks, do you like *REDACTED*?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "No, just want to know if there's anybody else in the *REDACTED* society...");
        
        System.out.println("----------");
    }
    
    /**
     * Test same as above in different order
     * @throws Exception
     */
    @Test
    public void testExportingConversationArgumentOrder() throws Exception {

    	ConversationExporter exporter = new ConversationExporter();
        
        String[] argumentsWithPieFilterBobFilterPieRedactionOrderChange = {"chat.txt", "chatPieBobRedactedPieOrderChange.json", "/fuser", "bob", "/fword", "pie", "/blist", "pie"};

        ConversationExporterConfiguration configurationPieFilterBobFilterPieRedactionOrderChange = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithPieFilterBobFilterPieRedactionOrderChange);
        
        ArrayList<ArgumentData> packagedArgumentsPieFilterBobFilterPieRedactionOrderChange = exporter.packageArguments(configurationPieFilterBobFilterPieRedactionOrderChange.getFilterSettings(), configurationPieFilterBobFilterPieRedactionOrderChange.getFilterValues());

        
        exporter.exportConversation(argumentsWithPieFilterBobFilterPieRedactionOrderChange[0], argumentsWithPieFilterBobFilterPieRedactionOrderChange[1], packagedArgumentsPieFilterBobFilterPieRedactionOrderChange);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatPieBobRedactedPieOrderChange.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(2, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "I'm good thanks, do you like *REDACTED*?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "No, just want to know if there's anybody else in the *REDACTED* society...");
        
        System.out.println("----------");
    }
    
    /**
     * Test filtering names by bob, pie keyword with "pie" and "thanks" redacted
     * @throws Exception
     */
    @Test
    public void testExportingConversationBobUserFilterPieKeywordPieThanksRedacted() throws Exception {

    	ConversationExporter exporter = new ConversationExporter();
        
        String[] argumentsWithPieFilterBobFilterPieThanksRedaction = {"chat.txt", "chatPieBobRedactedPieThanks.json", "/fword", "pie", "/blist", "pie", "thanks", "/fuser", "bob"};

        ConversationExporterConfiguration configurationPieFilterBobFilterPieThanksRedaction = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithPieFilterBobFilterPieThanksRedaction);
        
        ArrayList<ArgumentData> packagedArgumentsPieFilterBobFilterPieThanksRedaction = exporter.packageArguments(configurationPieFilterBobFilterPieThanksRedaction.getFilterSettings(), configurationPieFilterBobFilterPieThanksRedaction.getFilterValues());

        
        exporter.exportConversation(argumentsWithPieFilterBobFilterPieThanksRedaction[0], argumentsWithPieFilterBobFilterPieThanksRedaction[1], packagedArgumentsPieFilterBobFilterPieThanksRedaction);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatPieBobRedactedPieThanks.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(2, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "I'm good *REDACTED*, do you like *REDACTED*?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "No, just want to know if there's anybody else in the *REDACTED* society...");
        
        System.out.println("----------");
    }
    
    /**
     * Test filtering with invlaid filter
     * @throws Exception
     */
    @Test
    public void testExportingConversationInvalidFilter() throws Exception {

    	ConversationExporter exporter = new ConversationExporter();
        
        String[] argumentsWithPieFilterBobFilterPieRedactionInvalid = {"chat.txt", "chatPieBobRedactedPieInvalid.json", "/fword", "pie", "/invalid", "/blist", "pie", "/fuser", "bob"};

        ConversationExporterConfiguration configurationPieFilterBobFilterPieRedactionInvalid = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithPieFilterBobFilterPieRedactionInvalid);
        
        ArrayList<ArgumentData> packagedArgumentsPieFilterBobFilterPieRedactionInvalid = exporter.packageArguments(configurationPieFilterBobFilterPieRedactionInvalid.getFilterSettings(), configurationPieFilterBobFilterPieRedactionInvalid.getFilterValues());

        
        exporter.exportConversation(argumentsWithPieFilterBobFilterPieRedactionInvalid[0], argumentsWithPieFilterBobFilterPieRedactionInvalid[1], packagedArgumentsPieFilterBobFilterPieRedactionInvalid);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatPieBobRedactedPieInvalid.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(2, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "I'm good thanks, do you like *REDACTED*?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "No, just want to know if there's anybody else in the *REDACTED* society...");
        
        System.out.println("----------");
    }
    
    /**
     * Test filtering message by pie keyword, pie redacted and a blank user argument
     * @throws Exception
     */
    @Test
    public void testExportingConversationPieKeywordPieRedactedBlankUserFilter() throws Exception {

    	ConversationExporter exporter = new ConversationExporter();
        
        String[] argumentsWithPieFilterEmptyUserPieRedaction = {"chat.txt", "chatPieEmptyRedactedPie.json", "/fword", "pie", "/blist", "pie", "/fuser"};

        ConversationExporterConfiguration configurationPieFilterEmptyUserPieRedaction = new CommandLineArgumentParser().parseCommandLineArguments(argumentsWithPieFilterEmptyUserPieRedaction);
        
        ArrayList<ArgumentData> packagedArgumentsPieFilterEmptyUserPieRedaction = exporter.packageArguments(configurationPieFilterEmptyUserPieRedaction.getFilterSettings(), configurationPieFilterEmptyUserPieRedaction.getFilterValues());

        
        exporter.exportConversation(argumentsWithPieFilterEmptyUserPieRedaction[0], argumentsWithPieFilterEmptyUserPieRedaction[1], packagedArgumentsPieFilterEmptyUserPieRedaction);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chatPieEmptyRedactedPie.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(4, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "I'm good thanks, do you like *REDACTED*?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[1].senderId, "angus");
        assertEquals(ms[1].content, "Hell yes! Are we buying some *REDACTED*?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the *REDACTED* society...");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[3].senderId, "angus");
        assertEquals(ms[3].content, "YES! I'm the head *REDACTED* eater there...");
        
        System.out.println("----------");
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
