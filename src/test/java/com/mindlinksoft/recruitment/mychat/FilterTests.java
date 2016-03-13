package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.filter.secondary.BlackListFilter;
import com.mindlinksoft.recruitment.mychat.filter.primary.KeywordFilter;
import com.mindlinksoft.recruitment.mychat.filter.primary.SenderFilter;
import com.mindlinksoft.recruitment.mychat.filter.secondary.ObfuscateFilter;
import com.mindlinksoft.recruitment.mychat.message.Message;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the various filter units.
 *
 * @author Gabor
 */
public class FilterTests {

    private Conversation conversation;

    private SenderFilter senderFilter;
    private KeywordFilter keywordFilter;
    private BlackListFilter blackListFilter;
    private ObfuscateFilter obfuscateFilter;

    @Before
    public void setUp() throws Exception {
        List<Message> messages = new ArrayList<>();

        String timestamp = "1448470906";
        String senderId = "bob";
        String content = "I'm good thanks, do you like pie?";

        Message message = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), senderId, content);
        messages.add(message);

        timestamp = "1448470910";
        senderId = "mike";
        content = "no, let me ask Angus...";

        message = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), senderId, content);
        messages.add(message);

        timestamp = "1448470912";
        senderId = "angus";
        content = "Hell yes! Are we buying some pie?";

        message = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), senderId, content);
        messages.add(message);

        timestamp = "1448470914";
        senderId = "bob";
        content = "No, just want to know if there's anybody else in the pie society...";

        message = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), senderId, content);
        messages.add(message);

        conversation = new Conversation("Test conversation", messages);

        senderFilter = new SenderFilter("angus");
        keywordFilter = new KeywordFilter("pie");
        blackListFilter = new BlackListFilter("good,hell, pie");
        obfuscateFilter = new ObfuscateFilter();
    }

    @Test
    public void testSenderFilter() throws Exception {
        senderFilter.apply(conversation);
        // should only give back one message
        Assert.assertTrue(conversation.getMessages().size() == 1);
        // message sender id should be bob
        Assert.assertTrue(StringUtils.equals(conversation.getMessages().iterator().next().getSenderId(), "angus"));
    }

    @Test
    public void testKeywordFilter() throws Exception {
        keywordFilter.apply(conversation);
        // should give back 3 messages
        Assert.assertTrue(conversation.getMessages().size() == 3);
        // messages should be the following
        for (Message message : conversation.getMessages()) {
            if (StringUtils.equals(message.getContent(), "I'm good thanks, do you like pie?")) {
                Assert.assertTrue(true);
            }
            if (StringUtils.equals(message.getContent(), "no, let me ask Angus...")) {
                Assert.assertTrue(false);
            }
            if (StringUtils.equals(message.getContent(), "Hell yes! Are we buying some pie?")) {
                Assert.assertTrue(true);
            }
            if (StringUtils.equals(message.getContent(), "No, just want to know if there's anybody else in the pie society...")) {
                Assert.assertTrue(true);
            }
        }
    }

    @Test
    public void testBlackListFilter() throws Exception {
        blackListFilter.apply(conversation);
        // should give back 4 messages
        Assert.assertTrue(conversation.getMessages().size() == 4);
        // messages should be the following
        List<Message> messages = new ArrayList<>(conversation.getMessages());
        System.out.println("");
        Assert.assertEquals("I'm *redacted* thanks, do you like *redacted*?", messages.get(0).getContent());
        Assert.assertEquals("no, let me ask Angus...", messages.get(1).getContent());
        Assert.assertEquals("*redacted* yes! Are we buying some *redacted*?", messages.get(2).getContent());
        Assert.assertEquals("No, just want to know if there's anybody else in the *redacted* society...", messages.get(3).getContent());
    }

    @Test
    public void testObfuscatedFilter() throws Exception {
        obfuscateFilter.apply(conversation);
        // should give back 4 messages
        Assert.assertTrue(conversation.getMessages().size() == 4);
        // messages should be the following
        List<Message> messages = new ArrayList<>(conversation.getMessages());

        String obfuscatedBob1 = "";
        String obfuscatedBob2 = "";
        for (Message message : messages) {
            // get messages for bob
            if (message.getContent().equals("I'm good thanks, do you like pie?")) {
                obfuscatedBob1 = message.getSenderId();
            }
            if (message.getContent().equals("No, just want to know if there's anybody else in the pie society...")) {
                obfuscatedBob2 = message.getSenderId();
            }
        }
        // the obfuscated names for bob should be equal
        Assert.assertEquals(obfuscatedBob1, obfuscatedBob2);
    }
}
