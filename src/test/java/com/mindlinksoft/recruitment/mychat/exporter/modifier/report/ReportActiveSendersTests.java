package com.mindlinksoft.recruitment.mychat.exporter.modifier.report;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Sender;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReportActiveSendersTests {

    private Conversation conversation;
    private List<Message> messages;

    private Conversation expectedConversation;
    private List<Message> expectedMessages;

    private List<Sender> mostActiveUsers;

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
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "It is 4738 9382 3927 1920, it's a Barclays account"));
        expectedMessages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "NICE! We'll get us some good pie. Call me on 07812345678 and I'll tell you our safe house"));

        expectedConversation.setMessages(expectedMessages);

        // set up list of senders with new and old text
        Sender bob = new Sender("bob", 3);
        Sender mike = new Sender("mike", 2);
        Sender angus = new Sender("angus", 2);
        mostActiveUsers = List.of(bob, mike, angus);
    }

    @Test
    public void test() {
        // report number of users
        ReportActiveSenders reportActiveSenders = new ReportActiveSenders(conversation);
        Conversation result = reportActiveSenders.modify();
        List<Sender> resultSenders = result.getActiveUsers();

        // expect active senders to have same size i.e. 3 senders
        assertEquals(mostActiveUsers.size(), resultSenders.size());

        System.out.println(resultSenders);
        // expect bob to be first and have 3 messages
        assertEquals(resultSenders.get(0).getMessageCount(), mostActiveUsers.get(0).getMessageCount());
        assertEquals(resultSenders.get(0).getSenderText(), mostActiveUsers.get(0).getSenderText());

        // expect the rest to have two messages
        // (unstable sort due to frequency map)
        assertEquals(resultSenders.get(1).getMessageCount(), mostActiveUsers.get(1).getMessageCount());
        assertEquals(resultSenders.get(2).getMessageCount(), mostActiveUsers.get(2).getMessageCount());
    }
}
