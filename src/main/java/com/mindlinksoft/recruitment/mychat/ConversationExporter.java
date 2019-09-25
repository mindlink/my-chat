package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser()
                .parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.userFilter,
                configuration.keyword, configuration.redacted);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to
     * {@code outputFilePath}.
     * 
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String userFilter, String keyword,
            String redacted) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);

        // FILTER BY USER IF USERFILTER IS NOT NULL
        if (!userFilter.isEmpty()) {
            System.out.println("filtering by user: " + userFilter);
            conversation = new Conversation(conversation.name, filterByUser(conversation, userFilter));
        }

        // FILTER BY KEYWORD IF KEYWORD IS NOT NULL
        if (!keyword.isEmpty()) {
            System.out.println("filtering keyword: " + keyword);
            conversation = new Conversation(conversation.name, filterByKeyword(conversation, keyword));
        }

        // REDACT CONTENT
        if (!redacted.isEmpty()) {
            System.out.println("redacting: " + redacted);
            conversation = new Conversation(conversation.name, redactContent(conversation, redacted));
        }

        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    /**
     * Returns a new Collection of Messages that were sent by a specific user
     * 
     * @param conversation
     * @param outputFilePath
     * @throws Exception
     */
    public Collection<Message> filterByUser(Conversation conversation, String userFilter) {

        Predicate<Message> filter = item -> item.senderId.equals(userFilter);
        Collection<Message> filteredCollection = conversation.messages;

        return filteredCollection.stream().filter(filter).collect(Collectors.toList());
    }

    /**
     * Returns a new Collection of Messages that contain a keyword
     * 
     * @param conversation
     * @param keyword
     * @return
     */
    public Collection<Message> filterByKeyword(Conversation conversation, String keyword) {

        Predicate<Message> filter = item -> item.content.contains(keyword);
        Collection<Message> filteredCollection = conversation.messages;

        return filteredCollection.stream().filter(filter).collect(Collectors.toList());
    }

    /**
     * Returns a new Collection of Messages with redacted content
     * 
     * @param conversation
     * @param redacted
     * @return
     */
    public Collection<Message> redactContent(Conversation conversation, String redacted) {

        Collection<Message> messages = conversation.messages;

        for (Message message : messages) {
            message.content = message.content.replace(redacted, "*redacted*");
        }

        return messages;
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given
     * {@code outputFilePath}.
     * 
     * @param conversation   The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the
        // stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Something went wrong");
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
        try (InputStream is = new FileInputStream(inputFilePath);
                BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");

                Instant time = Instant.ofEpochSecond(Long.parseUnsignedLong(split[0]));
                String sender = split[1];
                String[] contentArray = Arrays.copyOfRange(split, 2, split.length);
                String content = String.join(" ", contentArray);

                messages.add(new Message(time, sender, content));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
