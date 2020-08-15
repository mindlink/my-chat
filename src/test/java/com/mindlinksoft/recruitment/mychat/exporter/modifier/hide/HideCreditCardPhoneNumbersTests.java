package com.mindlinksoft.recruitment.mychat.exporter.modifier.hide;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;

import org.junit.Before;
import org.junit.Test;

public class HideCreditCardPhoneNumbersTests {

    private Conversation conversation;
    private List<Message> messages;

    private Conversation expectedConversation;
    private List<Message> expectedMessages;

    private Hide hideCreditCardPhoneNumbers;

    @Before
    public void setUp() {
        // set up sample conversation
        conversation = new Conversation();
        conversation.setName("My Conversation");

        messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there, I am 15!"));
        messages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "No one cares??"));
        messages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm going to give you my credit card number"));
        messages.add(new Message(Instant.ofEpochSecond(1448470910), "mike", "Uhh, I don't think that's a good idea my friend"));
        messages.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Actually, it is! What's the number, friend?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "It is 4738 9382 3927 1920, it's a Barclays account"));
        messages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "NICE! We'll get us some good pie. Call me on 07812345678 and I'll tell you our safe house"));

        conversation.setMessages(messages);

        // set up expected conversation
        expectedConversation = new Conversation();
        expectedConversation.setName("My Conversation");

        expectedMessages = new ArrayList<>();
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there, I am 15!"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "No one cares??"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm going to give you my credit card number"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470910), "mike", "Uhh, I don't think that's a good idea my friend"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Actually, it is! What's the number, friend?"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "It is *redacted*, it's a Barclays account"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "NICE! We'll get us some good pie. Call me on *redacted* and I'll tell you our safe house"));

        expectedConversation.setMessages(expectedMessages);
    }

    @Test
    public void hide() {
        // hide messages with credit card numbers
        hideCreditCardPhoneNumbers = new HideCreditCardPhoneNumbers(conversation);
        Conversation result = hideCreditCardPhoneNumbers.hide();
        List<Message> resultMessages = result.getMessages();

        // expect conversation to have same size i.e. 7 messages
        assertEquals(expectedMessages.size(), resultMessages.size());

        // expect all messages to have its content be modified if it phone numbers or credit card numbers
        for (int i = 0; i < expectedMessages.size(); i++) {
            assertEquals(expectedMessages.get(i).getSenderText(), resultMessages.get(i).getSenderText());
            assertEquals(expectedMessages.get(i).getContent(), resultMessages.get(i).getContent());
            assertEquals(expectedMessages.get(i).getTimestamp(), resultMessages.get(i).getTimestamp());
        }
    }
}