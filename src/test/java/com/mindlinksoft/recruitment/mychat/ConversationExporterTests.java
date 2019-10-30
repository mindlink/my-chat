package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Scanner;

import com.google.gson.stream.JsonReader;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {

    public final String inputFilePath = "chat.txt";
    public final String outputFilePath = "chat.json";

    /**
     * Tests, with no filter, that exporting a conversation will export the conversation correctly.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testNoFilterExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[4];
        args[0] = "no_filter";
        args[1] = "nil";
        args[2] = "no";
        args[3] = "no";
        exporter.exportConversation(inputFilePath, outputFilePath, args);


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());


        Gson g = builder.create();


        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.conversation_name);


        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].unix_timestamp);
        assertEquals("bob", ms[0].username);
        assertEquals("Hello there!", ms[0].message);

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].unix_timestamp);
        assertEquals("mike", ms[1].username);
        assertEquals("how are you?", ms[1].message);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].unix_timestamp);
        assertEquals("bob", ms[2].username);
        assertEquals("I'm good thanks, do you like pie?", ms[2].message);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].unix_timestamp);

        assertEquals("mike", ms[3].username);
        assertEquals("no, let me ask Angus...", ms[3].message);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].unix_timestamp);
        assertEquals("angus", ms[4].username);
        assertEquals("Hell yes! Are we buying some pie?", ms[4].message);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].unix_timestamp);
        assertEquals("bob", ms[5].username);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].message);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].unix_timestamp);
        assertEquals("angus", ms[6].username);
        assertEquals("YES! I'm the head pie eater there...", ms[6].message);

        assertEquals(Instant.ofEpochSecond(1448470919), ms[7].unix_timestamp);
        assertEquals("dave", ms[7].username);
        assertEquals("Ok, here\u0027s my card number: 4321567890121234. What\u0027s your phone number bob? I\u0027ll send you the adress of the pie shop...", ms[7].message);

        assertEquals(Instant.ofEpochSecond(1448470919), ms[8].unix_timestamp);
        assertEquals("bob", ms[8].username);
        assertEquals("it\u0027s 07804377261. Thanks, looking forward to the pie!", ms[8].message);



    }

    /**
     * Username Filter Test: Only Bob's messages should show in the correct order
     * @throws Exception When something bad happens.
     */
    @Test
    public void testUsernameFilter() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[4];
        args[0] = "username";
        args[1] = "bob";
        args[2] = "no";
        args[3] = "no";
        exporter.exportConversation(inputFilePath, outputFilePath, args);


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());


        Gson g = builder.create();


        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.conversation_name);


        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].unix_timestamp);
        assertEquals("bob", ms[0].username);
        assertEquals("Hello there!", ms[0].message);



        assertEquals(Instant.ofEpochSecond(1448470906), ms[1].unix_timestamp);
        assertEquals("bob", ms[1].username);
        assertEquals("I'm good thanks, do you like pie?", ms[1].message);



        assertEquals(Instant.ofEpochSecond(1448470914), ms[2].unix_timestamp);
        assertEquals("bob", ms[2].username);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].message);

        assertEquals(Instant.ofEpochSecond(1448470919), ms[3].unix_timestamp);
        assertEquals("bob", ms[3].username);
        assertEquals("it's 07804377261. Thanks, looking forward to the pie!", ms[3].message);


    }

    /**
     * Test for filtering by a specific word. Only those messages containing the specific word
     * should be shown in the outputted json.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testFilterBySpecificWord() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[4];
        args[0] = "specific_word";
        args[1] = "pie";
        args[2] = "no";
        args[3] = "no";
        exporter.exportConversation(inputFilePath, outputFilePath, args);


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());


        Gson g = builder.create();


        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.conversation_name);


        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);



        assertEquals(Instant.ofEpochSecond(1448470906), ms[0].unix_timestamp);
        assertEquals("bob", ms[0].username);
        assertEquals("I'm good thanks, do you like pie?", ms[0].message);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[1].unix_timestamp);
        assertEquals("angus", ms[1].username);
        assertEquals("Hell yes! Are we buying some pie?", ms[1].message);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[2].unix_timestamp);
        assertEquals("bob", ms[2].username);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].message);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[3].unix_timestamp);
        assertEquals("angus", ms[3].username);
        assertEquals("YES! I'm the head pie eater there...", ms[3].message);

        assertEquals(Instant.ofEpochSecond(1448470919), ms[4].unix_timestamp);
        assertEquals("dave", ms[4].username);
        assertEquals("Ok, here\u0027s my card number: 4321567890121234. What\u0027s your phone number bob? I\u0027ll send you the adress of the pie shop...", ms[4].message);
    }

    /**
     * Test for filtering by removing a specific word. All messages containing the word
     * will have the word changed to '*redacted*' in the outputted json.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testFilterByRemovingASpecificWord() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[4];
        args[0] = "hide_word";
        args[1] = "pie";
        args[2] = "no";
        args[3] = "no";
        exporter.exportConversation(inputFilePath, outputFilePath, args);


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());


        Gson g = builder.create();


        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.conversation_name);


        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);



        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].unix_timestamp);
        assertEquals("bob", ms[0].username);
        assertEquals("Hello there!", ms[0].message);

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].unix_timestamp);
        assertEquals("mike", ms[1].username);
        assertEquals("how are you?", ms[1].message);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].unix_timestamp);
        assertEquals("bob", ms[2].username);
        assertEquals("I'm good thanks, do you like *redacted*? ", ms[2].message);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].unix_timestamp);

        assertEquals("mike", ms[3].username);
        assertEquals("no, let me ask Angus...", ms[3].message);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].unix_timestamp);
        assertEquals("angus", ms[4].username);
        assertEquals("Hell yes! Are we buying some *redacted*? ", ms[4].message);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].unix_timestamp);
        assertEquals("bob", ms[5].username);
        assertEquals("No, just want to know if there's anybody else in the *redacted* society... ", ms[5].message);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].unix_timestamp);
        assertEquals("angus", ms[6].username);
        assertEquals("YES! I'm the head *redacted* eater there... ", ms[6].message);

        assertEquals(Instant.ofEpochSecond(1448470919), ms[7].unix_timestamp);
        assertEquals("dave", ms[7].username);
        assertEquals("Ok, here\u0027s my card number: 4321567890121234. What\u0027s your phone number bob? I\u0027ll send you the adress of the *redacted* shop... ", ms[7].message);

        assertEquals(Instant.ofEpochSecond(1448470919), ms[8].unix_timestamp);
        assertEquals("bob", ms[8].username);
        assertEquals("it\u0027s 07804377261. Thanks, looking forward to the *redacted*! ", ms[8].message);

    }

    /**
     * Test for filtering by removing a specific word. All messages containing the word
     * will have the word changed to '*redacted*' in the outputted json.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testFilterByRemovingMultipleWords() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[4];
        args[0] = "hide_word";
        args[1] = "pie,shop";
        args[2] = "no";
        args[3] = "no";
        exporter.exportConversation(inputFilePath, outputFilePath, args);


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());


        Gson g = builder.create();


        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.conversation_name);


        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);



        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].unix_timestamp);
        assertEquals("bob", ms[0].username);
        assertEquals("Hello there!", ms[0].message);

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].unix_timestamp);
        assertEquals("mike", ms[1].username);
        assertEquals("how are you?", ms[1].message);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].unix_timestamp);
        assertEquals("bob", ms[2].username);
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].message);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].unix_timestamp);

        assertEquals("mike", ms[3].username);
        assertEquals("no, let me ask Angus...", ms[3].message);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].unix_timestamp);
        assertEquals("angus", ms[4].username);
        assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].message);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].unix_timestamp);
        assertEquals("bob", ms[5].username);
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms[5].message);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].unix_timestamp);
        assertEquals("angus", ms[6].username);
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].message);

        assertEquals(Instant.ofEpochSecond(1448470919), ms[7].unix_timestamp);
        assertEquals("dave", ms[7].username);
        assertEquals("Ok, here\u0027s my card number: 4321567890121234. What\u0027s your phone number bob? I\u0027ll send you the adress of the *redacted* *redacted*...", ms[7].message);

        assertEquals(Instant.ofEpochSecond(1448470919), ms[8].unix_timestamp);
        assertEquals("bob", ms[8].username);
        assertEquals("it\u0027s 07804377261. Thanks, looking forward to the *redacted*!", ms[8].message);

    }

    /**
     * Test for filtering by removing a specific word. All messages containing the word
     * will have the word changed to '*redacted*' in the outputted json. Also Testing
     * the censoring of user ids and any card or phone numbers used.
     *
     * @throws Exception When something bad happens.
     */
    @Test
    public void testFilterByRemovingMultipleWordsAndRemoveUserIds() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[4];
        args[0] = "hide_word";
        args[1] = "pie,shop";
        args[2] = "yes";
        args[3] = "yes";
        exporter.exportConversation(inputFilePath, outputFilePath, args);


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());


        Gson g = builder.create();


        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.conversation_name);


        Message[] ms = new Message[c.messages.size()];
        c.messages.toArray(ms);



        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].unix_timestamp);
        assertEquals("9f9d51bc-70ef-31ca-9c14-f307980a29d8", ms[0].username);
        assertEquals("Hello there!", ms[0].message);

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].unix_timestamp);
        assertEquals("18126e7b-d3f8-3b3f-be4d-f094def5b7de", ms[1].username);
        assertEquals("how are you?", ms[1].message);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].unix_timestamp);
        assertEquals("9f9d51bc-70ef-31ca-9c14-f307980a29d8", ms[2].username);
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].message);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].unix_timestamp);

        assertEquals("18126e7b-d3f8-3b3f-be4d-f094def5b7de", ms[3].username);
        assertEquals("no, let me ask Angus...", ms[3].message);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].unix_timestamp);
        assertEquals("e7a70020-ac0d-33a2-9b88-0f2003bb4e46", ms[4].username);
        assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].message);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].unix_timestamp);
        assertEquals("9f9d51bc-70ef-31ca-9c14-f307980a29d8", ms[5].username);
        assertEquals("No, just want to know if there's anybody else in the *redacted* society...", ms[5].message);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].unix_timestamp);
        assertEquals("e7a70020-ac0d-33a2-9b88-0f2003bb4e46", ms[6].username);
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].message);

        assertEquals(Instant.ofEpochSecond(1448470919), ms[7].unix_timestamp);
        assertEquals("16108387-43cc-30e3-a4fd-da748282d9b8", ms[7].username);
        assertEquals("Ok, here's my card number: *redacted*. What's your phone number bob? I'll send you the adress of the *redacted* *redacted*...", ms[7].message);

        assertEquals(Instant.ofEpochSecond(1448470919), ms[8].unix_timestamp);
        assertEquals("9f9d51bc-70ef-31ca-9c14-f307980a29d8", ms[8].username);
        assertEquals("it\u0027s *redacted*. Thanks, looking forward to the *redacted*!", ms[8].message);

    }

//    class InstantDeserializer implements JsonDeserializer<Instant> {
//
//        @Override
//        public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//            if (!jsonElement.isJsonPrimitive()) {
//                throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
//            }
//
//            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
//
//            if (!jsonPrimitive.isNumber()) {
//                throw new JsonParseException("Expected instant represented as JSON number, but different primitive found.");
//            }
//
//            return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
//        }
//    }
}
