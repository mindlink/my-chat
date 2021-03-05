package com.mindlinksoft.recruitment.juliankubelec.mychat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

public class TestHelper {
    /**
     * Helper function to initialise a mock of the conversation from chat.txt
     * @return the original conversation
     */
    public static ConversationBuilder prepareConversation()
    {
        String name = "My Conversation";
        Collection<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1448470901),
                "bob", "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(1448470905),
                "mike", "how are you?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470906),
                "bob", "I'm good thanks, do you like pie?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470910),
                "mike", "no, let me ask Angus..."));
        messages.add(new Message(Instant.ofEpochSecond(1448470912),
                "angus", "Hell yes! Are we buying some pie?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470914),
                "bob", "No, just want to know if there's anybody else in the pie society..."));
        messages.add(new Message(Instant.ofEpochSecond(1448470915),
                "angus", "YES! I'm the head pie eater there..."));

        Conversation conversation = new Conversation(name, messages);

        return new ConversationBuilder(conversation);
    }
}
