package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
   

	private String inputFilePath;
	private String outputFilePath;
	private String jsonTestFilePath;
	
	@Before
	public void setUp() {

		inputFilePath = "chat.txt";
		outputFilePath = "chat.json";
		jsonTestFilePath = "chat_test.json";
	}
	
	/**
     * Tests that reading the name of a conversation works correctly.
     * @throws Exception.
     * @result The name of the conversation has been read correctly.
     */
    @Test
    public void testExportingConversationReadingConversationName() throws Exception {
    	
    	ConversationExporter exporter = new ConversationExporter();
    	
        Conversation conversation = exporter.readConversation(inputFilePath);
        
        //tests the name of the conversation
        assertEquals("Conversation name is not correct!", "My Conversation", conversation.getName());
    }
	
    /**
     * Tests that reading the time stamp of the messages of a conversation works correctly.
     * @throws Exception When something bad happens.
     * @result The time stamp of the message has been read correctly.
     */
    @Test
    public void testExportingConversationReadingConversationTimeStamp() throws Exception {
    	
    	ConversationExporter exporter = new ConversationExporter();
    	
        Conversation conversation = exporter.readConversation(inputFilePath);
        
        //gets messages from conversation and converts to array
        Message[] messageArray = new Message[conversation.getMessages().size()];
        conversation.getMessages().toArray(messageArray);
        
        //tests the time stamp of the message
        assertEquals("Time stamp is not correct!", Instant.ofEpochSecond(1448470901), messageArray[0].getTimeStamp());
        //add more rows
    }
    
    /**
     * Tests that reading the sender ID of the messages of a conversation works correctly.
     * @throws Exception When something bad happens.
     * @result The sender ID of the message has been read correctly.
     */
    @Test
    public void testExportingConversationReadingConversationSenderId() throws Exception {
    	
    	ConversationExporter exporter = new ConversationExporter();
    	
        Conversation conversation = exporter.readConversation(inputFilePath);
        
        //gets messages from conversation and converts to array
        Message[] messageArray = new Message[conversation.getMessages().size()];
        conversation.getMessages().toArray(messageArray);
        
        //tests the sender ID of the message
        assertEquals("Sender ID is not correct!", "bob", messageArray[0].getSenderId());
        //add more rows
    }
    
    /**
     * Tests that reading the content of the messages of a conversation works correctly.
     * @throws Exception When something bad happens.
     * @result The content of the message has been read correctly.
     */
    @Test
    public void testExportingConversationReadingConversationContent() throws Exception {
    	
    	ConversationExporter exporter = new ConversationExporter();
    	
        Conversation conversation = exporter.readConversation(inputFilePath);
        
        //gets messages from conversation and converts to array
        Message[] messageArray = new Message[conversation.getMessages().size()];
        conversation.getMessages().toArray(messageArray);
        
        //tests the content of the message
        assertEquals("Content is not correct!", "Hello there!", messageArray[0].getContent());

    }
    
    
    /**
     * Tests that no messages are missing when read.
     * @throws Exception When something bad happens.
     * @result All the messages of the conversation have been read.
     */
    @Test
    public void testExportingConversationReadingAllConversationMessages() throws Exception {
    	
    	ConversationExporter exporter = new ConversationExporter();
    	
        Conversation conversation = exporter.readConversation(inputFilePath);
        
        //tests that all messages are included
        assertEquals("Number of messages is not correct!", 7, conversation.getMessages().size());
    }
	
    /**
     * Tests that writing a conversation to JSON file works correctly.
     * @throws Exception
     * @result The conversation has been written to JSON file correctly.
     */
    @Test
    public void testExportingConversationWritingConversation() throws Exception {

    	ConversationExporter exporter = new ConversationExporter();
    	
    	//initialize conversation and manually add variables
    	List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "testSenderId", "testContent"));
        Conversation conversation = new Conversation ("Test", messages);
        
        //write conversation to JSON
        exporter.writeConversation(conversation, outputFilePath);
        
        //read and compare json file
        String actualOutput = "";
        String expectedOutput = "";

        //read actual output and store in String
        try(InputStream is = new FileInputStream(outputFilePath);
        		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
        	
        	String actualLine;
	
            while ((actualLine = br.readLine()) != null) {
            	actualOutput += actualLine;        	
            }
        }	
        
        //read expected output and store in String
        try(InputStream is2 = new FileInputStream(jsonTestFilePath);
        		BufferedReader br2 = new BufferedReader(new InputStreamReader(is2, "UTF-8"))) {

           String expectedLine;

            while ((expectedLine = br2.readLine()) != null) {
            	expectedOutput += expectedLine;
            }
        }

        assertEquals("The JSON files are not identical!", actualOutput, expectedOutput);
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
