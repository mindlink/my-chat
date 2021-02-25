package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import java.nio.file.Files;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {

     @Before
     public void clearOutputFile() throws Exception {
         try {
             new FileOutputStream("chat.json").close();
         }
         catch (FileNotFoundException e) {
             System.out.println("File not found when cleaning 'chat.json': " + e.getMessage());
         }
     }

    /**
    * Tests that exporting a conversation will export the conversation correctly.
    * @throws Exception When something bad happens.
    */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", null, null);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(7, c.messages.size());

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("Hello there!", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
        assertEquals("mike", ms[1].senderId);
        assertEquals("how are you?", ms[1].content);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
        assertEquals("bob", ms[2].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[2].content);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
        assertEquals("mike", ms[3].senderId);
        assertEquals("no, let me ask Angus...", ms[3].content);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
        assertEquals("angus", ms[4].senderId);
        assertEquals("Hell yes! Are we buying some pie?", ms[4].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
        assertEquals("bob", ms[5].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
        assertEquals("angus", ms[6].senderId);
        assertEquals("YES! I'm the head pie eater there...", ms[6].content);
    }

    @Test
    public void testFilterByUser() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", null, null);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.FilteredByUser("bob").size()];
        c.FilteredByUser("bob").toArray(ms);
        assertEquals(3, ms.length);
        assertEquals("Hello there!", ms[0].content);
        assertEquals("I'm good thanks, do you like pie?", ms[1].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);

        ms = new Message[c.FilteredByUser("angus").size()];
        c.FilteredByUser("angus").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("Hell yes! Are we buying some pie?", ms[0].content);
        assertEquals("YES! I'm the head pie eater there...", ms[1].content);

        ms = new Message[c.FilteredByUser("mike").size()];
        c.FilteredByUser("mike").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("how are you?", ms[0].content);
        assertEquals("no, let me ask Angus...", ms[1].content);

        ms = new Message[c.FilteredByUser("dude").size()];
        c.FilteredByUser("dude").toArray(ms);
        assertEquals(0, ms.length);
    }

    @Test
    public void testFilterByKeyword() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", null, null);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.FilteredByKeyword("pie").size()];
        c.FilteredByKeyword("pie").toArray(ms);
        assertEquals(4, ms.length);
        assertEquals("I'm good thanks, do you like pie?", ms[0].content);
        assertEquals("Hell yes! Are we buying some pie?", ms[1].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);
        assertEquals("YES! I'm the head pie eater there...", ms[3].content);

        ms = new Message[c.FilteredByKeyword("yes").size()];
        c.FilteredByKeyword("yes").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("Hell yes! Are we buying some pie?", ms[0].content);
        assertEquals("YES! I'm the head pie eater there...", ms[1].content);

        ms = new Message[c.FilteredByKeyword("no").size()];
        c.FilteredByKeyword("no").toArray(ms);
        assertEquals(2, ms.length);
        assertEquals("no, let me ask Angus...", ms[0].content);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[1].content);

        ms = new Message[c.FilteredByKeyword("dude").size()];
        c.FilteredByKeyword("dude").toArray(ms);
        assertEquals(0, ms.length);
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
