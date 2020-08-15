package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

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
 *
 */
public class ChatFeatureTests 
{
	/**
	 * Tests whether IllegalArgumentException is thrown when arguments for flags are provided using something other than '='
	 */
	@Test(expected = IllegalArgumentException.class)
    public void testInvalidArgumentFormat() throws Exception 
    {
    	String[] args = {"chat.txt", "chat.json", "-b:abc,ad"};
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);
    }
	
	/**
	 * Tests whether IllegalArgumentException is thrown when invalid flags are used
	 */
	@Test(expected = IllegalArgumentException.class)
    public void testInvalidFeature() throws Exception 
    {
    	String[] args = {"chat.txt", "chat.json", "-r"};
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);
    }
	
	/**
	 * Tests whether IllegalArgumentException is thrown when flag is provided without arguments when one is required 
	 */
	@Test(expected = IllegalArgumentException.class)
    public void noArgumentSupplied() throws Exception 
    {
    	String[] args = {"chat.txt", "chat.json", "-b"};
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);
    }
	
	/**
	 * Tests whether a single blacklisted word is redacted
	 */
	@Test
    public void blacklistSingleWordTest() throws Exception 
    {
		String[] args = {"chat.txt", "chat.json", "-b=pie"};
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);

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
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);

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
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);

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
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);

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
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);

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
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);

        Conversation c = JSONConverter.convertJSONToConversation(configuration.outputFilePath);

        Map<String, Integer> ua = c.userActivity;

        assertEquals((int)ua.get("bob"), 3);
        assertEquals((int)ua.get("angus"), 2);
        assertEquals((int)ua.get("mike"), 2);
    }
}
