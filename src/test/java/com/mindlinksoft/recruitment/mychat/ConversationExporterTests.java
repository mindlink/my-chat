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

        assertEquals("My Conversation", conversation.name);

        assertEquals(7, conversation.messages.size());

        Message[] ms = new Message[conversation.messages.size()];
        conversation.messages.toArray(ms);

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

        //File path formatter test
        String specificFilePath = exporter.specificFilepath("C:\\Users\\Muhammad\\Desktop\\chat.txt");
        assertEquals(specificFilePath, "C:\\\\Users\\\\Muhammad\\\\Desktop\\\\chat.txt");

        //Filter message by a specific user test by the number of messages
        exporter.exportConversation("chat.txt", "chat.json", new Filter("filteruser", "bob", ""));
        conversation = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        assertEquals(conversation.messages.size(), 3);

        //Filter message by a specific user test by the number of messages
        exporter.exportConversation("chat.txt", "chat.json", new Filter("filterword", "pie", ""));
        conversation = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        assertEquals(conversation.messages.size(), 4);

        //Replacing a word with *redacted* to hide test by searching all messages that contains it.
        exporter.exportConversation("chat.txt", "chat.json", new Filter("hideword", "pie", ""));
        conversation = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        for (Message message : conversation.messages) {
            assertTrue(message.content.contains("*redacted*"));
        }

        //Replacing credict card and phone numbers with *redacted*
        exporter.exportConversation("chat.txt", "chat.json", new Filter("hideword", "pie", "hidesensitive"));

        conversation = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        for (Message message : conversation.messages) {
            assertTrue(message.content.contains("*redacted*"));
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
