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

        assertEquals("My Conversation", c.conversation_name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].unix_timestamp);
        assertEquals("bob", ms[0].username);
        assertEquals( "Hello there!", ms[0].message);

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].unix_timestamp);
        assertEquals("mike", ms[1].username);
        assertEquals( "how are you?", ms[1].message);

        assertEquals( Instant.ofEpochSecond(1448470906), ms[2].unix_timestamp);
        assertEquals( "bob", ms[2].username);
        assertEquals( "I'm good thanks, do you like pie?", ms[2].message);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].unix_timestamp);

        assertEquals("mike", ms[3].username);
        assertEquals( "no, let me ask Angus...", ms[3].message);

        assertEquals( Instant.ofEpochSecond(1448470912), ms[4].unix_timestamp);
        assertEquals( "angus", ms[4].username);
        assertEquals( "Hell yes! Are we buying some pie?", ms[4].message);

        assertEquals( Instant.ofEpochSecond(1448470914), ms[5].unix_timestamp);
        assertEquals( "bob", ms[5].username);
        assertEquals( "No, just want to know if there's anybody else in the pie society...", ms[5].message);

        assertEquals( Instant.ofEpochSecond(1448470915), ms[6].unix_timestamp);
        assertEquals( "angus", ms[6].username);
        assertEquals("YES! I'm the head pie eater there...", ms[6].message);
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
