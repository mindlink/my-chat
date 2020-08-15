package com.mindlinksoft.recruitment.mychat.exporter.modifier.hide;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;

import org.junit.Before;
import org.junit.Test;

public class HideKeyWordTests {

    private Conversation conversation;
    private List<Message> messages;

    private Conversation expectedConversation;
    private List<Message> expectedMessages;

    private List<String> keyWord;
    private HideKeyWord hideKeyWord;

    @Before
    public void setUp() {
        // set up sample conversation
        conversation = new Conversation();
        conversation.setName("My Conversation");

        messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus..."));
        messages.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "No, just want to know if there's anybody else in the pie society..."));
        messages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head pie eater there..."));

        conversation.setMessages(messages);

        // set up expected conversation
        expectedConversation = new Conversation();
        expectedConversation.setName("My Conversation");

        expectedMessages = new ArrayList<>();
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello *redacted*!"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like *redacted*?"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus..."));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some *redacted*?"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "No, just want to know if *redacted*'s anybody else in the *redacted* society..."));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head *redacted* eater *redacted*..."));

        expectedConversation.setMessages(expectedMessages);

        // set up hidden key words
        keyWord = List.of("pie", "there");
    }

    @Test
    public void hide() {
        // hide messages with "pie"
        hideKeyWord = new HideKeyWord(conversation, keyWord);
        Conversation result = hideKeyWord.hide();
        List<Message> resultMessages = result.getMessages();

        // expect conversation to have same size i.e. 7 messages
        assertEquals(expectedMessages.size(), resultMessages.size());

        // expect all messages to have its content be modified if it contains pie and there
        for (int i = 0; i < expectedMessages.size(); i++) {
            assertEquals(expectedMessages.get(i).getSenderText(), resultMessages.get(i).getSenderText());
            assertEquals(expectedMessages.get(i).getContent(), resultMessages.get(i).getContent());
            assertEquals(expectedMessages.get(i).getTimestamp(), resultMessages.get(i).getTimestamp());
        }
    }
}