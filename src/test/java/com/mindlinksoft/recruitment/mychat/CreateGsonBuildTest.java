package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.constructs.Conversation;
import com.mindlinksoft.recruitment.mychat.constructs.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link CreateGsonBuild}.
 */
public class CreateGsonBuildTest
{
    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testConversionJsonToString()
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1440010000), "jeremy", "This is the best thing, in the world there..."));
        messages.add(new Message(Instant.ofEpochSecond(1440010006), "richard", "I've crashed!"));
        messages.add(new Message(Instant.ofEpochSecond(1440010012), "james", "And now... 25 mph! Wow that's quick."));
        Conversation conversation = new Conversation("Test Conversation", messages);

        CreateGsonBuild createGsonBuild = new CreateGsonBuild();
        String jsonStringExpected = "{\"name\":\"Test Conversation\",\"messages\":[{\"timestamp\":1440010000,\"senderId\":\"jeremy\",\"content\":\"This is the best thing, in the world there...\"},{\"timestamp\":1440010006,\"senderId\":\"richard\",\"content\":\"I\\u0027ve crashed!\"},{\"timestamp\":1440010012,\"senderId\":\"james\",\"content\":\"And now... 25 mph! Wow that\\u0027s quick.\"}]}";
        assertEquals(jsonStringExpected, createGsonBuild.convert(conversation));
    }
}