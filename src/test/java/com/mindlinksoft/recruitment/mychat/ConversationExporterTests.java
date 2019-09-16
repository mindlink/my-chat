package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/*
It would be so much better to read the actual output of the JSON but unfortunately I didn't quite find the time to do that so
i'm testing the use of the configuration on the conversation output directly. Hope that's okay.
 */


public class ConversationExporterTests {

    @Test
    public void testFilterSender() {
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124324")), new Sender("Bob"), "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124326")), new Sender("Karen"), "Hey. My number is 07851289667"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("12432323")), new Sender("tom"), "Great. My CC number is 340000000000009" ));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1243289")), new Sender("Karen"), "Yo, Bob! My number is 07851289667"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("12432324")), new Sender("tom"), "Yes. okay." ));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124328")), new Sender("tom"), "Great. My CC number is 340000000000009" ));
        Conversation c = new Conversation("Convo", messages);

        config.setFilterSender(new Sender("Bob"));
        c.filterBySender(config);

        assertEquals(1, c.getMessages().size());

    }

    @Test
    public void testFilterKeyword(){
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124324")), new Sender("Bob"), "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124326")), new Sender("Karen"), "Hey. My number is 07851289667"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("12432323")), new Sender("tom"), "Great. My CC number is 340000000000009" ));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1243289")), new Sender("Karen"), "Yo, Bob! My number is 07851289667"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("12432324")), new Sender("tom"), "Yes. okay." ));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124328")), new Sender("tom"), "yes. My CC number is 340000000000009" ));

        Conversation c = new Conversation("Convo", messages);

        config.setFilterKeyword("yes");
        c.filterByKeyword(config);

        assertEquals(2, c.getMessages().size());
    }

    @Test
    public void testBlacklist(){
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124324")), new Sender("Bob"), "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124326")), new Sender("Karen"), "Hey. My number is 07851289667"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("12432323")), new Sender("tom"), "Great. My CC number is 340000000000009" ));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1243289")), new Sender("Karen"), "Yo, Bob! My number is 07851289667"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("12432324")), new Sender("tom"), "Yes. okay." ));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124328")), new Sender("tom"), "yes. My CC number is 340000000000009" ));

        Conversation c = new Conversation("Convo", messages);

        config.setBlacklist(new String[] {"Hello", "Yes"});
        for(int x = 0; x < config.getBlacklist().length; x++){
            c.redact(config.getBlacklist()[x], "*redacted*");
        }

        assertEquals(messages.get(0).getContent(), "*redacted* there!");
        assertEquals(messages.get(4).getContent(), "*redacted*. okay.");
    }

    @Test
    public void testObfuscateInfo(){
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124324")), new Sender("Bob"), "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124326")), new Sender("Karen"), "Hey. My number is 07851289667"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("12432323")), new Sender("tom"), "Great. My CC number is 340000000000009" ));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1243289")), new Sender("Karen"), "Yo, Bob! My number is 07851289667"));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("12432324")), new Sender("tom"), "Yes. okay." ));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124328")), new Sender("tom"), "yes. My CC number is 340000000000009" ));

        Conversation c = new Conversation("Convo", messages);

        config.setObfuscateInfo(true);
        c.obfuscate(config);



        assertEquals(messages.get(1).getContent(), "Hey. My number is*redacted*");
        assertEquals(messages.get(2).getContent(), "Great. My CC number is *redacted*00009");
    }

    @Test
    public void testObfuscateNames(){
            ConversationExporterConfiguration config = new ConversationExporterConfiguration();

            ArrayList<Message> messages = new ArrayList<>();
            messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124324")), new Sender("Bob"), "Hello there!"));
            messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124326")), new Sender("Karen"), "Hey. My number is 07851289667"));
            messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("12432323")), new Sender("tom"), "Great. My CC number is 340000000000009" ));
            messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1243289")), new Sender("Karen"), "Yo, Bob! My number is 07851289667"));
            messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("12432324")), new Sender("tom"), "Yes. okay." ));
            messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("124328")), new Sender("tom"), "yes. My CC number is 340000000000009" ));

            Conversation c = new Conversation("Convo", messages);

            config.setObfuscateUID(true);
            c.obfuscate(config);



            assertEquals(messages.get(0).getSender().getName(), String.valueOf(new Sender("Bob").hashCode()));

    }

}
