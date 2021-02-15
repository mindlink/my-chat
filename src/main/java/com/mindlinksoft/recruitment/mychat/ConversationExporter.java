package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
import com.mindlinksoft.recruitment.mychat.options.*;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {
    public static final Logger logger = LogManager.getLogger(ConversationExporter.class);

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when an error occurs.
     */
    public static void main(String[] args) throws Exception {
        logger.trace("ConversationExporter program launched.");

        // We use picocli to parse the command line - see https://picocli.info/
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

        try {
            ParseResult parseResult = cmd.parseArgs(args);
        
            if (parseResult.isUsageHelpRequested()) {
                logger.info("User requested help with command by using the --help option in command");
                cmd.usage(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnUsageHelp());
                return;
            }
            
            if (parseResult.isVersionHelpRequested()) {
                logger.info("User requested the version number with command by using the --version option in command");
                cmd.printVersionHelp(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnVersionHelp());
                return;
            }

            ConversationExporter exporter = new ConversationExporter();

            exporter.exportConversation(configuration, parseResult);

            System.exit(cmd.getCommandSpec().exitCodeOnSuccess());
        } catch (ParameterException ex) {
            logger.warn("Unknown command option used in command: " + cmd.parseArgs().unmatched());
            cmd.getErr().println(ex.getMessage());
            if (!UnmatchedArgumentException.printSuggestions(ex, cmd.getErr())) {
                ex.getCommandLine().usage(cmd.getErr());
            }

            System.exit(cmd.getCommandSpec().exitCodeOnInvalidInput());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace(cmd.getErr());
            System.exit(cmd.getCommandSpec().exitCodeOnExecutionException());
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param configuration All of the configuration options for exporting. Includes input file path, output file path and additional options
     * @param parseResult A ParseResult object of command line options
     * @throws IllegalArgumentException Thrown when the conversation file is not found at the given path
     * @throws IOException Thrown when there is an issue with writing to the output file
     */
    public void exportConversation(ConversationExporterConfiguration configuration, ParseResult parseResult) throws IllegalArgumentException, IOException {
        Conversation conversation = this.readConversation(configuration.inputFilePath);

        // Handle processing option from arguments in command
        HashMap<String, ConversationExportOptionInterface> optionMap = new HashMap<String, ConversationExportOptionInterface>();
        optionMap.put("report", new ReportOption());
        optionMap.put("filterByUser", new ByUserFilter(configuration.filterUserID));
        optionMap.put("filterByKeyword", new ByKeywordFilter(configuration.filterKeyword));
        optionMap.put("blacklist", new BlacklistFilter(configuration.blacklistWord));

        for (String key : optionMap.keySet()) {
            logger.trace("Now processing all conversation export options");
            if (parseResult.hasMatchedOption(key)) {
                optionMap.get(key).process(conversation);
            }
        }

        this.writeConversation(conversation, configuration.outputFilePath);

        logger.info("Conversation exported from '" + configuration.inputFilePath + "' to '" + configuration.outputFilePath + "'");
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws IllegalArgumentException Thrown when the conversation file is not found at the given path
     * @throws IOException Thrown when there is an issue with writing to the output file
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws IllegalArgumentException, IOException {
        // Given TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // Given TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.setPrettyPrinting().create();
            String json = g.toJson(conversation);

            bw.write(json);

            bw.close();
            os.close();

            logger.trace("Conversation written to JSON file at path: " + outputFilePath);

        } catch (FileNotFoundException e) {
            logger.error("A file (given by command argument) was not found at the given path: " + outputFilePath);
            throw new IllegalArgumentException("A file (given by command argument) was not found at the given path: " + outputFilePath);
        } catch (IOException e) {
            logger.error("Issue whilst writing to output file at given file path: " + outputFilePath );
            throw new IOException("Issue whilst writing to output file at given file path: " + outputFilePath + "\n" + e.getMessage());
        }
    }

    /**
     * A helper method to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws IllegalArgumentException Thrown when the input file is not found at the given path
     * @throws IOException Thrown when there is an issue with reading from the input file
     */
    public Conversation readConversation(String inputFilePath) throws IllegalArgumentException, IOException {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                String content = String.join(" ", Arrays.copyOfRange(split, 2, split.length));

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
            }

            is.close();
            r.close();

            logger.trace("Conversation read from TXT file at path: " + inputFilePath);


            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            logger.error("The input conversation file (given by command argument) was not found at the given path: " + inputFilePath);
            throw new IllegalArgumentException("The input conversation file (given by command argument) was not found at the given path: " + inputFilePath);
        } catch (IOException e) {
            logger.error("Issue whilst reading input file at given file path: "  + inputFilePath + "\n" + e.getMessage());
            throw new IOException("Issue whilst reading input file at given file path: "  + inputFilePath + "\n" + e.getMessage());
        }
    }

    static class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
