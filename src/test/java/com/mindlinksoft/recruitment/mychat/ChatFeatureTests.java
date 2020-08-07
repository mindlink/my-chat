package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChatFeatureTests 
{
	@Test(expected = IllegalArgumentException.class)
    public void testInvalidArgumentFormat() throws Exception 
    {
    	String[] args = {"chat.txt", "chat.json", "-b:abc,ad"};
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testInvalidFeature() throws Exception 
    {
    	String[] args = {"chat.txt", "chat.json", "-r"};
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void noArgumentSupplied() throws Exception 
    {
    	String[] args = {"chat.txt", "chat.json", "-b"};
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.features);
    }
	
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
}
