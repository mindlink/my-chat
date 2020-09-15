package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.constructs.Conversation;
import com.mindlinksoft.recruitment.mychat.constructs.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.constructs.Message;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link CreateGsonBuild}.
 */
public class CreateGsonBuildTests
{
    private ConversationExporterConfiguration config;

    @Before
    public void setUp()
    {
        config = new ConversationExporterConfiguration("", "");
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testConversionJsonToString() throws Exception
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1440010000), "jeremy", "This is the best thing, in the world there..."));
        messages.add(new Message(Instant.ofEpochSecond(1440010006), "richard", "I've crashed!"));
        messages.add(new Message(Instant.ofEpochSecond(1440010012), "james", "And now... 25 mph! Wow that's quick."));
        Conversation conversation = new Conversation("Test Conversation", messages);

        CreateGsonBuild createGsonBuild = new CreateGsonBuild(conversation, config);
        String jsonStringExpected = "{\"name\":\"Test Conversation\",\"messages\":[{\"timestamp\":1440010000,\"senderId\":\"jeremy\",\"content\":\"This is the best thing, in the world there...\"},{\"timestamp\":1440010006,\"senderId\":\"richard\",\"content\":\"I\\u0027ve crashed!\"},{\"timestamp\":1440010012,\"senderId\":\"james\",\"content\":\"And now... 25 mph! Wow that\\u0027s quick.\"}]}";
        assertEquals(jsonStringExpected, createGsonBuild.convert());
    }

    @Test
    public void testConversionJsonToString_obfuscate_1() throws Exception
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1440010000), "jeremy", "This is the best thing, in the world there..."));
        messages.add(new Message(Instant.ofEpochSecond(1440010006), "richard", "I've crashed!"));
        messages.add(new Message(Instant.ofEpochSecond(1440010012), "james", "And now... 25 mph! Wow that's quick."));
        messages.add(new Message(Instant.ofEpochSecond(1440010015), "richard", "James, that's really slow"));
        messages.add(new Message(Instant.ofEpochSecond(1440010019), "jeremy", "Captain slow, living up to his name!"));
        Conversation conversation = new Conversation("Test Conversation", messages);

        config.setObf(true);

        CreateGsonBuild c = new CreateGsonBuild(conversation, config);
        String jsonString = c.convert();
        String jsonStringExpected = "{\"name\":\"Test Conversation\"," +
                "\"messages\":[" +
                "{\"timestamp\":1440010000,\"senderId\":\"" + c.getObfMappings().get("jeremy") + "\",\"content\":\"This is the best thing, in the world there...\"}," +
                "{\"timestamp\":1440010006,\"senderId\":\"" + c.getObfMappings().get("richard") + "\",\"content\":\"I\\u0027ve crashed!\"}," +
                "{\"timestamp\":1440010012,\"senderId\":\"" + c.getObfMappings().get("james") + "\",\"content\":\"And now... 25 mph! Wow that\\u0027s quick.\"}," +
                "{\"timestamp\":1440010015,\"senderId\":\"" + c.getObfMappings().get("richard") + "\",\"content\":\"James, that\\u0027s really slow\"}," +
                "{\"timestamp\":1440010019,\"senderId\":\"" + c.getObfMappings().get("jeremy") + "\",\"content\":\"Captain slow, living up to his name!\"}" +
                "]}";
        assertEquals(jsonStringExpected, jsonString);

        String obfFile = FileUtils.readFileToString(new File(config.getOBF_FILE_PATH()), "utf-8");
        String obfExpected = "Obfuscated User Mappings:\n" +
                "1) senderID: james -> generatedID: " + c.getObfMappings().get("james") + "\n" +
                "2) senderID: jeremy -> generatedID: " + c.getObfMappings().get("jeremy") + "\n" +
                "3) senderID: richard -> generatedID: " + c.getObfMappings().get("richard") + "\n";
        assertEquals(obfExpected, obfFile);
    }

    @Test
    public void testConversionJsonToString_obfuscate_2() throws Exception
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1440010000), "charlie", "I'm in the chat!"));
        messages.add(new Message(Instant.ofEpochSecond(1440010006), "amber", "Hello there, I'm here too"));
        messages.add(new Message(Instant.ofEpochSecond(1440010012), "becky", "And snap, hiya..."));
        messages.add(new Message(Instant.ofEpochSecond(1440010015), "amber", "Now we're all here, lets begin!"));
        Conversation conversation = new Conversation("Test Conversation", messages);

        config.setObf(true);

        CreateGsonBuild c = new CreateGsonBuild(conversation, config);
        String jsonString = c.convert();
        String jsonStringExpected = "{\"name\":\"Test Conversation\"," +
                "\"messages\":[" +
                "{\"timestamp\":1440010000,\"senderId\":\"" + c.getObfMappings().get("charlie") + "\",\"content\":\"I\\u0027m in the chat!\"}," +
                "{\"timestamp\":1440010006,\"senderId\":\"" + c.getObfMappings().get("amber") + "\",\"content\":\"Hello there, I\\u0027m here too\"}," +
                "{\"timestamp\":1440010012,\"senderId\":\"" + c.getObfMappings().get("becky") + "\",\"content\":\"And snap, hiya...\"}," +
                "{\"timestamp\":1440010015,\"senderId\":\"" + c.getObfMappings().get("amber") + "\",\"content\":\"Now we\\u0027re all here, lets begin!\"}" +
                "]}";
        assertEquals(jsonStringExpected, jsonString);

        String obfFile = FileUtils.readFileToString(new File(config.getOBF_FILE_PATH()), "utf-8");
        String obfExpected = "Obfuscated User Mappings:\n" +
                "1) senderID: amber -> generatedID: " + c.getObfMappings().get("amber") + "\n" +
                "2) senderID: becky -> generatedID: " + c.getObfMappings().get("becky") + "\n" +
                "3) senderID: charlie -> generatedID: " + c.getObfMappings().get("charlie") + "\n";
        assertEquals(obfExpected, obfFile);
    }

    @Test
    public void testConversionJsonToString_report_1() throws Exception
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1440010000), "jeremy", "This is the best thing, in the world there..."));
        messages.add(new Message(Instant.ofEpochSecond(1440010006), "richard", "I've crashed!"));
        messages.add(new Message(Instant.ofEpochSecond(1440010012), "james", "And now... 25 mph! Wow that's quick."));
        messages.add(new Message(Instant.ofEpochSecond(1440010015), "richard", "James, that's really slow"));
        messages.add(new Message(Instant.ofEpochSecond(1440010019), "jeremy", "Captain slow, living up to his name!"));
        Conversation conversation = new Conversation("Test Conversation", messages);

        config.setReport(true);

        CreateGsonBuild c = new CreateGsonBuild(conversation, config);
        String jsonString = c.convert();
        String jsonStringExpected = "{\"name\":\"Test Conversation\"," +
                "\"messages\":[" +
                "{\"timestamp\":1440010000,\"senderId\":\"jeremy\",\"content\":\"This is the best thing, in the world there...\"}," +
                "{\"timestamp\":1440010006,\"senderId\":\"richard\",\"content\":\"I\\u0027ve crashed!\"}," +
                "{\"timestamp\":1440010012,\"senderId\":\"james\",\"content\":\"And now... 25 mph! Wow that\\u0027s quick.\"}," +
                "{\"timestamp\":1440010015,\"senderId\":\"richard\",\"content\":\"James, that\\u0027s really slow\"}," +
                "{\"timestamp\":1440010019,\"senderId\":\"jeremy\",\"content\":\"Captain slow, living up to his name!\"}" +
                "]," +
                "\"userReport\":[" +
                "{\"senderId\":\"richard\",\"messageCount\":2}," +
                "{\"senderId\":\"jeremy\",\"messageCount\":2}," +
                "{\"senderId\":\"james\",\"messageCount\":1}" +
                "]}";
        assertEquals(jsonStringExpected, jsonString);
    }

    @Test
    public void testConversionJsonToString_report_2() throws Exception
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1440010000), "charlie", "I'm in the chat!"));
        messages.add(new Message(Instant.ofEpochSecond(1440010006), "amber", "Hello there, I'm here too"));
        messages.add(new Message(Instant.ofEpochSecond(1440010012), "becky", "And snap, hiya..."));
        messages.add(new Message(Instant.ofEpochSecond(1440010015), "amber", "Now we're all here, lets begin!"));
        Conversation conversation = new Conversation("Test Conversation", messages);

        config.setReport(true);

        CreateGsonBuild c = new CreateGsonBuild(conversation, config);
        String jsonString = c.convert();
        String jsonStringExpected = "{\"name\":\"Test Conversation\",\"messages\":[{" +
                "\"timestamp\":1440010000,\"senderId\":\"charlie\",\"content\":\"I\\u0027m in the chat!\"}," +
                "{\"timestamp\":1440010006,\"senderId\":\"amber\",\"content\":\"Hello there, I\\u0027m here too\"}," +
                "{\"timestamp\":1440010012,\"senderId\":\"becky\",\"content\":\"And snap, hiya...\"}," +
                "{\"timestamp\":1440010015,\"senderId\":\"amber\",\"content\":\"Now we\\u0027re all here, lets begin!\"}" +
                "]," +
                "\"userReport\":[" +
                "{\"senderId\":\"amber\",\"messageCount\":2}," +
                "{\"senderId\":\"becky\",\"messageCount\":1}," +
                "{\"senderId\":\"charlie\",\"messageCount\":1}" +
                "]}";
        assertEquals(jsonStringExpected, jsonString);
    }

    @Test
    public void testConversionJsonToString_obfuscate_report_1() throws Exception
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1440010000), "jeremy", "This is the best thing, in the world there..."));
        messages.add(new Message(Instant.ofEpochSecond(1440010006), "richard", "I've crashed!"));
        messages.add(new Message(Instant.ofEpochSecond(1440010012), "james", "And now... 25 mph! Wow that's quick."));
        messages.add(new Message(Instant.ofEpochSecond(1440010015), "richard", "James, that's really slow"));
        messages.add(new Message(Instant.ofEpochSecond(1440010019), "jeremy", "Captain slow, living up to his name!"));
        Conversation conversation = new Conversation("Test Conversation", messages);

        config.setObf(true);
        config.setReport(true);

        CreateGsonBuild c = new CreateGsonBuild(conversation, config);
        String jsonString = c.convert();
        String jsonStringExpected = "{\"name\":\"Test Conversation\"," +
                "\"messages\":[" +
                "{\"timestamp\":1440010000,\"senderId\":\"" + c.getObfMappings().get("jeremy") + "\",\"content\":\"This is the best thing, in the world there...\"}," +
                "{\"timestamp\":1440010006,\"senderId\":\"" + c.getObfMappings().get("richard") + "\",\"content\":\"I\\u0027ve crashed!\"}," +
                "{\"timestamp\":1440010012,\"senderId\":\"" + c.getObfMappings().get("james") + "\",\"content\":\"And now... 25 mph! Wow that\\u0027s quick.\"}," +
                "{\"timestamp\":1440010015,\"senderId\":\"" + c.getObfMappings().get("richard") + "\",\"content\":\"James, that\\u0027s really slow\"}," +
                "{\"timestamp\":1440010019,\"senderId\":\"" + c.getObfMappings().get("jeremy") + "\",\"content\":\"Captain slow, living up to his name!\"}" +
                "]," +
                "\"userReport\":[" +
                "{\"senderId\":\"" + c.getObfMappings().get("richard") + "\",\"messageCount\":2}," +
                "{\"senderId\":\"" + c.getObfMappings().get("jeremy") + "\",\"messageCount\":2}," +
                "{\"senderId\":\"" + c.getObfMappings().get("james") + "\",\"messageCount\":1}" +
                "]}";
        assertEquals(jsonStringExpected, jsonString);

        String obfFile = FileUtils.readFileToString(new File(config.getOBF_FILE_PATH()), "utf-8");
        String obfExpected = "Obfuscated User Mappings:\n" +
                "1) senderID: james -> generatedID: " + c.getObfMappings().get("james") + "\n" +
                "2) senderID: jeremy -> generatedID: " + c.getObfMappings().get("jeremy") + "\n" +
                "3) senderID: richard -> generatedID: " + c.getObfMappings().get("richard") + "\n";
        assertEquals(obfExpected, obfFile);
    }

    @Test
    public void testConversionJsonToString_obfuscate_report_2() throws Exception
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1440010000), "charlie", "I'm in the chat!"));
        messages.add(new Message(Instant.ofEpochSecond(1440010006), "amber", "Hello there, I'm here too"));
        messages.add(new Message(Instant.ofEpochSecond(1440010012), "becky", "And snap, hiya..."));
        messages.add(new Message(Instant.ofEpochSecond(1440010015), "amber", "Now we're all here, lets begin!"));
        Conversation conversation = new Conversation("Test Conversation", messages);

        config.setObf(true);
        config.setReport(true);

        CreateGsonBuild c = new CreateGsonBuild(conversation, config);
        String jsonString = c.convert();
        String jsonStringExpected = "{\"name\":\"Test Conversation\"," +
                "\"messages\":[" +
                "{\"timestamp\":1440010000,\"senderId\":\"" + c.getObfMappings().get("charlie") + "\",\"content\":\"I\\u0027m in the chat!\"}," +
                "{\"timestamp\":1440010006,\"senderId\":\"" + c.getObfMappings().get("amber") + "\",\"content\":\"Hello there, I\\u0027m here too\"}," +
                "{\"timestamp\":1440010012,\"senderId\":\"" + c.getObfMappings().get("becky") + "\",\"content\":\"And snap, hiya...\"}," +
                "{\"timestamp\":1440010015,\"senderId\":\"" + c.getObfMappings().get("amber") + "\",\"content\":\"Now we\\u0027re all here, lets begin!\"}" +
                "]," +
                "\"userReport\":[" +
                "{\"senderId\":\"" + c.getObfMappings().get("amber") + "\",\"messageCount\":2}," +
                "{\"senderId\":\"" + c.getObfMappings().get("becky") + "\",\"messageCount\":1}," +
                "{\"senderId\":\"" + c.getObfMappings().get("charlie") + "\",\"messageCount\":1}" +
                "]}";
        assertEquals(jsonStringExpected, jsonString);

        String obfFile = FileUtils.readFileToString(new File(config.getOBF_FILE_PATH()), "utf-8");
        String obfExpected = "Obfuscated User Mappings:\n" +
                "1) senderID: amber -> generatedID: " + c.getObfMappings().get("amber") + "\n" +
                "2) senderID: becky -> generatedID: " + c.getObfMappings().get("becky") + "\n" +
                "3) senderID: charlie -> generatedID: " + c.getObfMappings().get("charlie") + "\n";
        assertEquals(obfExpected, obfFile);
    }
}
