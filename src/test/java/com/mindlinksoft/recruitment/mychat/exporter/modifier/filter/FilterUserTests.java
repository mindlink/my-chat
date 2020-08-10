package com.mindlinksoft.recruitment.mychat.exporter.modifier.filter;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;

import org.junit.Before;

public class FilterUserTests {
    
    private Conversation conversation;
    private List<Message> messages;

    private Conversation expectedConversation;
    private List<Message> expectedMessages;

    private String filteredSender;
    private Filter filterUser;
    
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
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "No, just want to know if there's anybody else in the pie society..."));

        expectedConversation.setMessages(expectedMessages);

        // set up filtered sender
        filteredSender = "bob";
    }
    public void filter() {
        // filter out "bob" from conversation
        filterUser = new FilterUser(conversation, filteredSender);
        Conversation result = filterUser.filter();
        List<Message> resultMessages = result.getMessages();

        // expect conversation to have 3 messages
        assertEquals(expectedMessages.size(), resultMessages.size());

        // expect all messages to only have bob's messages
        for (int i = 0; i < expectedMessages.size(); i++) {
            assertEquals(expectedMessages.get(i), resultMessages.get(i));
        }
    }
}