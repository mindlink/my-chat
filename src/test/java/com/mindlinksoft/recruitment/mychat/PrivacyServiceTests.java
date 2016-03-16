package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.helpers.ConversationTestHelper;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.services.PrivacyService;

/**
 * Tests for the {@link PrivacyService}.
 */
public class PrivacyServiceTests {
	
	/**
     * Tests that the {@link PrivacyService} returns the conversation with the
     * blacklisted words redacted.
     */
    @Test
    public void testRedactBlacklist() {
    	PrivacyService privacy = new PrivacyService();
    	
    	List<String> blacklist = new ArrayList<String>();
    	blacklist.add("hell yes");
    	blacklist.add("society");
    	blacklist.add("pie");
    	
    	Conversation conversation = ConversationTestHelper.createStubConversation();	
    	Conversation redactedConversation = privacy.redactWords(conversation, blacklist);
    	
    	Message[] redactedMessages = new Message[redactedConversation.getMessages().size()];
    	redactedConversation.getMessages().toArray(redactedMessages);
    	
    	assertEquals(redactedConversation.getMessages().size(), 7);
    	
        assertEquals(redactedMessages[0].getContent(), "Hello there!");
        assertEquals(redactedMessages[1].getContent(), "how are you?");
        assertEquals(redactedMessages[2].getContent(), "I'm good thanks, do you like *redacted*?");
        assertEquals(redactedMessages[3].getContent(), "no, let me ask Angus...");
        assertEquals(redactedMessages[4].getContent(), "*redacted*! Are we buying some *redacted*?");
        assertEquals(redactedMessages[5].getContent(), "No, just want to know if there's anybody else in the *redacted* *redacted*...");
        assertEquals(redactedMessages[6].getContent(), "YES! I'm the head *redacted* eater there...");
    }
}
