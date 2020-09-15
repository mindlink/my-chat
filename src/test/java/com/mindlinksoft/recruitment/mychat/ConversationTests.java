package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Base64;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Tests for the Conversation
 */
public class ConversationTests {

    private final String inputFilePath = "C:/Users/Ricardo/Desktop/WorkForMindlink/my-chat/chat.txt";
    private final String outputFilePath = "C:/Users/Ricardo/Desktop/WorkForMindlink/my-chat/chat.json";

    public Gson createGsonDeserializer() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        return builder.create();
    }

    public Conversation initConversation(String command,String feature) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation(new CommandLineArgumentParser()
                .parseCommandLineArguments(new String[]{inputFilePath, outputFilePath, command, feature}));
        Gson g = createGsonDeserializer();
        return g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
    }

    /**
     * Tests that exporting a conversation will export the conversation correctly.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {

        Conversation c = initConversation("null","null");

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
     * Tests if the conversation is filtered by a specific user
     */
    @Test
    public void testFilterBySpecificUser() throws Exception {

        String commandArgs = "user:bob";
        String features = "null";
        Conversation c = initConversation(commandArgs,features);

        assertEquals("My Conversation", c.name);
        assertEquals(3, c.messages.size());

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
        String commandArgs = "word:there";
        String features = "null";
        Conversation c = initConversation(commandArgs,features);

        assertEquals("My Conversation", c.name);
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
        String commandArgs = "redact:pie";
        String features = "null";
        Conversation c = initConversation(commandArgs,features);

        assertEquals("My Conversation", c.name);
        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        for (Message m : ms) Assert.assertFalse(m.content.contains(commandArgs.split(":")[1]));

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
        String commandArgs = "null";
        String features = "obfuscate";
        Conversation c = initConversation(commandArgs,features);

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
        String commandArgs = "null";
        String features = "report";
        Conversation c = initConversation(commandArgs,features);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[ms.length-1].senderId,"Activity Report");
        assertEquals(ms[ms.length-1].content,"[bob:3, angus:2, mike:2]");
    }

    /**
     * Tests both features
     */
    @Test
    public void testEssentialAndAdditionalFeaturesCombined() throws Exception{
        String commandArgs = "user:bob";
        String features = "report";
        Conversation c = initConversation(commandArgs,features);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(ms[ms.length-1].senderId,"Activity Report");
        assertEquals(ms[ms.length-1].content,"[bob:3]");

        assertEquals(ms[0].timestamp, Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].senderId, "bob");
        assertEquals(ms[0].content, "Hello there!");

        assertEquals(ms[1].timestamp, Instant.ofEpochSecond(1448470906));
        assertEquals(ms[1].senderId, "bob");
        assertEquals(ms[1].content, "I'm good thanks, do you like pie?");

        assertEquals(ms[2].timestamp, Instant.ofEpochSecond(1448470914));
        assertEquals(ms[2].senderId, "bob");
        assertEquals(ms[2].content, "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[3].senderId, "Activity Report");
        assertEquals(ms[3].content, "[bob:3]");
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
