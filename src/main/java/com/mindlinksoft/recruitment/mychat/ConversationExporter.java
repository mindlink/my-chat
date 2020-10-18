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
import java.util.Arrays;
import java.util.List;

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
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param config The input configuration for conversation exporter.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration config) throws Exception {
        String inputFilePath = config.inputFilePath;
        String outputFilePath = config.outputFilePath;
        String filterUserName = config.userName;
        String filterWord = config.keyWord;
        String[] redactedWords = config.redactedWords;
        boolean reportRequested = config.reportRequested;

        Conversation conversation = this.readConversation(inputFilePath);

        this.writeConversation(conversation, config);

        String exportFeedback = ("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "'  ");

        if(filterUserName != null){
            exportFeedback = exportFeedback.concat("filtered by user name '" + filterUserName + "' ");
        }
        if(filterWord != null){
            exportFeedback = exportFeedback.concat("filtered by word '" + filterWord + "' ");
        }
        if(redactedWords != null){
            exportFeedback = exportFeedback.concat("words redacted: '" + Arrays.toString(redactedWords)) + "' ";
        }
        if(reportRequested == true){
            exportFeedback = exportFeedback.concat("conversation report generated"+ " ");
        }

        // TODO: Add more logging...
        System.out.println(exportFeedback);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param config The input configuration for conversation exporter.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, ConversationExporterConfiguration config) throws Exception {
        String outputFilePath = config.outputFilePath;
        String filterUserName = config.userName;
        String filterWord = config.keyWord;
        String[] redactedWords = config.redactedWords;
        boolean reportRequested = config.reportRequested;


        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath, true)))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            if(filterUserName != null){
                conversation.filterByUserName(filterUserName);
            }
            if(filterWord != null){
                conversation.filterByKeyWord(filterWord);
            }
            if(redactedWords != null){
                conversation.redactByKeyWords(redactedWords);
            }
            if(reportRequested == true){
                conversation = new Conversation (conversation, conversation.generateReport());
            }

            bw.write(g.toJson(conversation));
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("Error'" + e.getMessage() + "'");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new IOException("Error '" + e.getMessage() + "'");
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
                String[] split = line.split(" ");
                int contentSplitPosition = 2; // +2 for removed " " during line.split();
                int contentStartingIndex = split[0].length()+split[1].length()+ contentSplitPosition;
                String content = line.substring(contentStartingIndex);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
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
