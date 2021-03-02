package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
        // We use picocli to parse the command line - see https://picocli.info/
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

        try {
            ParseResult parseResult = cmd.parseArgs(args);

            if (parseResult.isUsageHelpRequested()) {
                cmd.usage(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnUsageHelp());
                return;
            }

            if (parseResult.isVersionHelpRequested()) {
                cmd.printVersionHelp(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnVersionHelp());
                return;
            }

            ConversationExporter exporter = new ConversationExporter();

            exporter.exportConversation(configuration);

            System.exit(cmd.getCommandSpec().exitCodeOnSuccess());
        } catch (ParameterException ex) {
            cmd.getErr().println(ex.getMessage());
            if (!UnmatchedArgumentException.printSuggestions(ex, cmd.getErr())) {
                ex.getCommandLine().usage(cmd.getErr());
            }

            System.exit(cmd.getCommandSpec().exitCodeOnInvalidInput());
        } catch (Exception ex) {
            ex.printStackTrace(cmd.getErr());
            System.exit(cmd.getCommandSpec().exitCodeOnExecutionException());
        }
    }

    /**
     * Exports the conversation according to the configuration.
     * @param config The configuration of command line arguments, specifying
     *               input and output filepaths, users and keywords for filtering,
     *               words to be redacted, and whether a report should be
     *               included in the output.
     */
    public void exportConversation(ConversationExporterConfiguration config) throws Exception {
        Conversation conversation = this.readConversation(config.inputFilePath);

        ConversationEditor editor = new ConversationEditor(config);
        editor.editConversation(conversation);

        this.writeConversation(conversation, config.outputFilePath);

        // DONE: Add more logging...
        System.out.println("Conversation '" + conversation.name + "' exported from '" + config.inputFilePath + "' to '" + config.outputFilePath + "'.");
        System.out.println("Export contains " + conversation.messages.size() + " messages from " + editor.composeReport(conversation).size() + " senders.");
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // DONE: Do we need both to be resources, or will buffered writer close the stream?
        /* Honestly, I'm not really sure - I've tried to do a bit of reading up on this
         * and I think the answer is that they should both be resources. The reason being
         * that if there is a problem opening the buffered writer, and an exception gets
         * thrown, then the output stream will still be properly closed.
        */
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            /* ... I'm unsure of what this is asking for. I tried making gsonBuilder and g
             * member variables, but I don't think that makes it more testable, since you
             * still need to change the type adapter to use InstantDeserializer...
             */
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
        } catch (FileNotFoundException e) {
            // DONE: Maybe include more information?
            throw new IllegalArgumentException("Output file not found: " + e.getMessage());
        } catch (IOException e) {
            // DONE: Should probably throw different exception to be more meaningful :/
            throw new IOException("Exception when outputting the conversation: output stream may have been closed.");
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
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
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Exception when reading the conversation: input stream may have been closed.");
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
