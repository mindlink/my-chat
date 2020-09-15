package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.exceptions.IOProblemException;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;
import com.mindlinksoft.recruitment.mychat.utils.UtilOperations;

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
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(configuration);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     *
     * @param conversation The conversation to write.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, ConversationExporterConfiguration configuration) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(configuration.outputFilePath))) {

            Conversation result = checkRequirements(conversation, configuration);
            bw.write(gsonInitializer().toJson(result));

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The output file path is not valid.", e.getCause());
        } catch (IOException e) {
            throw new IOProblemException("Something went wrong writing the conversation to the output file Path.", e.getCause());
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
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
                String content = UtilOperations.getCommandLineDataInput(split);
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found, please correct the input file path");
        } catch (IOException e) {
            throw new IOProblemException("Something went wrong reading the input file.", e.getCause());
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     *
     * @param configuration contains all the relevant information
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
        Conversation conversation = readConversation(configuration.inputFilePath);
        writeConversation(conversation, configuration);
        printLog(configuration);
    }

    /**
     * Creates a gson
     * @return a gson
     */
    public Gson gsonInitializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new ConversationExporter.InstantSerializer());
        return gsonBuilder.create();
    }

    public static class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }

    /**
     * Prints logs with detailed information
     * @param configuration containing the program arguments
     */
    private void printLog(ConversationExporterConfiguration configuration) {
        if (!configuration.commandInput.equals("null")) {
            System.out.println("The command <" + configuration.commandInput.split(":")[0] + "> with value(s) <" + configuration.commandInput.split(":")[1] + "> was applied.");
        }
        if (!configuration.features.equals("null")) {
            System.out.println("The feature <" + configuration.features + "> was applied.");
        }
        System.out.println("The output file is available at: " + configuration.outputFilePath);
        System.out.println("--- --- --- --- --- --- --- --- ---");
    }

    /**
     * Verifies which program arguments were chosen to be executed
     * @param conversation containing all the messages
     * @param configuration containing all the program arguments
     * @return a conversation with the applied commands and/or features
     */
    private Conversation checkRequirements(Conversation conversation, ConversationExporterConfiguration configuration) throws Exception {
        if (!configuration.commandInput.contains("null") && !configuration.features.contains("null")) {
            return AdditionalFeatures.optionalFeatures(EssentialFeatures
                    .applyFilters(conversation, configuration.commandInput), configuration.features);
        }

        if (configuration.commandInput.contains("null") && !configuration.features.contains("null")) {
            return AdditionalFeatures.optionalFeatures(conversation, configuration.features);
        }

        if (!configuration.commandInput.contains("null")) {
            return EssentialFeatures.applyFilters(conversation, configuration.commandInput);
        }
        return conversation;
    }


}
