package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

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
     * Tests that the {@link FilterService} returns only the specified user's
     * messages.
     */
    @Test
    public void testFilterByUser() {
    	FilterService filter = new FilterService();
    	
    	Conversation conversation = ConversationTestHelper.createStubConversation();	
    	Conversation filterConversation = filter.filterByUser(conversation, "bob");
    	
    	Message[] filterMessages = new Message[filterConversation.getMessages().size()];
    	filterConversation.getMessages().toArray(filterMessages);
    	
    	assertEquals(filterConversation.getMessages().size(), 3);
    	
        assertEquals(filterMessages[0].getSenderId(), "bob");
        assertEquals(filterMessages[1].getSenderId(), "bob");
        assertEquals(filterMessages[2].getSenderId(), "bob");
    }
    
    /**
     * Tests that the {@link FilterService} only returns messages with a specific
     * keyword.
     */
    @Test
    public void testFilterByKeyword() {
    	FilterService filter = new FilterService();
    	
    	Conversation conversation = ConversationTestHelper.createStubConversation();	
    	Conversation filterConversation = filter.filterByKeyword(conversation, "pie");
    	
    	Message[] filterMessages = new Message[filterConversation.getMessages().size()];
    	filterConversation.getMessages().toArray(filterMessages);
    	
    	assertEquals(filterConversation.getMessages().size(), 4);
    	
        assertEquals(filterMessages[0].getContent(), "I'm good thanks, do you like pie?");
        assertEquals(filterMessages[1].getContent(), "Hell yes! Are we buying some pie?");
        assertEquals(filterMessages[2].getContent(), "No, just want to know if there's anybody else in the pie society...");
        assertEquals(filterMessages[3].getContent(), "YES! I'm the head pie eater there...");
    }
}
