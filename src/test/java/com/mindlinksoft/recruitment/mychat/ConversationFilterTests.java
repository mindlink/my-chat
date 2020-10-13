package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.models.*;
import com.mindlinksoft.recruitment.mychat.filters.*;

import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the individual filters
 */
public class ConversationFilterTests {

    public Conversation conversation;

    /**
     * Tests the validity of the blacklist filter given a conversation
     * @throws Exception When something bad happens.
     */
    @Test
    public void testBlacklist() throws Exception {
        List<String> filterWords = Arrays.asList("pie", "Eater");
        
        FilterByBlacklist fb = new FilterByBlacklist(filterWords);
        fb.runFilter(conversation);

        List<Message> resultMessages = conversation.getMessages();
        assertEquals(resultMessages.get(0).getContent(), "I am a *redacted* *redacted*");
        assertEquals(resultMessages.get(1).getContent(), "I am a pie-eater");
        assertEquals(resultMessages.get(2).getContent(), "I am a *redacted* *redacted*");
        assertEquals(resultMessages.get(3).getContent(), "This doesn't contain any blacklisted words");
        assertEquals(resultMessages.get(4).getContent(), "I am a /*redacted*? [*redacted*!");
        assertEquals(resultMessages.get(5).getContent(), "");
    }

    /**
     * Tests the validity of the keyword filter given a conversation
     * @throws Exception When something bad happens
     */
    @Test
    public void testWordFilter() throws Exception {
        String filterWord = "pie";

        FilterByKeyword fw = new FilterByKeyword(filterWord);
        fw.runFilter(conversation);

        assertEquals(conversation.getMessages().size(), 2);
        assertEquals(conversation.getMessages().get(0).getContent(), "I am a pie eater");
        assertEquals(conversation.getMessages().get(1).getContent(), "I am a pie, eater!");
    }   

    /**
     * Tests the validity of the user filter given a conversation
     * @throws Exception When something bad happens
     */
    @Test
    public void testUserFilter() throws Exception {
        String filterUser = "larry";

       FilterByUser fu = new FilterByUser(filterUser);
       fu.runFilter(conversation);

       assertEquals(conversation.getMessages().size(), 2);
       assertEquals(conversation.getMessages().get(0).getContent(), "I am a pie-eater");
       assertEquals(conversation.getMessages().get(1).getContent(), "I am a /*redacted*? [*redacted*!");
    }

    public ConversationFilterTests() {
        // Create the new conversation
        List<Message> myMessages = new ArrayList<Message>();
        myMessages.add(0, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", "I am a pie eater"));
        myMessages.add(1, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123457")), "larry", "I am a pie-eater"));
        myMessages.add(2, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "I am a pie, eater!"));
        myMessages.add(3, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "This doesn't contain any blacklisted words"));
        myMessages.add(4, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "larry", "I am a /*redacted*? [*redacted*!"));
        myMessages.add(5, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", ""));
        conversation = new Conversation("Conversation name", myMessages);
    }

}
