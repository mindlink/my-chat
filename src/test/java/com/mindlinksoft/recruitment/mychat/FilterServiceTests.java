package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.helpers.ConversationTestHelper;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.services.FilterService;

/**
 * Tests for the {@link FilterService}.
 */
public class FilterServiceTests {
	
	/**
     * Tests that the {@link FilterService} returns only the specified users
     * messages.
     */
    @Test
    public void testFilterUser() {
    	FilterService filter = new FilterService();
    	
    	Conversation conversation = ConversationTestHelper.createStubConversation();	
    	Conversation filterConversation = filter.filterUser(conversation, "bob");
    	
    	Message[] filterMessages = new Message[filterConversation.getMessages().size()];
    	filterConversation.getMessages().toArray(filterMessages);
    	
    	assertEquals(filterConversation.getMessages().size(), 3);
    	
        assertEquals(filterMessages[0].getSenderId(), "bob");
        assertEquals(filterMessages[1].getSenderId(), "bob");
        assertEquals(filterMessages[2].getSenderId(), "bob");
    }
}
