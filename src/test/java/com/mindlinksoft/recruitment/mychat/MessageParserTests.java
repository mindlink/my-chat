package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.message.Message;
import com.mindlinksoft.recruitment.mychat.message.MessageParser;
import java.time.Instant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the message parser unit.
 *
 * @author Gabor
 */
public class MessageParserTests {

    private MessageParser messageParse;
    private String rawMessage;
    private Message message;

    @Before
    public void setUp() throws Exception {
        String timestamp = "1448470906";
        String senderId = "bob";
        String content = "I'm good thanks, do you like pie?";

        rawMessage = timestamp + " " + senderId + " " + content;
        message = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), senderId, content);

        messageParse = new MessageParser();
    }

    @Test
    public void testMessageParser() throws Exception {
        Message result = messageParse.parse(rawMessage);
        Assert.assertEquals(message, result);
    }
}
