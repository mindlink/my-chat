package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws IOException Failed to read in or write file.
     */
    @Test
    public void testExportingConversationExportsConversation() throws IOException {
        ConversationExporter exporter = new ConversationExporter();

        String[] option = {"",""};
        String inputFilePath = "chat.txt";
        String outputFilePath = "chat.json";
        exporter.exportConversation(inputFilePath, outputFilePath, option);

        Conversation c = InstantDeserializer.createJsonDeserialized(outputFilePath);
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
    
    @Test
    public void testConversationExporterConfigurationAndArgumentParser() throws IOException {
        ConversationExporter exporter = new ConversationExporter();
        
        String[] option = {"",""};
        String inputFilePath = "chat.txt";
        String outputFilePath = "chat2.json";
        String[] args = {inputFilePath, outputFilePath, option[0], option[1]};
        ConversationExporterConfiguration config = new CommandLineArgumentParser().parseArguments(args);

        exporter.exportConversation(config.inputFilePath, config.outputFilePath, config.option);
    }
    
    @Test
    public void testWriteConversationThrowsFileNotFoundException() throws IOException {
    	ConversationExporter exporter = new ConversationExporter();
    	Conversation conversation = null;
    	String output = "";
		try {
			exporter.writeConversation(conversation, output);
		} catch (FileNotFoundException e) {
			assertEquals("The file '' was not found.", e.getMessage());
		}    	
    }
    
    @Test
    public void testReadConversationThrowsFileNotFoundException() throws IOException {
    	ConversationExporter exporter = new ConversationExporter();
    	String input = "topic.txt";
		try {
			exporter.readConversation(input);
		} catch (FileNotFoundException e) {
			assertEquals("The file 'topic.txt' was not found.", e.getMessage());
		}    	
    }
    
    @Test
    public void testMainNoOptionsReturnsNoErrors() throws IOException {
        String inputFilePath = "chat.txt";
        String outputFilePath = "chat_main.json";
    	String[] arguments = {inputFilePath, outputFilePath};
    	ConversationExporter.main(arguments);
    }
    
    @Test
    public void testMainWithOptionsReturnsNoErrors() throws IOException {
        String inputFilePath = "chat.txt";
        String outputFilePath = "chat_mainkey.json";
        String[] options = {"key", "are"};
    	String[] arguments = {inputFilePath, outputFilePath, options[0], options[1]};
    	ConversationExporter.main(arguments);
    }
    
    
    
    


}
