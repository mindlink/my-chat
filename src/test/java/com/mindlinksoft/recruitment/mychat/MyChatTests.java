package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.convertors.ConversationToJson;
import com.mindlinksoft.recruitment.mychat.filter.Filter;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;
import com.mindlinksoft.recruitment.mychat.validation.Validator;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for MyChat application.
 */
public class MyChatTests {
    /**
     * Tests that exporting a conversation will export the conversation correctly. This test also checks the IOUtils since
     * these are used by exportConversation() method.
     * @throws IOException When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws IOException {

        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", null, "bob", new String[] {"hell"});

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        JsonObject jo = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")),JsonObject.class);
        Conversation c = g.fromJson(jo.get("Conversation"), Conversation.class);

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
     * Tests keyword filtering end to end.
     * @throws IOException When something bad happens.
     */
    @Test
    public void testKeywordFiltering() throws IOException {

        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", "hello", null, null);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        JsonObject jo = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")),JsonObject.class);
        Conversation c = g.fromJson(jo.get("Conversation"), Conversation.class);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        for (Message message : ms) {
            Assert.assertThat(message.content.toLowerCase(), CoreMatchers.containsString("hello"));
        }

    }

    /**
     * Tests user filtering  end to end.
     * @throws IOException When something bad happens.
     */
    @Test
    public void testUserFiltering() throws IOException {

        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", null, "angus", null);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        JsonObject jo = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")),JsonObject.class);
        Conversation c = g.fromJson(jo.get("Conversation"), Conversation.class);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        for (Message message : ms) {
            Assert.assertThat(message.senderId.toLowerCase(), CoreMatchers.containsString("angus"));
        }

    }

    /**
     * Tests blacklist functionality  end to end.
     * @throws IOException When something bad happens.
     */
    @Test
    public void testBlacklistFunctionality() throws IOException {

        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json", null, null, new String[]{"hell"});

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        JsonObject jo = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")),JsonObject.class);
        Conversation c = g.fromJson(jo.get("Conversation"), Conversation.class);

        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        for (Message message : ms) {
            Assert.assertThat(message.content.toLowerCase(), CoreMatchers.not(CoreMatchers.containsString("hell ")));
        }

    }

    /**
     * Tests Conversation to json functionality.
     */
    @Test
    public void testConversationToJson() {

        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong("1448470901")),"testSender","This is the content"));
        Conversation conversation = new Conversation("Test Conversation", messages);
        String json = ConversationToJson.convertConversationToJson(conversation);
        String expectedJson = "{\n" +
                "  \"Conversation\": {\n" +
                "    \"name\": \"Test Conversation\",\n" +
                "    \"messages\": [\n" +
                "      {\n" +
                "        \"content\": \"This is the content\",\n" +
                "        \"timestamp\": 1448470901,\n" +
                "        \"senderId\": \"testSender\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"Activity Report\": {\n" +
                "    \"testSender\": 1\n" +
                "  }\n" +
                "}";
        assertEquals(json,expectedJson);

    }

    /**
     * Tests filtering functionality.
     */
    @Test
    public void testFilters() {

        Filter filter = new Filter("This is the keyword of dimitris with blacklisted word", "keyword", "dimitris", new String[]{"blacklisted"});
        Filter filter2 = new Filter(filter.getMessage(), "another keyword", "bob", new String[]{"word"});
        assertFalse(filter.filterByKeyword());
        assertTrue(filter2.filterByKeyword());
        assertFalse(filter.filterByUser("dimitris"));
        assertTrue(filter2.filterByUser("dimitris"));
        assertTrue(filter.filterByUser("bob"));
        assertFalse(filter2.filterByUser("bob"));
        filter.filterBlacklist();
        filter2.filterBlacklist();
        assertEquals(filter.getMessage(), "This is the keyword of dimitris with ****** word");
        assertEquals(filter2.getMessage(), "This is the keyword of dimitris with blacklisted ******");

    }

    /**
     * Tests validators.
     * @throws IOException When something bad happens.
     */
    @Test
    public void testValidators() {

        assertTrue(Validator.checkPhoneNo("210-9256547"));
        assertFalse(Validator.checkPhoneNo("2109256547"));
        assertFalse(Validator.checkPhoneNo("210-92356547"));
        assertFalse(Validator.checkPhoneNo("21-49256547"));
        assertFalse(Validator.checkValidCC("2149256547"));
        assertFalse(Validator.checkValidCC("1234567890123456"));
//        assertTrue(Validator.checkValidCC("XXXXXXXXXXX")); A real credit card no. is needed here to pass the test

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
