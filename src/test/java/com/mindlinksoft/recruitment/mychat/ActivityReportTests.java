package com.mindlinksoft.recruitment.mychat;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

import static org.junit.Assert.assertEquals;


/**
 * Tests for activity reporting.
 */
public class ActivityReportTests {
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
     * Tests that reporter correctly reports activity history.
     * @throws Exception When something bad happens
     */
    @Test
    public void testActivityReport() throws Exception{
        Conversation testConvo = conversation;

        testConvo = new Reporter().recordActivity(testConvo);
        
        ArrayList<Report> reportArray = new ArrayList<Report>(testConvo.activity);

        assertEquals(2, reportArray.size());

        assertEquals("Kris", reportArray.get(0).senderId);
        assertEquals(2, reportArray.get(0).count);

        assertEquals("Alice", reportArray.get(1).senderId);
        assertEquals(1, reportArray.get(1).count);    }
}