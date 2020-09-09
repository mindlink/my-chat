package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.Constructs.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Constructs.Message;
import com.mindlinksoft.recruitment.mychat.Utilities.ReadWrite;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReadWriteTests {
    private ConversationDefault populateTestConversation() {

        List<Message> messages = new ArrayList<>();

        messages.add(new Message(Instant.ofEpochSecond(1440000000), "Tom", "hello there"));
        messages.add(new Message(Instant.ofEpochSecond(1440000005), "Jerry", "Hi."));
        messages.add(new Message(Instant.ofEpochSecond(1440000010), "James", "Hi everyone!"));

        return new ConversationDefault("Test Conversation", messages);
    }

    @Test
    public void readFileTest() throws Exception {
        ReadWrite readWrite = new ReadWrite();

        ConversationDefault c = readWrite.readConversation("src/test/java/com/mindlinksoft/recruitment/mychat/test.txt");

        assertEquals("Test Conversation", c.name);

        assertEquals(3, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1440000000));
        assertEquals(ms[0].senderId, "Tom");
        assertEquals(ms[0].content, "hello there");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1440000005));
        assertEquals(ms[1].senderId, "Terry");
        assertEquals(ms[1].content, "Hi.");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1440000010));
        assertEquals(ms[2].senderId, "James");
        assertEquals(ms[2].content, "Hi everyone!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void readIllegalArgumentExceptionException() throws Exception {
        ReadWrite readWrite = new ReadWrite();
        readWrite.readConversation("src/test/java/com/mindlinksoft/recruitment/mychat");
    }

    @Test
    public void writeFileTest() throws Exception {
        ReadWrite readWrite = new ReadWrite();
        readWrite.writeConversation(populateTestConversation(), "src/test/java/com/mindlinksoft/recruitment/mychat/test.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeIllegalArgumentExceptionException() throws Exception {
        ReadWrite readWrite = new ReadWrite();
        readWrite.readConversation("src/test/java/com/mindlinksoft/recruitment/mychat");
    }
}
