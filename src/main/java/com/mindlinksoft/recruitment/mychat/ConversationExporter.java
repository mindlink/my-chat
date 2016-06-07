package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.sun.javafx.font.CharToGlyphMapper;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration config) throws Exception {
        Conversation conversation = this.readConversation(config.inputFilePath);
        if (config.usernameFilter.length() > 0) conversation = ChatFilter.filterByUser(conversation, config.usernameFilter);
        if (config.keywordFilter.length() > 0) conversation = ChatFilter.filterByKeyword(conversation, config.keywordFilter);
        if (config.blacklistWords.length > 0) conversation = ChatFilter.filterOutWordList(conversation, config.blacklistWords);
        if (config.phoneNumberFilter) conversation = ChatFilter.filterOutPhoneNumbers(conversation);
        if (config.creditCardFilter) conversation = ChatFilter.filterOutCardNumbers(conversation);
        this.writeConversation(conversation, config.outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + config.inputFilePath + "' to '" + config.outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens
     * TODO:EXPAND ON THIS^ (exceptions)
     * TODO:PASS BY REFERENCE
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (
                OutputStream os = new FileOutputStream(outputFilePath, true);
                // TODO: If os is undefined then next line will error out
                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))
        ) {
            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation) + "\n");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Error: " + e.getMessage() + "\nFileOutputStream() - If the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason then a FileNotFoundException is thrown." );
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new IOException("Error: " + e.getMessage() + "\n");
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        //Is this the right syntax?
        // try {  *risky code*  } catch (ExceptionType name) {  *deal with it* } ;
        try(
                InputStream is = new FileInputStream(inputFilePath);
                BufferedReader r = new BufferedReader(new InputStreamReader(is))
        ) {

            ArrayList<Message> messages = new ArrayList<Message>();
            HashSet<String> users = new HashSet<String>();

            String conversationName = r.readLine();
            String line;

            Pattern pattern = Pattern.compile("(?m)^(\\d{10})\\s(\\w+)\\s(.+)$");


            while ((line = r.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    users.add(matcher.group(2));
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(matcher.group(1))), matcher.group(2), matcher.group(3)));
                }
            }
            return new Conversation(conversationName, users.toArray(new String[users.size()]), messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    private class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
