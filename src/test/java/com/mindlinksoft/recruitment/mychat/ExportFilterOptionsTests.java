package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.options.BlacklistFilter;
import com.mindlinksoft.recruitment.mychat.options.ConversationExportOptionInterface;
import com.mindlinksoft.recruitment.mychat.options.ByKeywordFilter;
import com.mindlinksoft.recruitment.mychat.options.ByUserFilter;

import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExportOptionInterface}.
 */
public class ExportFilterOptionsTests {
    /**
     * Test that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testFilteringByUserReturnsFilteredConversationByUser() throws Exception {
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.filterUserID = "paul";

        Conversation conversation = createTestConversation();

        // Check that the test conversation created is indeed correct.
        assertEquals(conversation.getName(), "Chuckle Brothers Test Conversation");
        assertEquals(conversation.getMessages().size(), 7);

        // Filter conversation with ByUserFilter
        Conversation filteredConversation = new ByUserFilter(config.filterUserID).process(conversation);
        Message[] filteredConversationMessages = new Message[filteredConversation.getMessages().size()];
        filteredConversation.getMessages().toArray(filteredConversationMessages);

        assertEquals(conversation.getMessages().size(), 3);

        assertEquals(filteredConversationMessages[0].getSenderID(), "paul");
        assertEquals(filteredConversationMessages[0].getContent(), "Hello Barry, how are you?");
        assertEquals(filteredConversationMessages[1].getSenderID(), "paul");
        assertEquals(filteredConversationMessages[1].getContent(), "To me...");
        assertEquals(filteredConversationMessages[2].getSenderID(), "paul");
        assertEquals(filteredConversationMessages[2].getContent(), "To me...");
    }

    @Test
    public void testFilteringByKeywordReturnsFilteredConversationByKeyword() throws Exception {
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.filterKeyword = "Hello";

        Conversation conversation = createTestConversation();
        Collection<Message> conversationMessages = conversation.getMessages();

        // Check that the test conversation created is indeed correct.
        assertEquals(conversation.getName(), "Chuckle Brothers Test Conversation");
        assertEquals(conversation.getMessages().size(), 7);

        // Filter conversation with ByKeywordFilter
        Conversation filteredConversation = new ByKeywordFilter(config.filterKeyword).process(conversation);
        Message[] filteredConversationMessages = new Message[filteredConversation.getMessages().size()];
        filteredConversation.getMessages().toArray(filteredConversationMessages);

        assertEquals(filteredConversation.getMessages().size(), 2);

        assertEquals(filteredConversationMessages[0].getSenderID(), "barry");
        assertEquals(filteredConversationMessages[0].getContent(), "Hello Paul!");
        assertEquals(filteredConversationMessages[1].getSenderID(), "paul");
        assertEquals(filteredConversationMessages[1].getContent(), "Hello Barry, how are you?");
    }

    @Test
    public void testFilteringByOneBlacklistWordKeywordReturnsFilteredConversationByWord() throws Exception {
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        String[] blackListWordArray = new String[] {"hello"};
        config.blacklistWord = blackListWordArray;

        Conversation conversation = createTestConversation();
        Collection<Message> conversationMessages = conversation.getMessages();

        // Check that the test conversation created is indeed correct.
        assertEquals(conversation.getName(), "Chuckle Brothers Test Conversation");
        assertEquals(conversation.getMessages().size(), 7);

        // Filter conversation with BlacklistFilter
        Conversation filteredConversation = new BlacklistFilter(config.blacklistWord).process(conversation);
        Message[] filteredConversationMessages = new Message[filteredConversation.getMessages().size()];
        filteredConversation.getMessages().toArray(filteredConversationMessages);

        assertEquals(filteredConversation.getMessages().size(), 7);

        assertEquals(filteredConversationMessages[0].getSenderID(), "barry");
        assertEquals(filteredConversationMessages[0].getContent(), "*REDACTED* Paul!");
        assertEquals(filteredConversationMessages[1].getSenderID(), "paul");
        assertEquals(filteredConversationMessages[1].getContent(), "*REDACTED* Barry, how are you?");
        assertEquals(filteredConversationMessages[2].getSenderID(), "barry");
        assertEquals(filteredConversationMessages[2].getContent(), "Ha! Here take this ladder, Paul!");
        assertEquals(filteredConversationMessages[3].getSenderID(), "paul");
        assertEquals(filteredConversationMessages[3].getContent(), "To me...");
        assertEquals(filteredConversationMessages[4].getSenderID(), "barry");
        assertEquals(filteredConversationMessages[4].getContent(), "To you...");
        assertEquals(filteredConversationMessages[5].getSenderID(), "paul");
        assertEquals(filteredConversationMessages[5].getContent(), "To me...");
        assertEquals(filteredConversationMessages[6].getSenderID(), "barry");
        assertEquals(filteredConversationMessages[6].getContent(), "To you...");
    }

    @Test
    public void testFilteringByMultipleBlacklistWordKeywordsReturnsFilteredConversationByWords() throws Exception {
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        String[] blackListWordArray = new String[] {"Paul", "Barry"};
        config.blacklistWord = blackListWordArray;

        Conversation conversation = createTestConversation();

        // Check that the test conversation created is indeed correct.
        assertEquals(conversation.getName(), "Chuckle Brothers Test Conversation");
        assertEquals(conversation.getMessages().size(), 7);

        // Filter conversation with BlacklistFilter
        Conversation filteredConversation = new BlacklistFilter(config.blacklistWord).process(conversation);
        Message[] filteredConversationMessages = new Message[filteredConversation.getMessages().size()];
        filteredConversation.getMessages().toArray(filteredConversationMessages);


        assertEquals(filteredConversation.getMessages().size(), 7);

        assertEquals(filteredConversationMessages[0].getSenderID(), "barry");
        assertEquals(filteredConversationMessages[0].getContent(), "Hello *REDACTED*!");
        assertEquals(filteredConversationMessages[1].getSenderID(), "paul");
        assertEquals(filteredConversationMessages[1].getContent(), "Hello *REDACTED*, how are you?");
        assertEquals(filteredConversationMessages[2].getSenderID(), "barry");
        assertEquals(filteredConversationMessages[2].getContent(), "Ha! Here take this ladder, *REDACTED*!");
        assertEquals(filteredConversationMessages[3].getSenderID(), "paul");
        assertEquals(filteredConversationMessages[3].getContent(), "To me...");
        assertEquals(filteredConversationMessages[4].getSenderID(), "barry");
        assertEquals(filteredConversationMessages[4].getContent(), "To you...");
        assertEquals(filteredConversationMessages[5].getSenderID(), "paul");
        assertEquals(filteredConversationMessages[5].getContent(), "To me...");
        assertEquals(filteredConversationMessages[6].getSenderID(), "barry");
        assertEquals(filteredConversationMessages[6].getContent(), "To you...");
    }


    public Conversation createTestConversation() {
        List<Message> testMessages = new ArrayList<>();

        testMessages.add(0, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123000")), "barry", "Hello Paul!"));
        testMessages.add(1, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123001")), "paul", "Hello Barry, how are you?"));
        testMessages.add(2, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123002")), "barry", "Ha! Here take this ladder, Paul!"));
        testMessages.add(3, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123003")), "paul", "To me..."));
        testMessages.add(4, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123004")), "barry", "To you..."));
        testMessages.add(5, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123005")), "paul", "To me..."));
        testMessages.add(6, new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("123006")), "barry", "To you..."));

        Conversation testConversation = new Conversation("Chuckle Brothers Test Conversation", testMessages);

        return testConversation;
    }
}
