package com.mindlinksoft.recruitment.mychat.services;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.exceptions.ReadConversationException;
import com.mindlinksoft.recruitment.mychat.exceptions.WriteConversationException;
import com.mindlinksoft.recruitment.mychat.helpers.TestConversationHelper;
import com.mindlinksoft.recruitment.mychat.helpers.TestFileHelper;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.services.FileIOService;

/**
 * Tests for the {@link FileIOService}.
 */
public class FileIOServiceTests {
	
	/**
     * Tests that the {@link FileIOService} will read a file properly.
     * 
	 * @throws IllegalArgumentException When it cannot find the test file.
	 * @throws ReadConversationException When it cannot read from the test file.
     */
    @Test
    public void testReadFile() throws IllegalArgumentException, ReadConversationException  {
    	FileIOService fileService = new FileIOService();
    	Conversation conversation = fileService.readConversation("chat.txt");
    	
    	TestConversationHelper.testConversation(conversation);
    }
    
    /**
     * Tests that the {@link FileIOService} will write a file properly.
     * 
     * @throws IllegalArgumentException When it cannot find the test file path.
     * @throws WriteConversationException When it cannot write to the test file.
     * @throws ReadConversationException When it cannot read from the output test file.
     */
    @Test
    public void testWriteFile() throws IllegalArgumentException, WriteConversationException, ReadConversationException {
    	FileIOService fileService = new FileIOService();
    	
    	TestFileHelper.clearOutput();
    	Conversation stubConversation = TestConversationHelper.createStubConversation();
    	fileService.writeConversation(stubConversation, "chat.json");
    	
    	Conversation conversation = TestFileHelper.readOutput();
    	TestConversationHelper.testConversation(conversation);
    }
}
