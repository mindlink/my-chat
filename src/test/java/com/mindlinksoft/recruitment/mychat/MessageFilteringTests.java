package com.mindlinksoft.recruitment.mychat;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for message filtering.
 */
public class MessageFilteringTests {
    Conversation conversation;

    @Before
    public void setup() {
        List<Message> testMessages = new ArrayList<Message>();
        testMessages.add(new Message(Instant.ofEpochSecond(1448470901), "Kris", "Hello, this is a test message."));
        testMessages.add(new Message(Instant.ofEpochSecond(1448470905), "Alice", "Hi, this is also a message to test."));
        testMessages.add(new Message(Instant.ofEpochSecond(1448470906), "Kris", "Just another message!"));

        conversation = new Conversation("Test conversation", testMessages);
    }

     /**
     * Tests that filterer will filter conversation given a user.
     * In this case "Alice"
     * @throws Exception When something bad happens
     */
    @Test
    public void testFilterConversationByUser() throws Exception{
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.filterUser = "Alice";

        Conversation testConvo = conversation;

        testConvo = new Filterer().filterConversation(testConvo, config);
        
        ArrayList<Message> messageArray = new ArrayList<Message>(testConvo.messages);

        assertEquals(1, messageArray.size());

        assertEquals("Alice", messageArray.get(0).senderId);
        assertEquals("Hi, this is also a message to test.", messageArray.get(0).content);
        assertEquals(Instant.ofEpochSecond(1448470905), messageArray.get(0).timestamp);
    }

    /**
     * Tests the filterer will filter conversation given a keyword
     * In this case "test"
     * @throws Exception When something bad happens.
     */
    @Test
    public void testFilterConversationByKeyword() throws Exception{
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.filterKeyword = "test";

        Conversation testConvo = conversation;

        testConvo = new Filterer().filterConversation(testConvo, config);

        ArrayList<Message> messageArray = new ArrayList<Message>(testConvo.messages);

        assertEquals(2, messageArray.size());

        assertEquals("Kris", messageArray.get(0).senderId);
        assertEquals("Alice", messageArray.get(1).senderId);
    }

    /**
     * Test that the filterer will filter conversation given a user and a keyword
     * In this case "Kris" and "this" 
     * @throws Exception When something bad happens.
     */
    @Test 
    public void testFilterConversationByKeywordAndUser() throws Exception {
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.filterUser = "Kris";
        config.filterKeyword = "this";

        Conversation testConvo = conversation;

        testConvo = new Filterer().filterConversation(testConvo, config);

        ArrayList<Message> messageArray = new ArrayList<Message>(testConvo.messages);

        assertEquals(1, messageArray.size());

        assertEquals("Kris", messageArray.get(0).senderId);
        assertEquals("Hello, this is a test message.", messageArray.get(0).content);
    }

    /**
     * Test whether the filterer will redact black listed words from a conversation
     * In this case "message"
     * @throws Exception when somthing goes wrong.
     */
    @Test
    public void testFilterConversationByBlacklist() throws Exception {
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        String[] words = new String[]{"message"};
        config.blacklistWords = words;

        Conversation testConvo = conversation;

        testConvo = new Filterer().filterConversation(testConvo, config);

        ArrayList<Message> messageArray = new ArrayList<Message>(testConvo.messages);

        assertEquals("Hello, this is a test *redacted*.", messageArray.get(0).content);
        assertEquals("Hi, this is also a *redacted* to test.", messageArray.get(1).content);
        assertEquals("Just another *redacted*!", messageArray.get(2).content);
    }

    /**
     * Test whether filterer will filter user, keyword and redact words from a conversation
     * In this case user "Kris", keyword "this" and blacklist word "message"
     * @throws Exception when something goes wrong
     */
    @Test
    public void testFilterConversationByAll() throws Exception {
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        String[] words = new String[]{"message"};
        config.blacklistWords = words;
        config.filterUser = "Kris";
        config.filterKeyword = "this";

        Conversation testConvo = conversation;

        testConvo = new Filterer().filterConversation(testConvo, config);

        ArrayList<Message> messageArray = new ArrayList<Message>(testConvo.messages);

        assertEquals("Kris", messageArray.get(0).senderId);
        assertEquals("Hello, this is a test *redacted*.", messageArray.get(0).content);
    }
}