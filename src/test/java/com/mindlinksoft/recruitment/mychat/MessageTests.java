package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MessageTests {
    
    Message nullMessage; // non-null message with null fields

    String bobLine;
    Sender bobSender;
    Instant bobInstant;
    String bobContent;
    Message bobMessage;

    String mikeLine;
    Sender mikeSender;
    Instant mikeInstant;
    String mikeContent;
    Message mikeMessage;

    Map<String, Sender> senderMap;

    @Before
    public void setUp() {
        nullMessage = new Message(null, null, null);
        
        // bob's message, bob is not in sendermap
        bobLine = "1448470901 bob Hello there!";
        bobInstant = Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901"));
        bobSender = new Sender("bob");
        bobContent = "Hello there!";
        bobMessage = new Message(bobInstant, bobSender, bobContent);

        // mike's message, mike is already in sendermap
        mikeLine = "1448470905 mike how are you?";
        mikeInstant = Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905"));
        mikeSender = new Sender("mike");
        mikeContent = "how are you?";
        mikeMessage = new Message(mikeInstant, mikeSender, mikeContent);

        // adding mike to senderMap i.e. he already has a Sender object
        senderMap = new HashMap<>();
        senderMap.put("mike", mikeSender);
    } 

    @Test
    public void getContent() {
        assertNull(nullMessage.getContent());
        
        assertEquals("Hello there!", bobMessage.getContent());
        
        assertEquals("how are you?", mikeMessage.getContent());
    }

    @Test
    public void parseLine() {
        // parsing bob's message
        Message resultMessage = Message.parseLine(bobLine, senderMap);
        assertEquals(bobInstant, resultMessage.getTimestamp());
        assertEquals(bobSender, resultMessage.getSender());
        assertEquals(bobContent, resultMessage.getContent());

        // parsing mike's message
        resultMessage = Message.parseLine(mikeLine, senderMap);
        assertEquals(mikeInstant, resultMessage.getTimestamp());
        assertEquals(mikeSender, resultMessage.getSender());
        assertEquals(mikeContent, resultMessage.getContent());

        // sendermap length should be two, i.e not creating extra 
        // senders if already encountered sender before
        assertEquals(2, senderMap.size());
        assertEquals(bobSender, senderMap.get("bob"));
        assertEquals(mikeSender, senderMap.get("mike"));
    }
}