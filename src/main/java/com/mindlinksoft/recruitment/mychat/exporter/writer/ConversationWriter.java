package com.mindlinksoft.recruitment.mychat.exporter.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

public class ConversationWriter implements ConversationWriterService {
    
    private final String outputFilePath;
    private final Conversation conversation; // object you wish to convert to json
    
    /**
     * Creates writer that will produce a Json file from the given conversation object
     * @param outputFilePath where you wish the output file to be placed
     * @param conversation the object you wish to write to json
     */
    public ConversationWriter(String outputFilePath, Conversation conversation) {
        this.conversation = conversation;
        this.outputFilePath = outputFilePath;
    }

    /**
     * Writes the file according to the parameters set in the constructor.
     * The file will be output as a Json file.
     */
    public void write() {
        try (BufferedWriter bWriter = Files.newBufferedWriter(Paths.get(outputFilePath))) {
            Gson gson = createGsonBuilder();

            bWriter.write(gson.toJson(conversation));
        } catch (IOException e) {
            e.printStackTrace(); // FIXME: add logging
        }
    }

    /**
     * Creates appropriate Gson with an Instant serializer
     * @return configured gson object
     */
    public Gson createGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer()); // serialises timestamps
        gsonBuilder.disableHtmlEscaping(); // prevents escaping of certain characters e.g. '
        return gsonBuilder.create();
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public Conversation getConversation() {
        return conversation;
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}