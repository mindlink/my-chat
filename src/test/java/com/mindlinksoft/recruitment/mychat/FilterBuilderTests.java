package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.config.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.converter.FilterBuilder;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import org.junit.Test;

import picocli.CommandLine;
import picocli.CommandLine.ParseResult;

/**
 * Tests for checking whether the builder correctly chooses the appropriate filters
 */
public class FilterBuilderTests {

    private CommandLine cmd;
    private ConversationExporterConfiguration config = new ConversationExporterConfiguration();
    private Conversation conversation;

    /**
     * Test for verifying that the builder applies the blacklist filter given a config and parse result
     * @throws Exception When something bad happens
     */
    @Test
    public void testBlacklistFlag() throws Exception {
        ParseResult pr = cmd.parseArgs("-i", "input", "-o", "output", "-b", "word");
        FilterBuilder fb = new FilterBuilder(pr, config);

        String result = fb.filterConversation(conversation);
        assertEquals(result, "Running filter: blacklist\n");
    }

    /**
     * Test for verifying that the builder applies the filterByKeyword filter given a config and parse result
     * @throws Exception When something bad happens
     */
    @Test
    public void testKeywordFlag() throws Exception {
        ParseResult pr = cmd.parseArgs("-i", "input", "-o", "output", "-fw", "word");
        FilterBuilder fb = new FilterBuilder(pr, config);

        String result = fb.filterConversation(conversation);
        assertEquals(result, "Running filter: filterByKeyword\n");
    }

    /**
     * Test for verifying that the builder applies the filterByUser filter given a config and parse result
     * @throws Exception When something bad happens
     */
    @Test
    public void testUserFlag() throws Exception {
        ParseResult pr = cmd.parseArgs("-i", "input", "-o", "output", "-fu", "word");
        FilterBuilder fb = new FilterBuilder(pr, config);

        String result = fb.filterConversation(conversation);
        assertEquals(result, "Running filter: filterByUser\n");
    }
    
    /**
     * Test for verifying that the builder produces a report given a config and parse result
     * @throws Exception When something bad happens
     */
    @Test
    public void testReportFlag() throws Exception {
        ParseResult pr = cmd.parseArgs("-i", "input", "-o", "output", "-r");
        FilterBuilder fb = new FilterBuilder(pr, config);

        String result = fb.filterConversation(conversation);
        assertEquals(result, "Producing report");
    }


    public FilterBuilderTests() {
        cmd = new CommandLine(config);

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
