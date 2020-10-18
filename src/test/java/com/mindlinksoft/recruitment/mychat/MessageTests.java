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
 * Tests for the {@link Message}.
 */
public class MessageTests {

    private Message m;
    private Conversation c;
    /**
     * Sets up Message object
     */
    @Before
    public void setUp() throws Exception {
        Instant timeStamp = Instant.now();
        m = new Message(timeStamp, "angus", "YES! I'm the head pie eater there...");
    }

    /**
     * Tests whether correct boolean value is returned if a given user sent a message
     */
    @Test
    public void testMessageSentByUserCheck() {
        assertEquals(true, m.isSentBy("angus"));
        assertEquals(false, m.isSentBy("bob"));

    }

    /**
     * Test for correct boolean value if single message contains keyword
     */
    @Test
    public void testContentContainsMessage() throws Exception {
        assertEquals(true, m.contentContains("pie"));
        assertEquals(false, m.contentContains("goodbye"));
    }

}
