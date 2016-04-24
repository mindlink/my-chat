package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Conversation}.
 */
public class ConversationTests {
    /**
     * Test that username filter removes all messages except the chosen user's
     */
    @Test
    public void testUserFilter() {
        // Create test data
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.EPOCH, "alice", "Test message one!"));
        messages.add(new Message(Instant.EPOCH, "bob", "Test message two!"));
        messages.add(new Message(Instant.EPOCH, "alice", "Test message three!"));

        Conversation conversation = new Conversation("Test Conversation", messages);
        conversation.applyUserFilter("alice");

        // There are only two messages from alice
        assertEquals(conversation.messages.size(), 2);

        // Confirm all remaining messages have senderId of "alice"
        for (Message message : conversation.messages) {
            assertEquals("alice", message.senderId);
        }
    }
}
