package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {

    /**
     * Tests that exporting a conversation will export the conversation
     * correctly.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation conversation = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", conversation.getName());

        assertEquals(7, conversation.getMessages().size());

        Message[] ms = new Message[conversation.getMessages().size()];
        conversation.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].getSenderId(), "mike");
        assertEquals(ms[1].getContent(), "how are you?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].getSenderId(), "bob");
        assertEquals(ms[2].getContent(), "I'm good thanks, do you like pie?");

        assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].getSenderId(), "mike");
        assertEquals(ms[3].getContent(), "no, let me ask Angus...");

        assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].getSenderId(), "angus");
        assertEquals(ms[4].getContent(), "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].getSenderId(), "bob");
        assertEquals(ms[5].getContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].getSenderId(), "angus");
        assertEquals(ms[6].getContent(), "YES! I'm the head pie eater there...");

        //File path formatter test
        String specificFilePath = exporter.specificFilepath("C:\\Users\\Muhammad\\Desktop\\chat.txt");
        assertEquals(specificFilePath, "C:\\\\Users\\\\Muhammad\\\\Desktop\\\\chat.txt");

        //Filter message by a specific user test by the number of messages
        exporter.exportConversation("chat.txt", "chat.json", new Filter("filteruser", "bob", ""));
        conversation = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        assertEquals(conversation.getMessages().size(), 3);

        //Filter message by a specific user test by the number of messages
        exporter.exportConversation("chat.txt", "chat.json", new Filter("filterword", "pie", ""));
        conversation = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        assertEquals(conversation.getMessages().size(), 4);

        //Replacing a word with *redacted* to hide test by searching all messages that contains it.
        exporter.exportConversation("chat.txt", "chat.json", new Filter("hideword", "pie", ""));
        conversation = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        for (Message message : conversation.getMessages()) {
            assertTrue(message.getContent().contains("*redacted*"));
        }

        //Replacing credict card and phone numbers with *redacted*
        exporter.exportConversation("chat.txt", "chat.json", new Filter("hideword", "pie", "hidesensitive"));
        conversation = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        for (Message message : conversation.getMessages()) {
            assertTrue(message.getContent().contains("*redacted*"));
        }

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
