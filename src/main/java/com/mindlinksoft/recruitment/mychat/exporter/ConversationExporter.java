package com.mindlinksoft.recruitment.mychat.exporter;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.commandlineparser.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.conversation.ConversationInterface;
import com.mindlinksoft.recruitment.mychat.message.Message;
import com.mindlinksoft.recruitment.mychat.message.MessageInterface;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(ConversationInterface conversation, String outputFilePath) throws IllegalArgumentException, IOException {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        //lets use FileWriter for now since no images are involved in the current task
        try (FileWriter fileWriter = new FileWriter(outputFilePath, true)) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.setPrettyPrinting().disableHtmlEscaping().create();

            fileWriter.write(g.toJson(conversation));
            fileWriter.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.", e);
        } catch (IOException e) {
            throw new IOException("Something went wrong", e);
        }
    }

    /**
     * Serializes Instant
     */
    //TODO: move outside and serialize conversation
    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}