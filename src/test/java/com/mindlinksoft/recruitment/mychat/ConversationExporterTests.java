package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import com.google.gson.*;

import org.junit.Test;

import picocli.CommandLine;
import picocli.CommandLine.ParseResult;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
    /**
     * Test that reading a conversation from the given {@code inputFilePath} results in a conversation object correctly being formed
     * @throws IllegalArgumentException Thrown when the input file is not found at the given path
     * @throws IOException Thrown when there is an issue with reading from the input file
     */
    @Test
    public void testReadingConversationCreatesConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "chat.txt";

        Conversation conversation = exporter.readConversation(config.inputFilePath);

        assertEquals("My Conversation", conversation.getName());

        assertEquals(7, conversation.getMessages().size());

        Message[] ms = new Message[conversation.getMessages().size()];
        conversation.getMessages().toArray(ms);

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

    /**
     * End-to-end test that exporting a given conversation will export the conversation correctly.
     * @throws IllegalArgumentException Thrown when the conversation file is not found at the given path
     * @throws IOException Thrown when there is an issue with writing to the output file
     */
    @Test
    public void testExportingConversationExportsConversation() throws IllegalArgumentException, IOException {
        ConversationExporter exporter = new ConversationExporter();

        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "chat.txt";
        config.outputFilePath = "chat.json";

        CommandLine cmd = new CommandLine(config);
        String[] args = new String[] {"-i", "chat.txt", "-o", "chat.json"};
        ParseResult parseResult = cmd.parseArgs(args);

        exporter.exportConversation(config, parseResult);

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

    /**
     * End-to-end test that exporting a given conversation with options will export the conversation with processing.
     * @throws IllegalArgumentException Thrown when the conversation file is not found at the given path
     * @throws IOException Thrown when there is an issue with writing to the output file
     */
    @Test
    public void testExportingConversationWithOptionExportsConversationWithProcessing() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        ConversationExporterConfiguration config = new ConversationExporterConfiguration();

        CommandLine cmd = new CommandLine(config);
        String[] args = new String[] {"-i", "chat.txt", "-o", "chat.json", "-u", "bob", "-r"};
        ParseResult parseResult = cmd.parseArgs(args);

        exporter.exportConversation(config, parseResult);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.getName());

        assertEquals(3, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        //check bob user filtering was done
        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
        assertEquals(ms[0].getSenderID(), "bob");
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(Instant.ofEpochSecond(1448470906), ms[1].getTimestamp());
        assertEquals("bob", ms[1].getSenderID());
        assertEquals("I'm good thanks, do you like pie?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderID());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].getContent());

        //check report was generated
        assertEquals(c.getActivity().get(0).getSenderID(), "bob");
        assertEquals(c.getActivity().get(0).getMessageCount(), 3);
        assertEquals(c.getActivity().get(1).getSenderID(), "mike");
        assertEquals(c.getActivity().get(1).getMessageCount(), 2);
        assertEquals(c.getActivity().get(2).getSenderID(), "angus");
        assertEquals(c.getActivity().get(2).getMessageCount(), 2);
    }

    /**
     * Test that reading a file that does not exist throws a IllegalArgumentException
     * @throws IllegalArgumentException Thrown when the conversation file is not found at the given path
     * @throws IOException Thrown when there is an issue with writing to the output file
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReadingAFileThatDoesNotExistThrowsException() throws IllegalArgumentException, IOException {
        ConversationExporter exporter = new ConversationExporter();

        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        config.inputFilePath = "aInputFileThatDefinitelyDoesNotExist.txt";

        Conversation conversation = exporter.readConversation(config.inputFilePath);
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
