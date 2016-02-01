package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.Assert.*;

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

        //exporter.exportConversation("chat.txt", "chat.json");
        Conversation conversation = exporter.readConversation("chat.txt");
        exporter.writeConversation(conversation,"chat.json");

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
     * Testing report generated.
     */
    @Test
    public void testReport() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        Conversation conversation = exporter.readConversation("chat.txt");
        exporter.report(conversation,"chat.json");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Report r = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Report.class);

        ArrayList<User> users = r.getUsersList();

        ArrayList<User> actualList = new ArrayList<User>();

        actualList.add(new User("mike",2));
        actualList.add(new User("angus", 2));
        actualList.add(new User("bob",3));

        assertEquals(users.get(0).name, actualList.get(0).name);
        assertEquals(users.get(0).count, actualList.get(0).count);

        assertEquals(users.get(1).name, actualList.get(1).name);
        assertEquals(users.get(1).count, actualList.get(1).count);

        assertEquals(users.get(2).name, actualList.get(2).name);
        assertEquals(users.get(2).count, actualList.get(2).count);

    }

    /**
     * Testing method to redact words.
     */
    @Test
    public void testRedactWords() throws Exception {

        String[] keyWords={"bob"};
        ConversationExporter exporter = new ConversationExporter();
        Conversation conversation = exporter.readConversation("chat.txt");
        exporter.redactWords(conversation, keyWords, "chat.json");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        for(String k: keyWords) {
            for (Message m : c.messages) {
                assertTrue(!m.content.contains(k));
            }
        }
    }


    /**
     *Testing method to filter message sent by specific users.
     */
    @Test
    public void testFilterUsername() throws Exception {
        String keyWords="angus";
        ConversationExporter exporter = new ConversationExporter();
        Conversation conversation = exporter.readConversation("chat.txt");
        exporter.filterUsername(conversation, keyWords, "chat.json");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        for (Message m : c.messages) {
            assertTrue(m.senderId.equals(keyWords));
        }
    }

    /**
     *Testing method to filter message by keyword.
     */
    @Test
    public void testFilterKeyWord() throws Exception {
        String keyword="bob";
        ConversationExporter exporter = new ConversationExporter();
        Conversation conversation = exporter.readConversation("chat.txt");
        exporter.filterKeyword(conversation, keyword, "chat.json");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        boolean flag=false;
        for (Message m : c.messages) {
            if(m.senderId.equals(keyword) || m.content.contains(keyword)){
                flag=true;
            }
            assertTrue(flag);
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
