package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    private GsonBuilder gsonBuilder = new GsonBuilder();
    private static final Logger LOGGER = Logger.getLogger(ConversationExporter.class.getName());

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws IOException {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().buildConfig(args);

        exporter.exportConversation(configuration);
    }

    /**
     * Exports the conversation at {@code config.inputFilePath} as JSON to {@code config.outputFilePath}.
     * @param config the {@Link ConversationExporterConfiguration} object
     * @throws IOException when exporting the file fails.
     */

    public void exportConversation(ConversationExporterConfiguration config) throws IOException {
        Conversation conversation = this.readConversation(config);
        this.writeConversation(conversation, config);
        LOGGER.info("Conversation exported from '" + config.getInputFilePath() + "' to '" + config.getOutputFilePath());
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code config.outputFilePath}.
     * @param conversation The conversation to write.
     * @param config the {@Link ConversationExporterConfiguration} object specifying flags for how to write the conversation
     * @throws IllegalArgumentException when the specified file cannot be found
     * @throws IOException when an input/output general exception occurs
     */

    private void writeConversation(Conversation conversation, ConversationExporterConfiguration config) throws IllegalArgumentException, IOException {
        try (FileWriter fw = new FileWriter(config.getOutputFilePath(), true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            // Filter by sender first, then keyword, then obfuscateinfo, then obfuscatename ACCORDING to the command line arguments that set up the configuration object
            conversation.filterBySender(config);
            conversation.filterByKeyword(config);
            conversation.obfuscate(config);
            if(config.getBlacklist() != null && config.getBlacklist().length >0) {
                for (int x = 0; x < config.getBlacklist().length; x++) {
                    conversation.redact(config.getBlacklist()[x], "*redacted*");
                }
            }

            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
            Gson g = gsonBuilder.create();
            bw.write(g.toJson(conversation));
        } catch (FileNotFoundException e) {
            LOGGER.severe("File not found.");
            throw new IllegalArgumentException("The specified output file was not found.", e);
        } catch(IOException e){
            LOGGER.severe("Something went wrong appending to the file.");
            throw new IOException("Something went wrong appending to the file.", e);
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code config.inputFilePath}.
     * @param config the {@Link ConversationExporterConfiguration} object
     * @return The {@link Conversation} represented by the input file.
     */
    private Conversation readConversation(ConversationExporterConfiguration config) throws IllegalArgumentException, IOException {
        try(InputStream is = new FileInputStream(config.getInputFilePath());
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            ArrayList<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                // regex with timestamp, sender, message
                Pattern pattern = Pattern.compile("(\\d*) ([a-zA-Z]*) (.*)$");
                Matcher m = pattern.matcher(line);
                // If a correctly formatted message is found, add it to the messages - this means it will ignore empty messages or incorrectly formatted messages.
                if(m.find()) {
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(m.group(1))), new Sender(m.group(2)), m.group(3)));
                }
            }
            return new Conversation(conversationName, messages);
        } catch(FileNotFoundException e){
            throw new IllegalArgumentException("The specified file was not found.", e);
        } catch(IOException e){
            throw new IOException("Something went wrong while parsing the file.", e);
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
