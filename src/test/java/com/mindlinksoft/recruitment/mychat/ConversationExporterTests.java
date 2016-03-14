package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.helpers.ConversationTestHelper;
import com.mindlinksoft.recruitment.mychat.helpers.TestFileHelper;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
	
    /**
     * Tests that a conversation will be exported correctly.
     * 
     * @throws IOException When it cannot read or write from the test file.
     * @throws IllegalArgumentException When it cannot find the test file.
     */
    @Test
    public void testConversationExports() throws IllegalArgumentException, IOException {
        ConversationExporter exporter = new ConversationExporter();
        
        TestFileHelper.clearOutput();
        exporter.export(new String[] {"-i", "chat.txt", "-o", "chat.json"});

        Conversation conversation = TestFileHelper.readOutput();
        ConversationTestHelper.testConversation(conversation);
    }
    
    /**
     * Tests that a conversation will be exported correctly when filtered by user.
     * 
     * @throws IOException When it cannot read or write from the test file.
     * @throws IllegalArgumentException When it cannot find the test file.
     */
    @Test
    public void testConversationExportsFilteredByUser() throws IllegalArgumentException, IOException {
        ConversationExporter exporter = new ConversationExporter();
        
        TestFileHelper.clearOutput();
        exporter.export(new String[] {"-i", "chat.txt", "-o", "chat.json", "-u", "bob"});

        Conversation conversation = TestFileHelper.readOutput();
        
        Message[] filterMessages = new Message[conversation.getMessages().size()];
        conversation.getMessages().toArray(filterMessages);
    	
    	assertEquals(conversation.getMessages().size(), 3);
    	
        assertEquals(filterMessages[0].getSenderId(), "bob");
        assertEquals(filterMessages[1].getSenderId(), "bob");
        assertEquals(filterMessages[2].getSenderId(), "bob");
    }
}
