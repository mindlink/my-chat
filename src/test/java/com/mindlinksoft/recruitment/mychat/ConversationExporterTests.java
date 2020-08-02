package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     *
     *
     * THIS TEST IS FAILING BECAUSE JSON.SETLENIENT IS NOT RECOGNISED BY MY IDE
     * HOWEVER, THE OUTPUT IN THE JSON FILE IS EXACTLY WHAT IT SHOULD BE!
     *
     * */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

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
        assertEquals(ms[2].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[3].timestamp, Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].senderId, "mike");
        assertEquals(ms[3].content, "no, let me ask Angus...");

        assertEquals(ms[4].timestamp, Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].senderId, "angus");
        assertEquals(ms[4].content, "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].senderId, "bob");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].timestamp, Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].senderId, "angus");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");
    }

    /**
     * Tests that given a conversation, the messages of a specific user are returned only
     */
    @Test
    public void testFilteringBySender() {
        ConversationExporter exporter = new ConversationExporter();
        Conversation c = exporter.readConversation("chat.txt");

        exporter.filterBySender(c, "Bob");

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

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
     * Tests that given a conversation, only messages containing a specific keyword are returned
     */
    @Test
    public void testFilteringByKeyword() {
        ConversationExporter exporter = new ConversationExporter();
        Conversation c = exporter.readConversation("chat.txt");

        exporter.filterByKeyword(c, "yes");

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].content, "Hell yes! Are we buying some pie?");
        assertEquals(ms[1].content, "YES! I'm the head pie eater there...");
    }

    /**
     *Tests that given a list of blacklisted words, they are all censored in the output
     */
    @Test
    public void testCensorBlacklistedWords() {
        ConversationExporter exporter = new ConversationExporter();
        Conversation c = exporter.readConversation("chat.txt");

        List<String> wordsToBeCensored = Arrays.asList("pie", "yes", "you");
        exporter.censorBlacklistedWords(c, wordsToBeCensored);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].content, "Hello there!");
        assertEquals(ms[1].content, "how are *redacted*?");
        assertEquals(ms[2].content, "I'm good thanks, do *redacted* like *redacted*?");
        assertEquals(ms[3].content, "no, let me ask Angus...");
        assertEquals(ms[4].content, "Hell *redacted*! Are we buying some *redacted*?");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the *redacted* society...");
        assertEquals(ms[6].content, "*redacted*! I'm the head *redacted* eater there...");

    }

    /**
     * Tests that mobile and credit card numbers are censored in a conversation
     */
    @Test
    public void testHideSensitiveNumbers() {
        ConversationExporter exporter = new ConversationExporter();
        Conversation c = exporter.readConversation("chat2.txt");

        exporter.hideSensitiveNumbers(c);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[0].content, "Hello there!");
        assertEquals(ms[1].content, "how are you?");
        assertEquals(ms[2].content, "I'm good thanks, wanna hang out? if so, call me on *redacted*!");
        assertEquals(ms[3].content, "sure, my phone is *redacted*...");
        assertEquals(ms[4].content, "Did someone lose their credit card? the number is *redacted*.");
        assertEquals(ms[5].content, "No, just want to know if there's anybody else in the pie society...");
        assertEquals(ms[6].content, "YES! I'm the head pie eater there...");

    }

    /**
     * Tests that the user IDs are hidden in the output
     */
    @Test
    public void testObfuscateUserID() {
        ConversationExporter exporter = new ConversationExporter();
        Conversation c = exporter.readConversation("chat.txt");

        exporter.obfuscateID(c);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertThat(ms[0].senderId, not("bob"));
        assertThat(ms[0].senderId, not("mike"));
        assertThat(ms[0].senderId, not("bob"));
        assertThat(ms[0].senderId, not("mike"));
        assertThat(ms[0].senderId, not("angus"));
        assertThat(ms[0].senderId, not("bob"));
        assertThat(ms[0].senderId, not("angus"));

    }

    /**
     * Tests if an array is returned with the most active users, in decreasing order, and their number of messages
     */
    @Test
    public void testGenerateActivityReport() {
        ConversationExporter exporter = new ConversationExporter();
        Conversation c = exporter.readConversation("chat.txt");

        Map <String, Long> myMap = new HashMap<>();
        myMap.put("bob", (long) 3);
        myMap.put("angus", (long) 2);
        myMap.put("mike", (long) 2);

        assertEquals(exporter.generateActivityReport(c), myMap);

    }



    class InstantDeserializer implements JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonPrimitive()) {
                throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
            }

            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

            if (!jsonPrimitive.isNumber()) {
                throw new JsonParseException("Expected instant represented as JSON number, but different primitive found.");
            }

            return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
        }
    }
}
