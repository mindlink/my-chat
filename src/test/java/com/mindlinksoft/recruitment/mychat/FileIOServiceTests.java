package com.mindlinksoft.recruitment.mychat;

import java.io.IOException;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.helpers.ConversationTestHelper;
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
	 * @throws IOException When it cannot read from the test file.
	 * @throws IllegalArgumentException When it cannot find the test file.
     */
    @Test
    public void testReadFile() throws IllegalArgumentException, IOException  {
    	FileIOService fileService = new FileIOService();
    	Conversation conversation = fileService.readConversation("chat.txt");
    	
    	ConversationTestHelper.testConversation(conversation);
    }
    
    /**
     * Tests that the {@link FileIOService} will write a file properly.
     * 
     * @throws IOException When it cannot write to the test file.
     * @throws IllegalArgumentException When it cannot find the test file path.
     */
    @Test
    public void testWriteFile() throws IllegalArgumentException, IOException {
    	FileIOService fileService = new FileIOService();
    	
    	TestFileHelper.clearOutput();
    	Conversation stubConversation = ConversationTestHelper.createStubConversation();
    	fileService.writeConversation(stubConversation, "chat.json");
    	
    	Conversation conversation = TestFileHelper.readInput();
    	ConversationTestHelper.testConversation(conversation);
    }
}
