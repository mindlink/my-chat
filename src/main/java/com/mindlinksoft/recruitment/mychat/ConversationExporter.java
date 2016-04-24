package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();

        // Parse command line arguments
        CmdLineParser parser = new CmdLineParser(configuration);

        try {
            parser.parseArgument(args);

            // Start exporting conversation
            exporter.exportConversation(configuration.inputFilePath,
                    configuration.outputFilePath,
                    configuration.userFilter,
                    configuration.keywordFilter,
                    configuration.blacklistFile,
                    configuration.isObfuscate,
                    configuration.isHidingPersonalInfo);
        } catch (CmdLineException | IllegalArgumentException e) {
            // Incorrect arguments, print error and command usage, exit with an error
            System.err.println(e.getMessage());
            System.err.println(Resources.COMMAND_USAGE);
            parser.printUsage(System.err);

            System.exit(1);
        } catch (IOException e) {
            // IO error, exit program with an error
            System.err.println(Resources.FILE_IO_ERROR);
            System.err.println(e.getMessage());

            System.exit(1);
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath,
                                   String outputFilePath,
                                   String userFilter,
                                   String keywordFilter,
                                   String blacklistFile,
                                   boolean obfuscateNames,
                                   boolean hidePersonalInfo) throws IllegalArgumentException, IOException {
        Conversation conversation = this.readConversation(inputFilePath);

        // Apply filters before writing final conversation JSON
        if (userFilter != null) {
            System.out.format(Resources.USER_FILTER_MESSAGE, userFilter);
            conversation.applyUserFilter(userFilter);
        }

        if (keywordFilter != null) {
            System.out.format(Resources.KEYWORD_FILTER_MESSAGE, keywordFilter);
            conversation.applyKeywordFilter(keywordFilter);
        }

        if (blacklistFile != null) {
            System.out.print(Resources.BLACKLIST_FILTER_MESSAGE);
            conversation.applyBlacklist(blacklistFile);
        }

        if (obfuscateNames) {
            System.out.print(Resources.OBFUSCATE_MESSAGE);
            conversation.obfuscateUserNames();
        }

        if (hidePersonalInfo) {
            System.out.print(Resources.PERSONAL_MESSAGE);
            conversation.hidePersonalInformation();
        }

        // Write final json to a file
        this.writeConversation(conversation, outputFilePath);

        System.out.format(Resources.EXPORT_SUCCESS, inputFilePath, outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws IOException {
        // Delete the json file if it already exists
        File file = new File(outputFilePath);
        if (file.exists()) {
            file.delete();
        }

        // Write JSON representation of the conversation to the file
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws IllegalArgumentException, IOException {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(Resources.FILE_NOT_FOUND);
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
