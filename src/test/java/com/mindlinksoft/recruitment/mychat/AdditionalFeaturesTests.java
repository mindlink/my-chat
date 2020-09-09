package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.Constructs.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Constructs.ConversationReport;
import com.mindlinksoft.recruitment.mychat.Constructs.Message;
import com.mindlinksoft.recruitment.mychat.Constructs.User;
import com.mindlinksoft.recruitment.mychat.Utilities.AdditionalFeatures.Details;
import com.mindlinksoft.recruitment.mychat.Utilities.AdditionalFeatures.Obfuscate;
import com.mindlinksoft.recruitment.mychat.Utilities.AdditionalFeatures.Report;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AdditionalFeaturesTests {

    private ConversationDefault populateTestConversation() {

        List<Message> messages = new ArrayList<>();

        messages.add(new Message(Instant.ofEpochSecond(1440000000), "Jerry", "You can send your credit card details and your phone number here, it is safe."));
        messages.add(new Message(Instant.ofEpochSecond(1440000005), "Bob", "Alright, My phone number is: 07777777777 and my credit card number is: 1234-1234-1234-1234"));
        messages.add(new Message(Instant.ofEpochSecond(1440000010), "Sam", "mine are +447777777777 and 1234 1234 1234 1234"));
        messages.add(new Message(Instant.ofEpochSecond(1440000015), "Jerry", "That's good to hear! Should we continue then?"));
        messages.add(new Message(Instant.ofEpochSecond(1440000020), "Sam", "yes"));
        messages.add(new Message(Instant.ofEpochSecond(1440000025), "Sam", "please"));

        return new ConversationDefault("A Conversation", messages);
    }

    @Test
    public void RedactDetails() {
        Details details = new Details();

        ConversationDefault c = details.populateAndReturn(populateTestConversation());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1440000000));
        assertEquals(ms[0].senderId, "Jerry");
        assertEquals(ms[0].content, "You can send your credit card details and your phone number here, it is safe.");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1440000005));
        assertEquals(ms[1].senderId, "Bob");
        assertEquals(ms[1].content, "Alright, My phone number is: *redacted* and my credit card number is: *redacted*");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1440000010));
        assertEquals(ms[2].senderId, "Sam");
        assertEquals(ms[2].content, "mine are *redacted* and *redacted*");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1440000015));
        assertEquals(ms[3].senderId, "Jerry");
        assertEquals(ms[3].content, "That's good to hear! Should we continue then?");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1440000020));
        assertEquals(ms[4].senderId, "Sam");
        assertEquals(ms[4].content, "yes");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1440000025));
        assertEquals(ms[5].senderId, "Sam");
        assertEquals(ms[5].content, "please");
    }

    @Test
    public void Obfuscate() {
        Obfuscate obfuscate = new Obfuscate();

        ConversationDefault c = obfuscate.processAndReturn(populateTestConversation());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1440000000));
        assertTrue(!ms[0].senderId.contains("[a-zA-Z]+") && ms[0].senderId.length() == 5);
        assertEquals(ms[0].content, "You can send your credit card details and your phone number here, it is safe.");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1440000005));
        assertTrue(!ms[1].senderId.contains("[a-zA-Z]+") && ms[0].senderId.length() == 5);
        assertEquals(ms[1].content, "Alright, My phone number is: 07777777777 and my credit card number is: 1234-1234-1234-1234");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1440000010));
        assertTrue(!ms[2].senderId.contains("[a-zA-Z]+") && ms[0].senderId.length() == 5);
        assertEquals(ms[2].content, "mine are +447777777777 and 1234 1234 1234 1234");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1440000015));
        assertTrue(!ms[3].senderId.contains("[a-zA-Z]+") && ms[0].senderId.length() == 5);
        assertEquals(ms[3].content, "That's good to hear! Should we continue then?");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1440000020));
        assertTrue(!ms[4].senderId.contains("[a-zA-Z]+") && ms[0].senderId.length() == 5);
        assertEquals(ms[4].content, "yes");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1440000025));
        assertTrue(!ms[5].senderId.contains("[a-zA-Z]+") && ms[0].senderId.length() == 5);
        assertEquals(ms[5].content, "please");
    }

    @Test
    public void Report() {
        Report report = new Report();

        ConversationReport c = report.processAndReturn(populateTestConversation());

        User[] ms = new User[c.users.size()];
        c.users.toArray(ms);

        assertEquals(ms[0].messageCount, new Integer(3));
        assertEquals(ms[0].senderId, "Sam");

        assertEquals(ms[1].messageCount, new Integer(2));
        assertEquals(ms[1].senderId, "Jerry");

        assertEquals(ms[2].messageCount, new Integer(1));
        assertEquals(ms[2].senderId, "Bob");

    }
}
