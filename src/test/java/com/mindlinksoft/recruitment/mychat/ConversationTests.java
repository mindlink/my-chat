package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        assertEquals(2, conversation.messages.size());

        // Confirm all remaining messages have senderId of "alice"
        for (Message message : conversation.messages) {
            assertEquals("alice", message.senderId);
        }
    }

    /**
     * Test that keyword filter removes all messages which don't contain the keyword
     */
    @Test
    public void testKeywordFilter() {
        // Create test data
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.EPOCH, "alice", "Test message one!"));
        messages.add(new Message(Instant.EPOCH, "bob", "Test message two with keyword"));
        messages.add(new Message(Instant.EPOCH, "alice", "Test message three with Keyword"));

        Conversation conversation = new Conversation("Test Conversation", messages);
        conversation.applyKeywordFilter("keyword");

        // There are two messages containing keyword, case insensitive
        assertEquals(2, conversation.messages.size());

        // Confirm the remaining messages are the ones containing "keyword"
        Message[] outMessages = new Message[conversation.messages.size()];
        conversation.messages.toArray(outMessages);

        assertEquals("Test message two with keyword", outMessages[0].content);
        assertEquals("Test message three with Keyword", outMessages[1].content);
    }

    /**
     * Test the blacklist censors banned words
     */
    @Test
    public void testBlacklist() {
        // Create test data
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.EPOCH, "alice", "Pie!"));
        messages.add(new Message(Instant.EPOCH, "bob", "pie anybody?"));
        messages.add(new Message(Instant.EPOCH, "alice", "No thanks!"));

        Conversation conversation = new Conversation("Test Conversation", messages);

        try {
            conversation.applyBlacklist("blacklist.txt");
        } catch (IOException | IllegalArgumentException e) {
            fail(e.getMessage());
        }

        // Confirm the messages have been censored
        Message[] outMessages = new Message[conversation.messages.size()];
        conversation.messages.toArray(outMessages);

        assertEquals("*redacted*!", outMessages[0].content);
        assertEquals("*redacted* *redacted*?", outMessages[1].content);
        assertEquals("No thanks!", outMessages[2].content);
    }

    /**
     * Test user name obfuscation works
     */
    @Test
    public void testObfuscate() {
        // Create test data
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.EPOCH, "alice", "Test one!"));
        messages.add(new Message(Instant.EPOCH, "bob", "Test two"));
        messages.add(new Message(Instant.EPOCH, "alice", "Test three!"));

        Conversation conversation = new Conversation("Test Conversation", messages);
        conversation.obfuscateUserNames();

        // Confirm the messages have been obfuscated
        Message[] outMessages = new Message[conversation.messages.size()];
        conversation.messages.toArray(outMessages);

        assertEquals("User 1", outMessages[0].senderId);
        assertEquals("User 2", outMessages[1].senderId);
        assertEquals("User 1", outMessages[2].senderId);
    }

    /**
     * Test hiding personal information works
     */
    @Test
    public void testPersonalInfo() {
        // Create test data
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.EPOCH, "alice", "01604 532235 is my phone number"));
        messages.add(new Message(Instant.EPOCH, "bob", "0000-0000-0000-1234 is my credit card number"));

        Conversation conversation = new Conversation("Test Conversation", messages);
        conversation.hidePersonalInformation();

        // Confirm the messages have been censored
        Message[] outMessages = new Message[conversation.messages.size()];
        conversation.messages.toArray(outMessages);

        assertEquals("*redacted* is my phone number", outMessages[0].content);
        assertEquals("*redacted* is my credit card number", outMessages[1].content);
    }

    /**
     * Test activity ranking works
     */
    @Test
    public void testActivityRank() {
        // Create test data
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.EPOCH, "alice", "Test message"));
        messages.add(new Message(Instant.EPOCH, "bob", "Test message"));
        messages.add(new Message(Instant.EPOCH, "bill", "Test message"));
        messages.add(new Message(Instant.EPOCH, "bill", "Test message"));
        messages.add(new Message(Instant.EPOCH, "alice", "Test message"));
        messages.add(new Message(Instant.EPOCH, "bill", "Test message"));

        Conversation conversation = new Conversation("Test Conversation", messages);
        conversation.generateRanking();

        // Confirm the ranking is the correct length and correct values
        assertEquals(3, conversation.userRanking.size());

        String[] ranking = new String[conversation.userRanking.size()];
        conversation.userRanking.toArray(ranking);

        assertEquals("bill", ranking[0]);
        assertEquals("alice", ranking[1]);
        assertEquals("bob", ranking[2]);
    }
}
