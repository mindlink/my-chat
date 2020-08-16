package com.mindlinksoft.recruitment.mychat.exporter.writer;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Sender;
import org.junit.Before;
import org.junit.Test;

public class ConversationWriterTest {

    ConversationWriter writer;

    Conversation expectedConversation;
    List<Message> messages;
    List<Sender> expectedSenderList;
    String outputFilePath;

    @Before
    public void setUp() {
        // set up sample output file
        outputFilePath = "chat.json";

        // set up sample conversation
        String name = "My Conversation";

        messages = new ArrayList<>();
        messages.add(new Message(Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
        messages.add(new Message(Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus..."));
        messages.add(new Message(Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?"));
        messages.add(new Message(Instant.ofEpochSecond(1448470914), "bob", "No, just want to know if there's anybody else in the pie society..."));
        messages.add(new Message(Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head pie eater there..."));

        expectedSenderList = new ArrayList<>();
        Sender sender = new Sender("bob", 3);
        expectedSenderList.add(sender);
        sender = new Sender("mike", 2);
        expectedSenderList.add(sender);
        sender = new Sender("angus", 2);
        expectedSenderList.add(sender);

        expectedConversation = new Conversation(name, messages, expectedSenderList);

        // set up writer
        writer = new ConversationWriter(outputFilePath, expectedConversation);
    }
    
    @Test
    public void write() throws IOException {
        writer.write();

        // create gson builder, which will create Conversation object from the output Json file
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation builtConversation = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        // title should be correct
        assertEquals("My Conversation", builtConversation.getName());

        // there should be 7 messages total
        int actualSizeOfMessages = builtConversation.getMessages().size();
        assertEquals(7, actualSizeOfMessages);

        // add all messages to array
        Message[] ms = new Message[actualSizeOfMessages];
        builtConversation.getMessages().toArray(ms);

        // all messages are the same
        for (int i = 0; i < actualSizeOfMessages; i++) {
            assertEquals(ms[i].getTimestamp(), expectedConversation.getMessage(i).getTimestamp());
            assertEquals(ms[i].getSenderText(), expectedConversation.getMessage(i).getSenderText());
            assertEquals(ms[i].getContent(), expectedConversation.getMessage(i).getContent());
        }

        // frequency map should have correct number of messages for each sender
        List<Sender> senders = builtConversation.getActiveUsers();

        assertEquals(senders.get(0).getMessageCount(), 3);
        assertEquals(senders.get(1).getMessageCount(), 2);
        assertEquals(senders.get(2).getMessageCount(), 2);

    }

    @Test
    public void createGsonBuilder() {
        // set up another test conversation
        String name = "John's Conversational Messages";
        messages.clear();
        messages.add(new Message(Instant.ofEpochSecond(12938), "John", "Hello, world!"));

        Conversation testConversation = new Conversation(name, messages);

        // writer is set to new test conversation
        writer = new ConversationWriter(outputFilePath, testConversation);

        // obtain Gson build
        Gson gson = writer.createGsonBuilder();

        // gson build should return valid Json
        String expected = "{\"name\":\"John's Conversational Messages\",\"messages\":[{\"content\":\"Hello, world!\",\"timestamp\":12938,\"senderText\":\"John\"}]}";
        String conversationInJson = gson.toJson(testConversation);
        assertEquals(expected, conversationInJson);
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