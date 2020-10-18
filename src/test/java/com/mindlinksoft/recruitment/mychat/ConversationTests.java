package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link Conversation}.
 */
public class ConversationTests {

    private Conversation c;

    /**
     * Sets up conversation object read in from ./chat.txt
     */
    @Before
    public void setUp() throws Exception {
        Path txtPath = Paths.get(".", "/chat.txt");
        ConversationExporter expoter = new ConversationExporter();
        c = expoter.readConversation(txtPath.toString());
    }

    /**
     * Test for correct number of messages in filtered conversation by userName/senderID
     */
    @Test
    public void testfilterByUserName() {
        c.filterByUserName("bob");

        assertEquals(3, c.messages.size());
    }

    /**
     * Test for correct number of messages in conversation filtered by keyword
     */
    @Test
    public void testFilterByKeyWordConversation() throws Exception {
        c.filterByKeyWord("pie");

        assertEquals(4, c.messages.size());
    }

    /**
     * Test for correct number of messages in conversation filtered by keyword
     */
    @Test
    public void testRedactConversation() throws Exception {
        String[] redactedWords = {"pie", "Angus"};
        c.redactByKeyWords(redactedWords);
        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals("Hello there!", ms[0].content);
        assertEquals("how are you?", ms[1].content);
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].content);
        assertEquals("no, let me ask *redacted*...", ms[3].content);
        assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].content);
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms[5].content);
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].content);
    }

}
