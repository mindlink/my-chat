package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Features;
import com.mindlinksoft.recruitment.mychat.model.Message;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConversationFeaturesTest {

    public Conversation createMockConversation() throws Exception {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus..."));
        messages.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "No, just want to know if there's anybody else in the pie society..."));
        messages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head pie eater there..."));
        return new Conversation("My Conversation",messages);
    }
    public Conversation ConversationExportMock(Conversation c, Features f) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new ConversationExporterConfiguration("","",
                f);
        c = exporter.applyRequirements(c,config);
        return c;
    }

    /**
     * Tests if the conversation is filtered by a specific user
     */
    @Test
    public void testFilterBySpecificUser() throws Exception{
        Conversation c = createMockConversation();
        Features f = new Features("bob",null,null,false,false);
        ConversationExportMock(c,f);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        for (Message m : ms) assertEquals(m.senderId, "bob");

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");
    }

    /**
     * Tests if the conversation is filtered by a specific word
     */
    @Test
    public void testFilterByWord() throws Exception {
        Conversation c = createMockConversation();
        Features f = new Features(null,"there",null,false,false);
        ConversationExportMock(c,f);

        assertEquals(3, c.messages.size());
        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        for (Message m : ms) Assert.assertTrue(m.content.contains("there"));

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[2].senderId, "angus");
        assertEquals(ms[2].content, "YES! I'm the head pie eater there...");
    }

    /**
     * Tests if the conversation has the word specified redacted
     */
    @Test
    public void testRedactionOfWords() throws Exception {
        Conversation c = createMockConversation();
        Features f = new Features(null,null,"pie",false,false);
        ConversationExportMock(c,f);

        assertEquals(7, c.messages.size());
        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].senderId, "mike");
        assertEquals(ms[1].content, "how are you?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "I'm good thanks, do you like *redacted*?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some *redacted*?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the *redacted* society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head *redacted* eater there...");

    }

    /**
     * Tests if the userIDs are obfuscated
     */
    @Test
    public void testUserIDsObfuscated() throws Exception {
        Conversation c = createMockConversation();
        Features f = new Features(null,null,null,true,false);
        ConversationExportMock(c,f);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals("Ym9i", ms[0].senderId);
        assertEquals("bob",new String(Base64.getDecoder().decode("Ym9i")));

        assertEquals("bWlrZQ==", ms[1].senderId);
        assertEquals("mike",new String(Base64.getDecoder().decode("bWlrZQ==")));

        assertEquals("YW5ndXM=", ms[4].senderId);
        assertEquals("angus",new String(Base64.getDecoder().decode("YW5ndXM=")));
    }

    /**
     * Tests if a Activity Report is done
     */
    @Test
    public void testActivityReport() throws Exception{
        Conversation c = createMockConversation();
        Features f = new Features(null,null,null,false,true);
        Conversation conversationWithReport = ConversationExportMock(c,f);
        Map<String,Integer> report = conversationWithReport.report;

        assertNotNull(report);
        assertEquals(report.toString(),"{bob=3, angus=2, mike=2}");
    }

    /**
     * Tests both features
     */
    @Test
    public void testEssentialAndAdditionalFeaturesCombined() throws Exception{
        Conversation c = createMockConversation();
        Features f = new Features("bob",null,null,false,true);
        Conversation conversationWithReport = ConversationExportMock(c,f);
        Map<String,Integer>report = conversationWithReport.report;

        Message[] ms = new Message[conversationWithReport.messages.size()];
        conversationWithReport.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");

        assertNotNull(report);
        assertEquals(report.toString(),"{bob=3}");
    }

}
