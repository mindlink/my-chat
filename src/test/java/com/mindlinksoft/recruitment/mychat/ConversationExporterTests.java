package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.helpers.ConversationTestHelper;
import com.mindlinksoft.recruitment.mychat.helpers.ReadFileHelper;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import org.junit.Test;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
	
    /**
     * Tests that a conversation will be exported correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testConversationExportsCorrectly() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        exporter.export(new String[] {"chat.txt", "chat.json"});

        Conversation conversation = ReadFileHelper.readTestFile();
        ConversationTestHelper.testConversation(conversation);
    }
}
