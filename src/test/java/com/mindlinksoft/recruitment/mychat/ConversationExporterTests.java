package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.getName());

        assertEquals(7, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
        assertEquals(ms[0].getSenderID(), "bob");
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
        assertEquals("mike", ms[1].getSenderID());
        assertEquals("how are you?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderID());
        assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
        assertEquals("mike", ms[3].getSenderID());
        assertEquals("no, let me ask Angus...", ms[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
        assertEquals("angus", ms[4].getSenderID());
        assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
        assertEquals("bob", ms[5].getSenderID());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
        assertEquals("angus", ms[6].getSenderID());
        assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());
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
