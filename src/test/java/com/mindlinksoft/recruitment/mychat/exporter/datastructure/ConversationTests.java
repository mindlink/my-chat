package com.mindlinksoft.recruitment.mychat.exporter.datastructure;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ConversationTests {

    String exampleTitle;
    List<Message> exampleMessages;
    List<Sender> exampleFrequency;

    String bobLine;
    String bobSenderText;
    Instant bobInstant;
    String bobContent;
    Message bobMessage;
    Sender bobSender;

    String mikeLine;
    String mikeSenderText;
    Instant mikeInstant;
    String mikeContent;
    Message mikeMessage;
    Sender mikeSender;

    Conversation exampleConversation;

    @Before
    public void setUp() {
        // exampleConversation arguments
        exampleTitle = "My Conversation";
        exampleMessages = new ArrayList<>();
        exampleFrequency = new ArrayList<>();

        // conversation from above arguments i.e. no messages/sender added
        exampleConversation = new Conversation(exampleTitle, exampleMessages, exampleFrequency);

        // bob's message, not yet in conversation
        bobLine = "1448470901 bob Hello there!";
        bobInstant = Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901"));
        bobSenderText = "bob";
        bobSender = new Sender(bobSenderText);
        bobSender.setMessageCount(1);
        bobContent = "Hello there!";
        bobMessage = new Message(bobInstant, bobSenderText, bobContent);

        // mike's message, not yet in conversation
        mikeLine = "1448470905 mike how are you?";
        mikeInstant = Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905"));
        mikeSenderText = "mike";
        mikeSender = new Sender(mikeSenderText);
        mikeSender.setMessageCount(1);
        mikeContent = "how are you?";
        mikeMessage = new Message(mikeInstant, mikeSenderText, mikeContent);

        // frequency map
        exampleFrequency.add(bobSender);
        exampleFrequency.add(mikeSender);
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

    @Test
    public void getActiveUsers() {
        // bob's message count should be 1
        assertEquals(1L, (long) exampleFrequency.get(0).getMessageCount());

        // mike's message count should be 1
        assertEquals(1L, (long) exampleFrequency.get(1).getMessageCount());
    }
}