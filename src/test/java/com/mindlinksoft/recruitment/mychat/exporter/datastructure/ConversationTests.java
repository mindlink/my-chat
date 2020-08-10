package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ConversationTests {
    
    String exampleTitle;
    List<Message> exampleMessages;
    // Map<String, Sender> exampleSenders; FIXME: either implement or remove

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

    Message exampleMessage;
    
    Conversation exampleConversation;

    @Before
    public void setUp() {
        // exampleConversation arguments
        exampleTitle = "My Conversation";
        exampleMessages = new ArrayList<>();

        // conversation from above arguments i.e. no messages/sender added
        exampleConversation = new Conversation(exampleTitle, exampleMessages);

        // bob's message, not yet in conversation
        bobLine = "1448470901 bob Hello there!";
        bobInstant = Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901"));
        bobSenderText = "bob";
        bobContent = "Hello there!";
        bobMessage = new Message(bobInstant, bobSenderText, bobContent);

        // mike's message, not yet in conversation
        mikeLine = "1448470905 mike how are you?";
        mikeInstant = Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905"));
        mikeSenderText = "mike";
        mikeContent = "how are you?";
        mikeMessage = new Message(mikeInstant, mikeSenderText, mikeContent);
    }
    
    @Test
    public void getMessage() {
        // place bob's message first, index should be 0
        exampleMessages.add(bobMessage);
        assertEquals(bobMessage, exampleConversation.getMessage(0));

        // place mike's message next, index should be 1
        exampleMessages.add(mikeMessage);
        assertEquals(mikeMessage, exampleConversation.getMessage(1));

        // bob's message should still be index 0
        assertEquals(bobMessage, exampleConversation.getMessage(0));
    }
}