package com.mindlinksoft.recruitment.mychat;

import com.beust.jcommander.ParameterException;
import com.mindlinksoft.recruitment.mychat.JSONHandler;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Instant;
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
        String[] args = new String[]{"-i", "chat.txt", "-o", "chat.json"};

        CommandLineArgumentParser configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getArguments());

        Conversation conversation = JSONHandler.fromJSON(new FileInputStream(configuration.getOutputFilePath()));

        assertEquals("My Conversation", conversation.name);

        assertEquals(7, conversation.messages.size());

        Message[] messages = new Message[conversation.messages.size()];
        conversation.messages.toArray(messages);

        assertEquals(messages[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(messages[0].senderId, "bob");
        assertEquals(messages[0].content, "Hello there!");

        assertEquals(messages[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(messages[1].senderId, "mike");
        assertEquals(messages[1].content, "how are you?");

        assertEquals(messages[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(messages[2].senderId, "bob");
        assertEquals(messages[2].content, "I'm good thanks, do you like pie?");

        assertEquals(messages[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(messages[3].senderId, "mike");
        assertEquals(messages[3].content, "no, let me ask Angus...");

        assertEquals(messages[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(messages[4].senderId, "angus");
        assertEquals(messages[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(messages[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(messages[5].senderId, "bob");
        assertEquals(messages[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(messages[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(messages[6].senderId, "angus");
        assertEquals(messages[6].content, "YES! I'm the head pie eater there...");
    }

    @Test(expected = FileNotFoundException.class)
    public void testNoInputFile() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"-i", "fakechat.txt", "-o", "chat.json"};

        CommandLineArgumentParser configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getArguments());
    }

    @Test(expected = FileNotFoundException.class)
    public void testNoOutputFile() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"-i", "chat.txt", "-o", "fakefolder/fakechat.json"};

        CommandLineArgumentParser configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getArguments());
    }
    @Test(expected = ParameterException.class)
    public void testIncorrectArguments() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"-i", "chat.txt", "-o", "chat.json", "-fakeoption"};
        CommandLineArgumentParser configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
    }

    @Test
    public void testFilterKeyword() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"-i", "chat.txt", "-o", "chat.json", "-fk", "pie"};

        CommandLineArgumentParser configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getArguments());

        Conversation conversation = JSONHandler.fromJSON(new FileInputStream(configuration.getOutputFilePath()));

        for(Message message : conversation.messages){
            assertTrue(message.content.contains("pie"));
        }
    }

    @Test
    public void testFilterUser() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"-i", "chat.txt", "-o", "chat.json", "-fu", "bob"};

        CommandLineArgumentParser configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getArguments());

        Conversation conversation = JSONHandler.fromJSON(new FileInputStream(configuration.getOutputFilePath()));

        for(Message message : conversation.messages){
            assertTrue(message.senderId.contains("bob"));
        }
    }

    @Test
    public void testSanitizeBlacklist() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"-i", "chatPhoneCardFilter.txt", "-o", "chat.json", "-sbl", "pie"};

        CommandLineArgumentParser configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getArguments());

        Conversation conversation = JSONHandler.fromJSON(new FileInputStream(configuration.getOutputFilePath()));

        for(Message message : conversation.messages){
            assertFalse(message.content.contains("pie"));
        }
    }

    @Test
    public void testSanitizePhoneCardNumber() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"-i", "chatPhoneCardFilter.txt", "-o", "chatPhoneCardFilter.json", "-spc"};

        CommandLineArgumentParser configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getArguments());

        Conversation conversation = JSONHandler.fromJSON(new FileInputStream(configuration.getOutputFilePath()));

        for(Message message : conversation.messages){
            assertFalse(message.content.contains("4444333322221111"));
            assertFalse(message.content.contains("34343434343434"));
            assertFalse(message.content.contains("+447807728103"));
            assertFalse(message.content.contains("+447222555555"));
        }

    }

    @Test
    public void testSanitizeUserID() throws Exception{
        //plaintext conversation
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"-i", "chat.txt", "-o", "chat.json"};

        CommandLineArgumentParser configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(),configuration.getOutputFilePath(),configuration.getArguments());

        Conversation conversation = JSONHandler.fromJSON(new FileInputStream(configuration.getOutputFilePath()));

        Message[] plaintextMessages = new Message[conversation.messages.size()]; conversation.messages.toArray(plaintextMessages);

        //sanitized conversation

        exporter = new ConversationExporter();
        args = new String[]{"-i", "chat.txt", "-o", "chat.json", "-suid"};

        configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(),configuration.getOutputFilePath(),configuration.getArguments());

        conversation = JSONHandler.fromJSON(new FileInputStream(configuration.getOutputFilePath()));

        Message[] sanitizedMessages = new Message[conversation.messages.size()]; conversation.messages.toArray(sanitizedMessages);

        HashMap<String, String> plaintextToSanitizedMap = new HashMap<>();

        for (int i = 0; i < conversation.messages.size(); i++){
            assertEquals(plaintextMessages[i].content, sanitizedMessages[i].content);
            if (plaintextToSanitizedMap.containsKey(sanitizedMessages[i].senderId)){
                assertEquals(plaintextToSanitizedMap.get(sanitizedMessages[i].senderId), plaintextMessages[i].senderId);
            } else {
                plaintextToSanitizedMap.put(sanitizedMessages[i].senderId, plaintextMessages[i].senderId);
            }
        }
    }

    @Test
    public void testUserActivityReport() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"-i", "chat.txt", "-o", "chat.json", "-uar"};

        CommandLineArgumentParser configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getArguments());

        Conversation conversation = JSONHandler.fromJSON(new FileInputStream(configuration.getOutputFilePath()));

        Map<String,Integer> userActivity = conversation.userMessagesCount;

        assertEquals((long)userActivity.get("mike"), 2 ) ;
        assertEquals((long)userActivity.get("angus"), 2 ) ;
        assertEquals((long)userActivity.get("bob"), 3 ) ;

    }

}
