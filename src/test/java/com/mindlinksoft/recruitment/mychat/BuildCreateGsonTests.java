package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.Constructs.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Constructs.Message;
import com.mindlinksoft.recruitment.mychat.Utilities.BuildCreateGson;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BuildCreateGsonTests {
    private ConversationDefault populateTestConversation() {

        List<Message> messages = new ArrayList<>();

        messages.add(new Message(Instant.ofEpochSecond(1440000000), "Tom", "hello there"));
        messages.add(new Message(Instant.ofEpochSecond(1440000005), "Jerry", "Hi."));
        messages.add(new Message(Instant.ofEpochSecond(1440000010), "James", "Hi everyone!"));

        return new ConversationDefault("Test Conversation", messages);
    }

    @Test
    public void testConvertionJsonToString() {
        BuildCreateGson buildCreateGson = new BuildCreateGson();

        String convertion = buildCreateGson.convert(populateTestConversation());
        assertEquals(convertion, "{\"name\":\"Test Conversation\",\"messages\":[{\"content\":\"hello there\",\"timestamp\":1440000000,\"senderId\":\"Tom\"},{\"content\":\"Hi.\",\"timestamp\":1440000005,\"senderId\":\"Jerry\"},{\"content\":\"Hi everyone!\",\"timestamp\":1440000010,\"senderId\":\"James\"}]}");
    }
}
