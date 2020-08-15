package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

public class MessageTests {

    Message nullMessage; // non-null message with null fields

    String bobLine;
    String bobSenderText;
    Instant bobInstant;
    String bobContent;
    Message bobMessage;

    String mikeLine;
    String mikeSenderText;
    Instant mikeInstant;
    String mikeContent;
    Message mikeMessage;

    @Before
    public void setUp() {
        nullMessage = new Message(null, null, null);

        // bob's message
        bobLine = "1448470901 bob Hello there!";
        bobInstant = Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901"));
        bobSenderText = "bob";
        bobContent = "Hello there!";
        bobMessage = new Message(bobInstant, bobSenderText, bobContent);

        // mike's message
        mikeLine = "1448470905 mike how are you?";
        mikeInstant = Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905"));
        mikeSenderText = "mike";
        mikeContent = "how are you?";
        mikeMessage = new Message(mikeInstant, mikeSenderText, mikeContent);
    }

    @Test
    public void getContent() {
        assertNull(nullMessage.getContent());
        assertEquals("Hello there!", bobMessage.getContent());
        assertEquals("how are you?", mikeMessage.getContent());
    }

    @Test
    public void getTimestamp() {
        assertNull(nullMessage.getTimestamp());
        assertEquals(bobInstant, bobMessage.getTimestamp());
        assertEquals(mikeInstant, mikeMessage.getTimestamp());
    }

    @Test
    public void getSenderText() {
        assertNull(nullMessage.getSenderText());
        assertEquals(bobSenderText, bobMessage.getSenderText());
        assertEquals(mikeSenderText, mikeMessage.getSenderText());
    }

}