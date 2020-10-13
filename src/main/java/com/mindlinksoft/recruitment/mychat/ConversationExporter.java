package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.models.*;
import com.mindlinksoft.recruitment.mychat.config.*;
import com.mindlinksoft.recruitment.mychat.converter.*;

import com.google.gson.*;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it
 * out in JSON.
 */
public class ConversationExporter {

    public static ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();

    public static FilterBuilder fb;

    private GsonBuilder gsonBuilder;

    /**
     * The application entry point.
     * 
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        // We use picocli to parse the command line - see https://picocli.info/
        CommandLine cmd = new CommandLine(configuration);

        try {
            ParseResult parseResult = cmd.parseArgs(args);

            // Check if help requested
            if (parseResult.isUsageHelpRequested()) {
                cmd.usage(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnUsageHelp());
                return;
            }

            // Check if version requested
            if (parseResult.isVersionHelpRequested()) {
                cmd.printVersionHelp(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnVersionHelp());
                return;
            }

            ConversationExporter exporter = new ConversationExporter();
            exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath);

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

    // Method for creating our gsonBuilder which can be used to create json objects/files
    private void init() {
        gsonBuilder =  new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to
     * {@code outputFilePath}.
     * 
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);

        if (fb != null) {
            // Filter the conversation
            System.out.println("Screaming");
            fb.filterConversation(conversation);
        }

        this.writeConversation(conversation, outputFilePath);

        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);

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
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {            

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found and cannot create file of that type");
        } catch (IOException e) {
            throw new InterruptedException("Something went wrong, outputstream interrupted");
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

                // Added entire content of message rather than just the first word
                String[] content = Arrays.copyOfRange(split, 2, split.length);
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], String.join(" ", content)));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong, perhaps the connection was terminated");
        }
    }

    // Simple constructor to initialize the gsonBuilder
    public ConversationExporter() {
        this.init();
    }

    
    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
