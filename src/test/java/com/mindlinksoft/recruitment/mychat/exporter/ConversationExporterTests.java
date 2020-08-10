package com.mindlinksoft.recruitment.mychat.exporter;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.Modifier;
import com.mindlinksoft.recruitment.mychat.main.ConversationExporterConfiguration;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
    Modifier modifier;
    String modifierArgument;

    @Before
    public void setUp() {
        inputFilePath = "chat.txt";
        outputFilePath = "chat.json";
        modifier = Modifier.HIDE_KEYWORD;
        modifierArgument = "pie";
        
        config = new ConversationExporterConfiguration(inputFilePath, outputFilePath);

        exporter = new ConversationExporter(config);
    }

    @Test
    public void export() {
        // exporter should work correctly with valid arguments
        // i.e. no exceptions thrown
        exporter.export();

        // file is output when finished (correctness checked in WriterTest)
        File file =  new File(config.getOutputFilePath());
        assertTrue(file.exists());
    }

    @Test
    public void buildReader() {
        // buildReader should read the input file and return a conversation
        Conversation conv = exporter.buildReader(config.getInputFilePath());

        // resultant conversation should be not null
        // (correctness is checked in ConversationReaderTests)
        assertNotNull(conv.getName());
        assertNotNull(conv.getMessages());
    }

    @Test
    public void buildModifier() {
        Conversation originalConversation = exporter.buildReader(config.getInputFilePath());
        Conversation result = exporter.buildModifier(originalConversation, modifier, modifierArgument);

        // conversation name should be the same
        assertEquals(originalConversation.getName(), result.getName());

        // resultant conversation should be not null
        // (correctness is checked in ConversationReaderTests)
        assertNotNull(result.getMessages());
    }

    
    @Test
    public void buildWriter() {
        // delete the chat.json file
        File file =  new File(config.getOutputFilePath());
        file.delete();

        // obtain conversation from reader
        Conversation conv = exporter.buildReader(config.getInputFilePath());

        // buildWriter() should create a new file at same location
        exporter.buildWriter(config.getOutputFilePath(), conv);
        
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
