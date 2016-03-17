package com.mindlinksoft.recruitment.mychat.helpers;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

/**
 * Static helper class to help test and create stub conversations.
 */
public final class TestConversationHelper {
	
	/**
     * Tests that the conversation supplied is the same as the test case conversation.
     */
	public static void testConversation(Conversation conversation) {	
    	Message[] messages = new Message[conversation.getMessages().size()];
    	conversation.getMessages().toArray(messages);
    	
    	assertEquals(conversation.getName(), "My Conversation");
        assertEquals(conversation.getMessages().size(), 7);

        assertEquals(messages[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(messages[0].getSenderId(), "bob");
        assertEquals(messages[0].getContent(), "Hello there!");

        assertEquals(messages[1].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(messages[1].getSenderId(), "mike");
        assertEquals(messages[1].getContent(), "how are you?");

        assertEquals(messages[2].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(messages[2].getSenderId(), "bob");
        assertEquals(messages[2].getContent(), "I'm good thanks, do you like pie?");

        assertEquals(messages[3].getTimestamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(messages[3].getSenderId(), "mike");
        assertEquals(messages[3].getContent(), "no, let me ask Angus...");

        assertEquals(messages[4].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(messages[4].getSenderId(), "angus");
        assertEquals(messages[4].getContent(), "Hell yes! Are we buying some pie?");

        assertEquals(messages[5].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(messages[5].getSenderId(), "bob");
        assertEquals(messages[5].getContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(messages[6].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(messages[6].getSenderId(), "angus");
        assertEquals(messages[6].getContent(), "YES! I'm the head pie eater there...");
	}
	
	/**
     * Creates a stub conversation to use for testing.
     */
	public static Conversation createStubConversation() {
		List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
    	messages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
    	messages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?"));
    	messages.add(new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus..."));
    	messages.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?"));
    	messages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "No, just want to know if there's anybody else in the pie society..."));
    	messages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head pie eater there..."));
    	
    	return new Conversation("My Conversation" , messages);
	}
}
