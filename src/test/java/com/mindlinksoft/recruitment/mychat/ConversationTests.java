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

}
