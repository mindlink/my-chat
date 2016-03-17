package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.exceptions.ReadConversationException;
import com.mindlinksoft.recruitment.mychat.exceptions.WriteConversationException;
import com.mindlinksoft.recruitment.mychat.helpers.TestConversationHelper;
import com.mindlinksoft.recruitment.mychat.helpers.TestFileHelper;
import com.mindlinksoft.recruitment.mychat.models.ConfigurationOption;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
	
    /**
     * Tests that a conversation will be exported correctly.
     * 
     * @throws IllegalArgumentException When it cannot find the test file.
     * @throws ReadConversationException When it cannot read from the test file.
     * @throws WriteConversationException When it cannot write to the test file.
     */
    @Test
    public void testConversationExports() throws IllegalArgumentException, ReadConversationException, WriteConversationException {
        ConversationExporter exporter = new ConversationExporter();
        
        TestFileHelper.clearOutput();
        exporter.export(new String[] {
        		"-" + ConfigurationOption.INPUT.getValue(), "chat.txt",
        		"-" + ConfigurationOption.OUTPUT.getValue(), "chat.json"});

        Conversation conversation = TestFileHelper.readOutput();
        TestConversationHelper.testConversation(conversation);
    }
    
    /**
     * Tests that a conversation will be exported correctly when filtered by user.
     * 
     * @throws IllegalArgumentException When it cannot find the test file.
     * @throws ReadConversationException When it cannot read from the test file.
     * @throws WriteConversationException When it cannot write to the test file.
     */
    @Test
    public void testConversationExportsFilteredByUser() throws IllegalArgumentException, ReadConversationException, WriteConversationException {
        ConversationExporter exporter = new ConversationExporter();
        
        TestFileHelper.clearOutput();
        exporter.export(new String[] {
        		"-" + ConfigurationOption.INPUT.getValue(), "chat.txt",
        		"-" + ConfigurationOption.OUTPUT.getValue(), "chat.json",
        		"-" + ConfigurationOption.USER.getValue(), "bob"});

        Conversation conversation = TestFileHelper.readOutput();
        
        Message[] messages = new Message[conversation.getMessages().size()];
        conversation.getMessages().toArray(messages);
    	
    	assertEquals(conversation.getMessages().size(), 3);
    	
        assertEquals(messages[0].getSenderId(), "bob");
        assertEquals(messages[1].getSenderId(), "bob");
        assertEquals(messages[2].getSenderId(), "bob");
    }
    
    /**
     * Tests that a conversation will be exported correctly when filtered by keyword.
     * 
     * @throws IllegalArgumentException When it cannot find the test file.
     * @throws ReadConversationException When it cannot read from the test file.
     * @throws WriteConversationException When it cannot write to the test file.
     */
    @Test
    public void testConversationExportsFilteredByKeyword() throws IllegalArgumentException, ReadConversationException, WriteConversationException {
        ConversationExporter exporter = new ConversationExporter();
        
        TestFileHelper.clearOutput();
        exporter.export(new String[] {
        		"-" + ConfigurationOption.INPUT.getValue(), "chat.txt",
        		"-" + ConfigurationOption.OUTPUT.getValue(), "chat.json",
        		"-" + ConfigurationOption.KEYWORD.getValue(), "pie"});

        Conversation conversation = TestFileHelper.readOutput();
        
        Message[] messages = new Message[conversation.getMessages().size()];
        conversation.getMessages().toArray(messages);
    	
        assertEquals(conversation.getMessages().size(), 4);
    	
        assertEquals(messages[0].getContent(), "I'm good thanks, do you like pie?");
        assertEquals(messages[1].getContent(), "Hell yes! Are we buying some pie?");
        assertEquals(messages[2].getContent(), "No, just want to know if there's anybody else in the pie society...");
        assertEquals(messages[3].getContent(), "YES! I'm the head pie eater there...");
    }
    
    /**
     * Tests that a conversation will be exported correctly when requested to
     * redact a set of words.
     * 
     * @throws IllegalArgumentException When it cannot find the test file.
     * @throws ReadConversationException When it cannot read from the test file.
     * @throws WriteConversationException When it cannot write to the test file.
     */
    @Test
    public void testConversationExportsRedactedBlacklist() throws IllegalArgumentException, ReadConversationException, WriteConversationException {
        ConversationExporter exporter = new ConversationExporter();
        
        TestFileHelper.clearOutput();
        exporter.export(new String[] {
        		"-" + ConfigurationOption.INPUT.getValue(), "chat.txt",
        		"-" + ConfigurationOption.OUTPUT.getValue(), "chat.json",
        		"-" + ConfigurationOption.BLACKLIST.getValue(), "hell yes, society, pie"});

        Conversation conversation = TestFileHelper.readOutput();
        
        Message[] messages = new Message[conversation.getMessages().size()];
        conversation.getMessages().toArray(messages);
    	
        assertEquals(conversation.getMessages().size(), 7);
    	
        assertEquals(messages[0].getContent(), "Hello there!");
        assertEquals(messages[1].getContent(), "how are you?");
        assertEquals(messages[2].getContent(), "I'm good thanks, do you like *redacted*?");
        assertEquals(messages[3].getContent(), "no, let me ask Angus...");
        assertEquals(messages[4].getContent(), "*redacted*! Are we buying some *redacted*?");
        assertEquals(messages[5].getContent(), "No, just want to know if there's anybody else in the *redacted* *redacted*...");
        assertEquals(messages[6].getContent(), "YES! I'm the head *redacted* eater there...");
    }
}
