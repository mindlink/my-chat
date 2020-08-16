package com.mindlinksoft.recruitment.mychat.exporter.modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.filter.FilterKeyWord;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.filter.FilterUser;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.hide.HideCreditCardPhoneNumbers;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.hide.HideKeyWord;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.obfuscate.ObfuscateUsers;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConversationModifierTests {

    ConversationModifier conversationModifier;

    Conversation conversation;
    List<Message> messages;

    Conversation expectedConversation;
    List<Message> expectedMessages;

    @Before
    public void setUp() {
        // set up sample conversation
        String name = ("My Conversation");

        messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus..."));
        messages.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "No, just want to know if there's anybody else in the pie society..."));
        messages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head pie eater there..."));

        conversation = new Conversation(name, messages);

        // set up expected conversation
        expectedMessages = new ArrayList<>();
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like *redacted*?"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some *redacted*?"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "No, just want to know if there's anybody else in the *redacted* society..."));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head *redacted* eater there..."));

        expectedConversation = new Conversation(name, expectedMessages);

        conversationModifier = new ConversationModifier(conversation,
                Set.of(Modifier.HIDE_KEYWORD, Modifier.FILTER_USER),
                Map.of(Modifier.HIDE_KEYWORD, List.of("pie"), Modifier.FILTER_USER, List.of("bob", "angus")));
    }

    @Test
    public void modify() {
        // should return conversation with messages sent by bob and angus, and with all key words i.e. pie redacted
        Conversation result = conversationModifier.modify();
        List<Message> resultMessages = result.getMessages();

        // expect conversation to have same size i.e. 4 messages
        assertEquals(expectedMessages.size(), resultMessages.size());

        // expect all messages to only have messages with "pie" in it
        for (int i = 0; i < expectedMessages.size(); i++) {
            assertEquals(expectedMessages.get(i).getSenderText(), resultMessages.get(i).getSenderText());
            assertEquals(expectedMessages.get(i).getContent(), resultMessages.get(i).getContent());
            assertEquals(expectedMessages.get(i).getTimestamp(), resultMessages.get(i).getTimestamp());
        }
    }

    @Test
    public void chooseModifier() {
        // return relevant class according to the given modifier type
        ModifierBase result = conversationModifier.chooseModification(Modifier.FILTER_KEYWORD, conversation);
        assertTrue(result instanceof FilterKeyWord);

        result = conversationModifier.chooseModification(Modifier.FILTER_USER, conversation);
        assertTrue(result instanceof FilterUser);

        result = conversationModifier.chooseModification(Modifier.HIDE_KEYWORD, conversation);
        assertTrue(result instanceof HideKeyWord);

        result = conversationModifier.chooseModification(Modifier.HIDE_CREDIT_CARD_AND_PHONE_NUMBERS, conversation);
        assertTrue(result instanceof HideCreditCardPhoneNumbers);

        result = conversationModifier.chooseModification(Modifier.OBFUSCATE_USERS, conversation);
        assertTrue(result instanceof ObfuscateUsers);
    }
}