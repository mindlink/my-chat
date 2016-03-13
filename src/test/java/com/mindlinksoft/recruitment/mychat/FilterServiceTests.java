package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "bob", "Hello there!"));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905")), "mike", "how are you?"));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470906")), "bob", "I'm good thanks, do you like pie?"));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470910")), "mike", "no, let me ask Angus..."));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470912")), "angus", "Hell yes! Are we buying some pie?"));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470914")), "bob", "No, just want to know if there's anybody else in the pie society..."));
    	messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470915")), "angus", "YES! I'm the head pie eater there..."));
    	
    	Conversation conversation = new Conversation("My Conversation" , messages);
    	
    	FilterService filter = new FilterService();
    	Conversation filterConversation = filter.filterUser(conversation, "bob");
    	Message[] filterMessages = new Message[filterConversation.getMessages().size()];
    	filterConversation.getMessages().toArray(filterMessages);
    	
    	assertEquals(filterConversation.getMessages().size(), 3);
    	
        assertEquals(filterMessages[0].getSenderId(), "bob");
        assertEquals(filterMessages[1].getSenderId(), "bob");
        assertEquals(filterMessages[2].getSenderId(), "bob");
    }
}
