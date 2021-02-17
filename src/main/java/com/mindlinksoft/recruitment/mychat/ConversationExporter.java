package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;
// import com.mindlinksoft.recruitment.mychat.options.*;
import com.mindlinksoft.recruitment.mychat.options.FilterByUser;
import com.mindlinksoft.recruitment.mychat.options.Options;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static final Logger logger = LogManager.getLogger(ConversationExporter.class);

    /**
     * The application entry point.
     * 
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        logger.trace("Conversation Exporter started");

        // We use picocli to parse the command line - see https://picocli.info/
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

        try {
            ParseResult parseResult = cmd.parseArgs(args);

            if (parseResult.isUsageHelpRequested()) {
                logger.info("Help requested by user with the '--help' command");
                cmd.usage(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnUsageHelp());
                return;
            }

            if (parseResult.isVersionHelpRequested()) {
                logger.info("Version requested by user with the '--version' command");
                cmd.printVersionHelp(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnVersionHelp());
                return;
            }

            ConversationExporter exporter = new ConversationExporter();

            exporter.exportConversation(configuration, parseResult);
            logger.trace("Conversation Exporter ended");
            System.exit(cmd.getCommandSpec().exitCodeOnSuccess());
        } catch (ParameterException e) {
            logger.error("Unexpected error occured concerning the parameter(s). With error message: " + e.getMessage());
            if (!UnmatchedArgumentException.printSuggestions(e, cmd.getErr())) {
                e.getCommandLine().usage(cmd.getErr());
            }

            System.exit(cmd.getCommandSpec().exitCodeOnInvalidInput());
        } catch (Exception e) {
            logger.error("Unexpected error occured concerning the parameter(s). With error message: " + e.getMessage()
                    + " & " + cmd.getErr());
            System.exit(cmd.getCommandSpec().exitCodeOnExecutionException());
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to
     * {@code outputFilePath}.
     * 
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration configuration, ParseResult parseResult)
            throws Exception {
        Conversation conversation = this.readConversation(configuration.inputFilePath);
        logger.trace("Conversation loadded into memory from file: " + configuration.inputFilePath);

        Options savedOptions = new Options(conversation, configuration);
        conversation = savedOptions.applyOptionsToConversation();

        this.writeConversation(conversation, configuration.outputFilePath);
        logger.info(
                "Conversation exported from '" + configuration.inputFilePath + "' to '" + configuration.outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given
     * {@code outputFilePath}.
     * 
     * @param conversation   The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath)
            throws FileNotFoundException, IOException {
        // TODO: Do we need both to be resources, or will buffered writer close the
        // stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, false);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.setPrettyPrinting().create();
            String convertedConversation = g.toJson(conversation);
            logger.trace("Conversation converted to JSON");

            bw.write(convertedConversation);

            // release system resources from stream operations
            bw.close();
            os.close();

            logger.trace("Conversation written to JSON file: " + outputFilePath);

        } catch (FileNotFoundException e) {
            logger.error("The file '" + outputFilePath + "'" + "was not found." + "\n With the error message: "
                    + e.getMessage());
            throw new FileNotFoundException("The file '" + outputFilePath + "'" + "was not found."
                    + "\n With the error message: " + e.getMessage());
        } catch (IOException e) {
            logger.error("Error occured while writting to the file '" + outputFilePath + "'"
                    + "\n With the error message: " + e.getMessage());
            throw new IOException("Error occured while writting to the file '" + outputFilePath + "'"
                    + "\n With the error message: " + e.getMessage());
        }
    }

    /**
     * Represents a helper to read a conversation from the given
     * {@code inputFilePath}.
     * 
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws FileNotFoundException Thrown when the given file is not found.
     * @throws IOException           Thrown when issue with reading the data from
     *                               given file.
     */
    public Conversation readConversation(String inputFilePath) throws FileNotFoundException, IOException {
        try (InputStream is = new FileInputStream(inputFilePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ", 3);
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            // release system resources from stream operations
            br.close();
            is.close();

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            logger.error("The file '" + inputFilePath + "'" + "was not found." + "\n With the error message: "
                    + e.getMessage());
            throw new FileNotFoundException("The file '" + inputFilePath + "'" + "was not found."
                    + "\n With the error message: " + e.getMessage());
        } catch (IOException e) {
            logger.error("Error occured while reading the file '" + inputFilePath + "'" + "\n With the error message: "
                    + e.getMessage());
            throw new IOException("Error occured while reading the file '" + inputFilePath + "'"
                    + "\n With the error message: " + e.getMessage());
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
