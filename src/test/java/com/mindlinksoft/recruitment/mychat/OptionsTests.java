package com.mindlinksoft.recruitment.mychat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import org.junit.Test;

public class OptionsTests {
    /**
     * Test for illegal input
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testOptionsAreCorrectlyLoaded() throws FileNotFoundException, IOException {
        Conversation conversation = generateFakeConversation();
    }

    /**
     * Test for illegal input
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testFilterByUser() throws FileNotFoundException, IOException {
        Conversation conversation = generateFakeConversation();
    }

    public Conversation generateFakeConversation() {
        List<Message> messages = new ArrayList<Message>();

        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "Ralof",
                "Hey, you. You’re finally awake. You were trying to cross the border, right? Walked"
                        + " right into that Imperial ambush, same as us, and that thief over there."));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "Lokir",
                "Damn you Stormcloaks. Skyrim was fine until you came along. Empire was nice and lazy."
                        + "If they hadn’t been looking for you, I could’ve stolen that horse and been half way"
                        + "to Hammerfell. You there. You and me — we should be here. It’s these Stormcloaks the"
                        + "Empire wants."));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "Ralof",
                "We’re all brothers and sisters in binds now, thief."));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")), "Imperial Soldier",
                "Shut up back there!"));

        return new Conversation("Imperial wagon Chat", messages);

    }
}
