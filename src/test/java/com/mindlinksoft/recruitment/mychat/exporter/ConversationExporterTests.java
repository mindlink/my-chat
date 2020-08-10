package com.mindlinksoft.recruitment.mychat.exporter;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.main.ConversationExporterConfiguration;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Type;
import java.time.Instant;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {

    ConversationExporter exporter;

    ConversationExporterConfiguration config;
    String inputFilePath;
    String outputFilePath;

    @Before
    public void setUp() {
        inputFilePath = "chat.txt";
        outputFilePath = "chat.json";
        config = new ConversationExporterConfiguration(inputFilePath, outputFilePath);

        exporter = new ConversationExporter(config);
    }

    @Test
    public void export() {
        // TODO: write tests
        // should start building reader, modifier and writer

    }

    @Test
    public void buildReader() {
        // buildReader should read the input file and return a conversation
        Conversation conv = exporter.buildReader(config.inputFilePath);

        // resultant conversation should be non-empty 
        // (correctness is checked in ConversationReaderTests)
        assertNotNull(conv.getName());
        assertNotNull(conv.getMessages());
    }

    public void buildWriter() {
        // delete the chat.json file
        File file =  new File(config.outputFilePath);
        file.delete();

        // obtain conversation from reader
        Conversation conv = exporter.buildReader(config.inputFilePath);

        // buildWriter() should create a new file at same location
        exporter.buildWriter(config.outputFilePath, conv);
        
        // file must exist (correctness checked in WriterTests)
        assertTrue(file.exists());
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
