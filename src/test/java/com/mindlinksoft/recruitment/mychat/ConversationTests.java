package com.mindlinksoft.recruitment.mychat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ConversationTests {
    
    String exampleTitle;
    List<Message> exampleMessages;
    Map<String, Sender> exampleSenders;

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

    Message exampleMessage;
    
    Conversation exampleConversation;

    @Before
    public void setUp() {
        // exampleConversation arguments
        exampleTitle = "My Conversation";
        exampleMessages = new ArrayList<>();
        exampleSenders = new HashMap<>();

        // conversation from above arguments i.e. no messages/sender added
        exampleConversation = new Conversation(exampleTitle, exampleMessages, exampleSenders);

        // bob's message, not yet in conversation
        bobLine = "1448470901 bob Hello there!";
        bobInstant = Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901"));
        bobSender = new Sender("bob");
        bobContent = "Hello there!";
        bobMessage = new Message(bobInstant, bobSender, bobContent);

        // mike's message, not yet in conversation
        mikeLine = "1448470905 mike how are you?";
        mikeInstant = Instant.ofEpochSecond(Long.parseUnsignedLong("1448470905"));
        mikeSender = new Sender("mike");
        mikeContent = "how are you?";
        mikeMessage = new Message(mikeInstant, mikeSender, mikeContent);
    }

    @Test
    public void addMessage() {
        // add new message into message list
        exampleConversation.addMessage(mikeMessage);

        // there should be one new message in conversation
        int actualSize = exampleConversation.getMessages().size();
        assertEquals(1, actualSize);

        // add 4 more messages, to a total of 5
        for (int i = 0; i < 4; i++) {
            exampleConversation.addMessage(mikeMessage);
        }

        // size should now be 5
        actualSize = exampleConversation.getMessages().size();
        assertEquals(5, actualSize);
    }

    @Test
    public void getMessage() {
        // place bob's message first, index should be 0
        exampleConversation.addMessage(bobMessage);
        assertEquals(bobMessage, exampleConversation.getMessage(0));

        // place mike's message next, index should be 1
        exampleConversation.addMessage(mikeMessage);
        assertEquals(mikeMessage, exampleConversation.getMessage(1));

        // bob's message should still be index 0
        assertEquals(bobMessage, exampleConversation.getMessage(0));
    }

    @Test
    public void deleteMessage() {
        // place bob's and mike's messages into conversation
        exampleConversation.addMessage(bobMessage);
        exampleConversation.addMessage(mikeMessage);

        // delete message at index 0 i.e. bobMessage
        exampleConversation.deleteMessage(0);

        // size should now be 1
        int actualSize = exampleConversation.getMessages().size();
        assertEquals(1, actualSize);

        // mike's message should now be index 0
        assertEquals(mikeMessage, exampleConversation.getMessage(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void deleteMessageEmpty() {
        // removing messages from empty list should cause exception
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void deleteMessageOutOfBounds() {
        // place bob's and mike's messages into conversation
        exampleConversation.addMessage(bobMessage);
        exampleConversation.addMessage(mikeMessage);

        // removing message at an index out of bounds should cause exception
        exampleConversation.deleteMessage(2);
    }

    @Test
    public void putSender() {
        // place bobSender and MikeSender into map
        exampleConversation.putSender(bobSender.getSenderText(), bobSender);
        exampleConversation.putSender(mikeSender.getSenderText(), mikeSender);

        // map size should now be 2
        int actualSize = exampleConversation.getSenderMap().size();
        assertEquals(2, actualSize);
    }

    @Test(expected = IllegalStateException.class)
    public void putSenderAlreadyExistsException() {
        // place bobSender first
        exampleConversation.putSender(bobSender.getSenderText(), bobSender);

        // placing a sender that already exists should cause IllegalStateException
        exampleConversation.putSender(bobSender.getSenderText(), bobSender);
    }

    @Test
    public void hasSender() {
        // place bobSender and MikeSender into map
        exampleConversation.putSender(bobSender.getSenderText(), bobSender);
        exampleConversation.putSender(mikeSender.getSenderText(), mikeSender);

        // placed Senders should return true
        assertTrue(exampleConversation.hasSender(bobSender.getSenderText()));
        assertTrue(exampleConversation.hasSender(mikeSender.getSenderText()));

        // unencountered Sender should return false
        String senderStringNotInMap = "Jonathan";
        assertFalse(exampleConversation.hasSender(senderStringNotInMap));
    }

    @Test
    public void getSender() {
        // place bobSender and MikeSender into map
        exampleConversation.putSender(bobSender.getSenderText(), bobSender);
        exampleConversation.putSender(mikeSender.getSenderText(), mikeSender);

        // placed senders can be retrieved
        assertEquals(bobSender, exampleConversation.getSender(bobSender.getSenderText()));
        assertEquals(mikeSender, exampleConversation.getSender(mikeSender.getSenderText()));
    }

    @Test(expected = NoSuchElementException.class)
    public void getSenderDoesNotExist() {
        // getting a sender not in senderMap should throw an exception
        exampleConversation.getSender("tom");
    }

    @Test
    public void getSenderOrPut() {
        // place bob into map
        exampleConversation.putSender(bobSender.getSenderText(), bobSender);

        // getSenderOrPut("bob") should retrieve same bobSender object
        assertEquals(bobSender, exampleConversation.getSenderOrPut(bobSender.getSenderText()));

        // it should return a new object if the sender has not been encountered before
        assertNotEquals(mikeSender, exampleConversation.getSenderOrPut(mikeSender.getSenderText()));
    }

    @Test
    public void removeSender() {
        // place bob into map
        exampleConversation.putSender(bobSender.getSenderText(), bobSender);

        // remove bob from map
        exampleConversation.removeSender(bobSender.getSenderText());

        // map should now be size 0
        assertEquals(0, exampleConversation.getSenderMap().size());

        // map should not have bob
        assertFalse(exampleConversation.hasSender(bobSender.getSenderText()));
    }

    @Test(expected = NoSuchElementException.class)
    public void removeSenderDoesNotExist() {
        // removing sender that is not in map should cause exception
        exampleConversation.removeSender(bobSender.getSenderText());
    }
}