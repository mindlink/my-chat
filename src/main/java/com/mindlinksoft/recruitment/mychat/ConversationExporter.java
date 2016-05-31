package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it
 * out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.export(configuration);
    }

    public void export(ConversationExporterConfiguration configuration) throws Exception {

        Conversation conversation = this.readConversation(configuration.inputFilePath);
        System.out.println("Conversation imported from '" + configuration.inputFilePath);

        if (!configuration.userFilter.equals("")) {
            conversation = new FilterUser().Filter(conversation, configuration.userFilter, null);
            System.out.println("userFilter applied, only showing mesages from:"+ configuration.userFilter);
        }
        if (!configuration.keywordFilter.equals("")) {
            conversation = new FilterKeyword().Filter(conversation, configuration.keywordFilter, null);
            System.out.println("keywordFilter applied, only showing mesages containing:"+ configuration.keywordFilter);
        }
        if (!configuration.blacklistFilePath.equals("")) {
            conversation = new FilterBlacklist().Filter(conversation, importBlacklist(configuration.blacklistFilePath), "*redacted*");
            System.out.println("FilterBlacklist applied, replacing words to *redacted* from blacklist:"+ configuration.blacklistFilePath);
        }
        if (configuration.hideNumbers) {
            conversation = new FilterNumbers().Filter(conversation, null, "*redacted*");
            System.out.println("hideNumbers applied, replacing numbers of length greater than 10 to *redacted*");
        }
        if (configuration.obfuscateID) {
            conversation = new FilterObfuscateID().Filter(conversation, null, null);
            System.out.println("obfuscateID applied, replacing userIDs with obfuscateID-n");
        }
        
        conversation.refreshUsers();

        this.writeConversation(conversation, configuration.outputFilePath);
        System.out.println(" - Conversation exported from '" + configuration.inputFilePath + "' to '" + configuration.outputFilePath);
    }

    public String[] importBlacklist(String blacklistFilePath) throws Exception {
        String[] blacklist;
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(blacklistFilePath)));

            String line = r.readLine();
            line = line.replaceAll("\n", "");
            blacklist = line.split(", ");

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file: " + blacklistFilePath + " was not found.");
        } catch (IOException e) {
            throw new Exception("importBlacklist error: " + e);
        }
        return blacklist;
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the
     * given {@code outputFilePath}.
     *
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be
     * written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        try {

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath, false)));// Overwrites Output file, make Warning!!

            bw.write(serializeToJson((Object) conversation));
            bw.close();

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file: " + outputFilePath + " was not found.");
        } catch (IOException e) {
            throw new Exception("writeConversation error: " + e);
        }
    }

    /**
     * Represents a helper to read a conversation from the given
     * {@code inputFilePath}.
     *
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3); // Split into 3 parts: TimeStamp, SenderID, Content 

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file: " + inputFilePath + " was not found.");
        } catch (IOException e) {
            throw new Exception("readConversation error: " + e);
        }
    }

    public String serializeToJson(Object content) throws Exception {
        Gson g = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantSerializer()).create();
        try {
            return g.toJson(content);
        } catch (JsonParseException e) {
            throw new Exception("JsonParseException: " + e);
        }
    }

    public Object deserializeFromJson(Reader content, Class c) throws Exception {
        Gson g = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantDeserializer()).create();
        try {
            return g.fromJson(content, c);
        } catch (JsonParseException e) {
            throw new Exception("JsonParseException: " + e);
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {

        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
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
