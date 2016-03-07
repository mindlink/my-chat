package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.lang.reflect.Type;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
    /**
     * Tests that exporting a conversation with no filter used will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversationFilterNone() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", 0, new String[0]);

        Conversation c = exporter.readConversation("chat.txt");

        assertEquals("My Conversation", c.GetConvoName());

        assertEquals(7, c.GetMessages().size());

        Message[] ms = new Message[c.GetMessages().size()];
        c.GetMessages().toArray(ms);

        assertEquals(ms[0].GetTimeStamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].GetSenderId(), "bob");
        assertEquals(ms[0].GetContent(), "Hello there!");

        assertEquals(ms[1].GetTimeStamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].GetSenderId(), "mike");
        assertEquals(ms[1].GetContent(), "how are you?");

        assertEquals(ms[2].GetTimeStamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].GetSenderId(), "bob");
        assertEquals(ms[2].GetContent(), "I'm good thanks, do you like pie?");

        assertEquals(ms[3].GetTimeStamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].GetSenderId(), "mike");
        assertEquals(ms[3].GetContent(), "no, let me ask Angus...");

        assertEquals(ms[4].GetTimeStamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].GetSenderId(), "angus");
        assertEquals(ms[4].GetContent(), "Hell yes! Are we buying some pie?");

        assertEquals(ms[5].GetTimeStamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].GetSenderId(), "bob");
        assertEquals(ms[5].GetContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].GetTimeStamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].GetSenderId(), "angus");
        assertEquals(ms[6].GetContent(), "YES! I'm the head pie eater there...");
    }
    
    /**
     * Tests that exporting a conversation with username filtering will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversationFilterUser() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] username = { "bob" };
        exporter.exportConversation("chat.txt", "chat.json", 1, username);

        Conversation c = exporter.readConversation("chat.txt");

        assertEquals("My Conversation", c.GetConvoName());

        assertEquals(7, c.GetMessages().size());

        Message[] ms = new Message[c.GetMessages().size()];
        c.GetMessages().toArray(ms);

        assertEquals(ms[0].GetTimeStamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].GetSenderId(), "bob");
        assertEquals(ms[0].GetContent(), "Hello there!");
        
        assertEquals(ms[2].GetTimeStamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].GetSenderId(), "bob");
        assertEquals(ms[2].GetContent(), "I'm good thanks, do you like pie?");

        assertEquals(ms[5].GetTimeStamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].GetSenderId(), "bob");
        assertEquals(ms[5].GetContent(), "No, just want to know if there's anybody else in the pie society...");
    }
    
    /**
     * Tests that exporting a conversation with keyword filtering will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversationFilterKeyword() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] keyword = { "are" };
        exporter.exportConversation("chat.txt", "chat.json", 2, keyword);

        Conversation c = exporter.readConversation("chat.txt");

        assertEquals("My Conversation", c.GetConvoName());

        assertEquals(7, c.GetMessages().size());

        Message[] ms = new Message[c.GetMessages().size()];
        c.GetMessages().toArray(ms);

        assertEquals(ms[1].GetTimeStamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].GetSenderId(), "mike");
        assertEquals(ms[1].GetContent(), "how are you?");

        assertEquals(ms[4].GetTimeStamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].GetSenderId(), "angus");
        assertEquals(ms[4].GetContent(), "Hell yes! Are we buying some pie?");
    }
    
    /**
     * Tests that exporting a conversation with keyword filtering will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversationFilterBlacklist() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] blacklist = { "are", "you" };
        exporter.exportConversation("chat.txt", "chat.json", 3, blacklist);

        Conversation c = exporter.readConversation("chat.txt");

        assertEquals("My Conversation", c.GetConvoName());

        assertEquals(7, c.GetMessages().size());

        Message[] ms = new Message[c.GetMessages().size()];
        c.GetMessages().toArray(ms);

        assertEquals(ms[0].GetTimeStamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].GetSenderId(), "bob");
        assertEquals(ms[0].GetContent(), "Hello there!");

        assertEquals(ms[1].GetTimeStamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].GetSenderId(), "mike");
        assertEquals(ms[1].GetContent(), "how \"*redacted*\" you?");

        assertEquals(ms[2].GetTimeStamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].GetSenderId(), "bob");
        assertEquals(ms[2].GetContent(), "I'm good thanks, do \"*redacted*\" like pie?");

        assertEquals(ms[3].GetTimeStamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].GetSenderId(), "mike");
        assertEquals(ms[3].GetContent(), "no, let me ask Angus...");

        assertEquals(ms[4].GetTimeStamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].GetSenderId(), "angus");
        assertEquals(ms[4].GetContent(), "Hell yes! \"*redacted*\" we buying some pie?");

        assertEquals(ms[5].GetTimeStamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].GetSenderId(), "bob");
        assertEquals(ms[5].GetContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].GetTimeStamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].GetSenderId(), "angus");
        assertEquals(ms[6].GetContent(), "YES! I'm the head pie eater there...");
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
