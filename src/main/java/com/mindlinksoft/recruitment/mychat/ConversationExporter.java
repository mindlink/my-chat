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

        //Assuming the argument is something like "filterword pie hidesensitive". 
        if (args.length > 3) {//if the argument's length is more than 2
            //args[2] filter type, args[3] value, args[4] hide sensitive command 
            exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, new Filter(args[2], args[3], args[4]));
        } else if (args.length < 4 && args.length > 2) {//If there is three arguments with additional hidesensitive control argument. (args[2] hide sensitive command argument)
            exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, args[2]);
        } else { //default with no hidesensitive control
            exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, "");
        }

    }

    /**
     * Exports the conversation with filters at {@code inputFilePath} as JSON to
     * {@code outputFilePath}.
     *
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param filter The filter command
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, Filter filter) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);

        filter(filter, conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to
     * {@code outputFilePath}.
     *
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param hideSensitiveData Control to hide sensitive.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String hideSensitiveData) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);

        conversation = Filter.hideSensitiveData(hideSensitiveData, conversation);

        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    /**
     * Method that filters a conversation with a specific filter type.
     *
     * @param filter The filter object for accessing static methods.
     * @param outputFilePath The output file path.
     * @param conversation The conversation from an input file path to be
     * filtered.
     * @throws Exception Thrown when something bad happens.
     */
    private void filter(Filter filter, Conversation conversation, String outputFilePath) throws Exception {

        String filterType = filter.filterType; //Used to get the type of filter fromt he argument
        String argumentValue = filter.argumentValue; //Used to get the value from the argument to be filtered. 
        String hideSensitiveControl = filter.hideSensitiveDataControl;

        //Determine the type of filter
        switch (filterType) {
            case "filteruser":
                conversation = Filter.filterByUser(conversation, argumentValue);
                conversation = Filter.hideSensitiveData(hideSensitiveControl, conversation);
                this.writeConversation(conversation, outputFilePath);
                break;
            case "filterword":
                conversation = Filter.filterByWord(conversation, argumentValue);
                conversation = Filter.hideSensitiveData(hideSensitiveControl, conversation);
                this.writeConversation(conversation, outputFilePath);
                break;
            case "hideword":
                conversation = Filter.hideWord(conversation, argumentValue);
                conversation = Filter.hideSensitiveData(hideSensitiveControl, conversation);
                this.writeConversation(conversation, outputFilePath);
                break;
        }
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

        String specificOutpuFilePath = specificFilepath(outputFilePath);

        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(specificOutpuFilePath, false);
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
     * It formats the regular file path from the argument to a codeable file
     * path. (Assuming the file path is represented as
     * "C:\User\Owner\Directory\Some Directory" into
     * "C:\\User\\Owner\\Directory\\Some Directory")
     *
     * @param filePath The file path to be identified.
     * @return It returns a specific file path if a path address is identified
     * in the argument.
     */
    public String specificFilepath(String filePath) {

        String specificOutpuFilePath;
        if (filePath.contains("\\")) {
            specificOutpuFilePath = filePath.replace("\\", "\\\\");
        } else {
            specificOutpuFilePath = filePath;
        }
        return specificOutpuFilePath;
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

        String specificFilePath = specificFilepath(inputFilePath); //Represents a specific file path if found.

        try (InputStream is = new FileInputStream(specificFilePath);
                BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3); //To get the whole content of the message 

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
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
