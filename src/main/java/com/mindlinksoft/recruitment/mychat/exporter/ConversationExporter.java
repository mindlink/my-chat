package com.mindlinksoft.recruitment.mychat.exporter;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.conversation.ConversationInterface;
import com.mindlinksoft.recruitment.mychat.serialization.InstantSerializer;

import java.io.*;
import java.time.Instant;

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
        try (FileWriter fileWriter = new FileWriter(outputFilePath, false)) {

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
}