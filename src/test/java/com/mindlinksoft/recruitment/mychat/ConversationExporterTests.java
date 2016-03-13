package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.helpers.ConversationTestHelper;
import com.mindlinksoft.recruitment.mychat.helpers.ReadFileHelper;
import com.mindlinksoft.recruitment.mychat.models.Conversation;

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
    public void testConversationExportsCorrectly() throws IllegalArgumentException, IOException {
        ConversationExporter exporter = new ConversationExporter();
        exporter.export(new String[] {"chat.txt", "chat.json"});

        Conversation conversation = ReadFileHelper.readTestFile();
        ConversationTestHelper.testConversation(conversation);
    }
}
