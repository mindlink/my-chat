package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

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
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);


        exporter.exportConversation(configuration);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param configuration configuration for arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
        String inputFilePath = configuration.inputFilePath;
        String outputFilePath = configuration.outputFilePath;
        Conversation conversation = this.readConversation(inputFilePath);
        conversation.filterSpecificKeyWords(configuration.keyword);
        conversation.filterBySpecificUser(configuration.user);
        conversation.hideSpecificWord(configuration.blacklist);
        conversation.generateReport();

        this.writeConversation(conversation, outputFilePath);

        System.out.println("Users is:" + configuration.user);
        System.out.println("Keyword is: " + configuration.keyword);
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        System.out.println("Blacklist is: " + Arrays.toString(configuration.blacklist));
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        try (OutputStream os = new FileOutputStream(outputFilePath, false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();
            bw.write(g.toJson(conversation));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new IllegalArgumentException("Arguments are malformed ");
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
            if (conversationName.trim().isEmpty()){
                throw new IllegalArgumentException("No conversation name");
            }

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ",3);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            if (messages.isEmpty()){
                throw new IllegalArgumentException("No messages in conversation");
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
