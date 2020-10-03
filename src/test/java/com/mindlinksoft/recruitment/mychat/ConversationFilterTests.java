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
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationFilterTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testBlacklist() throws Exception {
       List<String> myBlacklist = Arrays.asList("pie", "eater");
       List<Message> myMessages = new ArrayList<Message>();
       myMessages.add(0, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", "I am a pie eater"));
       myMessages.add(1, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123457")), "larry", "I am a pie-eater"));
       myMessages.add(2, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "I am a pie, eater!"));
       myMessages.add(3, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "This doesn't contain any blacklisted words"));
       myMessages.add(4, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", "I am a /*redacted*? [*redacted*!"));
       myMessages.add(5, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "larry", ""));

       Conversation conversation = new Conversation("Conversation name", myMessages);

       ConversationFilter cf = new ConversationFilter();
       cf.removeBlacklist(conversation, myBlacklist);
       List<Message> resultMessages = conversation.getMessages();
       assertEquals(resultMessages.get(0).getContent(), "I am a *redacted* *redacted*");
       assertEquals(resultMessages.get(1).getContent(), "I am a pie-eater");
       assertEquals(resultMessages.get(2).getContent(), "I am a *redacted* *redacted*");
       assertEquals(resultMessages.get(3).getContent(), "This doesn't contain any blacklisted words");
       assertEquals(resultMessages.get(4).getContent(), "I am a /*redacted*? [*redacted*!");
       assertEquals(resultMessages.get(5).getContent(), "");
    }

    @Test
    public void testWordFilter() throws Exception {
        String filterWord = "pie";
        List<Message> myMessages = new ArrayList<Message>();
        myMessages.add(0, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", "I am a pie eater"));
        myMessages.add(1, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123457")), "larry", "I am a pie-eater"));
        myMessages.add(2, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "I am a pie, eater!"));
        myMessages.add(3, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "This doesn't contain any blacklisted words"));
        myMessages.add(4, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "larry", "I am a /*redacted*? [*redacted*!"));
        myMessages.add(5, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", ""));
 
        Conversation conversation = new Conversation("Conversation name", myMessages);

       ConversationFilter cf = new ConversationFilter();
       cf.filterByKeyword(conversation, filterWord);
       assertEquals(conversation.getMessages().size(), 2);
       assertEquals(conversation.getMessages().get(0).getContent(), "I am a pie eater");
       assertEquals(conversation.getMessages().get(1).getContent(), "I am a pie, eater!");
    }

    @Test
    public void testUserFilter() throws Exception {
        String filterUser = "larry";
        List<Message> myMessages = new ArrayList<Message>();
        myMessages.add(0, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", "I am a pie eater"));
        myMessages.add(1, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123457")), "larry", "I am a pie-eater"));
        myMessages.add(2, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "I am a pie, eater!"));
        myMessages.add(3, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "garry", "This doesn't contain any blacklisted words"));
        myMessages.add(4, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "larry", "I am a /*redacted*? [*redacted*!"));
        myMessages.add(5, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123456")), "harry", ""));
 
        Conversation conversation = new Conversation("Conversation name", myMessages);

       ConversationFilter cf = new ConversationFilter();
       cf.filterByUser(conversation, filterUser);
       assertEquals(conversation.getMessages().size(), 2);
       assertEquals(conversation.getMessages().get(0).getContent(), "I am a pie-eater");
       assertEquals(conversation.getMessages().get(1).getContent(), "I am a /*redacted*? [*redacted*!");
    }

}
