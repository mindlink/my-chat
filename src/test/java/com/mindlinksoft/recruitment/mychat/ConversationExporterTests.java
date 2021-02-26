package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testExportingConversationExportsConversation() throws FileNotFoundException, IOException {
        ConversationExporter exporter = new ConversationExporter();

        // fake configuration
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        configuration.inputFilePath = "chat.txt";
        configuration.outputFilePath = "out.json";

        exporter.exportConversation(configuration);

        Conversation c = getJsonFileContents("out.json");

        assertEquals("My Conversation", c.getName());

        assertEquals(7, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals("Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
        assertEquals("mike", ms[1].getSenderId());
        assertEquals("how are you?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
        assertEquals("mike", ms[3].getSenderId());
        assertEquals("no, let me ask Angus...", ms[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
        assertEquals("angus", ms[4].getSenderId());
        assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
        assertEquals("bob", ms[5].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
        assertEquals("angus", ms[6].getSenderId());
        assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());
    }

    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test
    public void testExportingToFileThatDoesNotExist() throws FileNotFoundException, IOException {
        // delete file if exists
        File fileToDelete = new File("newChat.json");
        if (fileToDelete.isFile()) {
            fileToDelete.delete();
        }

        ConversationExporter exporter = new ConversationExporter();

        // fake configuration
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        configuration.inputFilePath = "chat.txt";
        configuration.outputFilePath = "newChat.json";

        exporter.exportConversation(configuration);

        Conversation c = getJsonFileContents("newChat.json");

        assertEquals("My Conversation", c.getName());

        assertEquals(7, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals("Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
        assertEquals("mike", ms[1].getSenderId());
        assertEquals("how are you?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
        assertEquals("mike", ms[3].getSenderId());
        assertEquals("no, let me ask Angus...", ms[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
        assertEquals("angus", ms[4].getSenderId());
        assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
        assertEquals("bob", ms[5].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
        assertEquals("angus", ms[6].getSenderId());
        assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());
    }

    public Conversation getJsonFileContents(String filePath)
            throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        try {
            return g.fromJson(new InputStreamReader(new FileInputStream(filePath)), Conversation.class);
        } catch (JsonSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonIOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    class InstantDeserializer implements JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonPrimitive()) {
                throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
            }

            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

            if (!jsonPrimitive.isNumber()) {
                throw new JsonParseException(
                        "Expected instant represented as JSON number, but different primitive found.");
            }

            return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
        }
    }
}
