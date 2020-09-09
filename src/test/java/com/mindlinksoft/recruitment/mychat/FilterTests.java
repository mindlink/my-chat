package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.Constructs.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Constructs.Message;
import com.mindlinksoft.recruitment.mychat.Utilities.Filter.FilterHide;
import com.mindlinksoft.recruitment.mychat.Utilities.Filter.FilterKeyword;
import com.mindlinksoft.recruitment.mychat.Utilities.Filter.FilterName;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FilterTests {


    private ConversationDefault populateTestConversation() {

        List<Message> messages = new ArrayList<>();

        messages.add(new Message(Instant.ofEpochSecond(1440000000), "Jerry", "Hi how are you today?"));
        messages.add(new Message(Instant.ofEpochSecond(1440000005), "Bob", "Yeah I am good. Thanks for asking."));
        messages.add(new Message(Instant.ofEpochSecond(1440000010), "Sam", "I feel great."));
        messages.add(new Message(Instant.ofEpochSecond(1440000015), "Jerry", "That's good to hear! Should we continue then?"));

        return new ConversationDefault("A Conversation", messages);
    }

    @Test
    public void testBlackList() {
        FilterHide filterHide = new FilterHide();

        ConversationDefault c = filterHide.populateAndReturn(populateTestConversation(), "hi,good,then");

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1440000000));
        assertEquals(ms[0].senderId, "Jerry");
        assertEquals(ms[0].content, "*redacted*how are you today?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1440000005));
        assertEquals(ms[1].senderId, "Bob");
        assertEquals(ms[1].content, "Yeah I am*redacted*Thanks for asking.");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1440000010));
        assertEquals(ms[2].senderId, "Sam");
        assertEquals(ms[2].content, "I feel great.");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1440000015));
        assertEquals(ms[3].senderId, "Jerry");
        assertEquals(ms[3].content, "That's*redacted*to hear! Should we continue*redacted*");
    }

    @Test
    public void testKeyword() {
        FilterKeyword filterKeyword = new FilterKeyword();

        ConversationDefault c = filterKeyword.populateAndReturn(populateTestConversation(), "good");

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1440000005));
        assertEquals(ms[0].senderId, "Bob");
        assertEquals(ms[0].content, "Yeah I am good. Thanks for asking.");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1440000015));
        assertEquals(ms[1].senderId, "Jerry");
        assertEquals(ms[1].content, "That's good to hear! Should we continue then?");
    }

    @Test
    public void testName() {
        FilterName filterName = new FilterName();

        ConversationDefault c = filterName.populateAndReturn(populateTestConversation(), "Jerry");

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1440000000));
        assertEquals(ms[0].senderId, "Jerry");
        assertEquals(ms[0].content, "Hi how are you today?");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1440000015));
        assertEquals(ms[1].senderId, "Jerry");
        assertEquals(ms[1].content, "That's good to hear! Should we continue then?");
    }
}
