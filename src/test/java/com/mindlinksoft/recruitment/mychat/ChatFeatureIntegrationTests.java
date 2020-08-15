package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Map;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;
import com.mindlinksoft.recruitment.mychat.utils.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.utils.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.utils.JSONConverter;

/**
 * Class to test the various features implemented as part of the chat
 * TODO: Update references to comply with static method changes
 *
 */
public class ChatFeatureIntegrationTests 
{
	/**
	 * Tests whether IllegalArgumentException is thrown when arguments for flags are provided using something other than '='
	 */
	@Test(expected = IllegalArgumentException.class)
    public void testInvalidArgumentFormat() throws Exception 
    {
    	String[] args = {"chat.txt", "chat.json", "-b:abc,ad"};
        
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        ConversationExporter.exportConversation(configuration);
    }
	
	/**
	 * Tests whether IllegalArgumentException is thrown when invalid flags are used
	 */
	@Test(expected = IllegalArgumentException.class)
    public void testInvalidFeature() throws Exception 
    {
    	String[] args = {"chat.txt", "chat.json", "-r"};
    	
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        ConversationExporter.exportConversation(configuration);
    }
	
	/**
	 * Tests whether IllegalArgumentException is thrown when flag is provided without arguments when one is required 
	 */
	@Test(expected = IllegalArgumentException.class)
    public void noArgumentSupplied() throws Exception 
    {
    	String[] args = {"chat.txt", "chat.json", "-b"};
    	
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        ConversationExporter.exportConversation(configuration);
    }
	
	/**
	 * Tests whether a single blacklisted word is redacted
	 */
	@Test
    public void blacklistSingleWordTest() throws Exception 
    {
		String[] args = {"chat.txt", "chat.json", "-b=pie"};
    	
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        ConversationExporter.exportConversation(configuration);

        Conversation c = JSONConverter.convertJSONToConversation(configuration.outputFilePath);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        for(Message m : ms)
        {
        	assertTrue(!m.content.contains("pie"));
        }
    }
	
	/**
	 * Tests whether a list of blacklisted words are redacted
	 */
	@Test
    public void blacklistMultiWordTest() throws Exception 
    {
		String[] args = {"chat.txt", "chat.json", "-b=Hello,pie,yes"};
    	
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        ConversationExporter.exportConversation(configuration);

        Conversation c = JSONConverter.convertJSONToConversation(configuration.outputFilePath);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        for(Message m : ms)
        {
        	assertTrue(!m.content.contains("Hello"));
        	assertTrue(!m.content.contains("pie"));
        	assertTrue(!m.content.contains("yes"));
        }
    }
	
	/**
	 * Tests whether the keyword filter works by checking all messages in the conversation have the specified keyword
	 */
	@Test
    public void keywordFilterTest() throws Exception 
    {
		String[] args = {"chat.txt", "chat.json", "-k=pie"};
    	
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        ConversationExporter.exportConversation(configuration);

        Conversation c = JSONConverter.convertJSONToConversation(configuration.outputFilePath);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        for(Message m : ms)
        {
        	assertTrue(m.content.contains("pie"));
        }
    }
	
	/**
	 * Checks the user filter works by checking all messages have the same specified user id
	 */
	@Test
    public void userFilterTest() throws Exception 
    {
		String[] args = {"chat.txt", "chat.json", "-u=bob"};
    	
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        ConversationExporter.exportConversation(configuration);

        Conversation c = JSONConverter.convertJSONToConversation(configuration.outputFilePath);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        for(Message m : ms)
        {
        	assertTrue(m.senderId.equals("bob"));
        }
    }
	
	/**
	 * Tests whether the given phone numbers and card numbers are redacted by checking it no longer appears in the messages
	 */
	@Test
	public void hideNumberTest() throws Exception 
    {
		String[] args = {"chat.txt", "chat.json", "-h"};
    	
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        ConversationExporter.exportConversation(configuration);

        Conversation c = JSONConverter.convertJSONToConversation(configuration.outputFilePath);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);
        
        String hiddenNumber1 = "01234567891";
        String hiddenNumber2 = "077-305-37564";
        String hiddenNumber3 = "1234567891234567";
        String hiddenNumber4 = "1111-1111-1111-1111";

        for(Message m : ms)
        {
        	assertTrue(!m.content.contains(hiddenNumber1));
        	assertTrue(!m.content.contains(hiddenNumber2));
        	assertTrue(!m.content.contains(hiddenNumber3));
        	assertTrue(!m.content.contains(hiddenNumber4));
        }
    }
	
	/**
	 * Tests the user activity report at the end of the JSON file
	 */
	@Test
	public void userActivityTest() throws Exception 
    {
		String[] args = {"chat.txt", "chat.json", "-h"};
    	
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        ConversationExporter.exportConversation(configuration);

        Conversation c = JSONConverter.convertJSONToConversation(configuration.outputFilePath);

        Map<String, Integer> ua = c.userActivity;

        assertEquals((int)ua.get("bob"), 3);
        assertEquals((int)ua.get("angus"), 2);
        assertEquals((int)ua.get("mike"), 2);
    }
	
	/**
	 * Tests immutability by reading in conversation, applying features and checking original conversation is unchanged
	 */
	@Test
	public void testImmutability() throws Exception
	{
		String[] args = {"chat.txt", "chat.json", "-h -u=bob"};
    	
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        Conversation original_convo = ConversationExporter.readConversation(configuration.inputFilePath);
        
        ConversationExporter.applyFeatures(configuration, original_convo);

        assertEquals(7, original_convo.messages.size());

        Message[] ms = new Message[original_convo.messages.size()];
        original_convo.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        assertEquals(ms[1].content, "how are you? My phone number is  01234567891");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "I'm good thanks, do you like pie? Okay mine is 077-305-37564");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus... I'll pay with my card 1234567891234567");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie? I'll split it, my card is 1111-1111-1111-1111");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
	}
}
