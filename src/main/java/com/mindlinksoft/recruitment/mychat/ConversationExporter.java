package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws IOException  Thrown when either the input file cannot be read from or the output file cannot be written to.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws IOException {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        
        if(configuration !=  null) {
            exporter.exportConversation(configuration);
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param configuration A ConversationExporterConfiguration object
     * @throws IOException Thrown when either the input file cannot be read from or the output file cannot be written to.
     */
    private void exportConversation(ConversationExporterConfiguration configuration) throws IOException {
    	System.out.println("Reading conversation at '" + configuration.getInputFilePath() + "'.");
    	Conversation unfiltered = this.readConversation(configuration.getInputFilePath());
        
        ConversationFilterer filterer = new ConversationFilterer(configuration.getUserFilter(), configuration.getKeyWord(), 
        		configuration.getBlackList(), configuration.isHideNumbers(), configuration.isObfuscateIDs());
       
        System.out.println("Applying filters:\n" + filterer.toString());
        Conversation filtered = filterer.filterConversation(unfiltered);
        
        ConversationAnalyser analyser = new ConversationAnalyser();
        
        Conversation analysed = analyser.analyseConversation(filtered);
        System.out.println("Conversation analysed:\n" + Arrays.toString(analysed.getActiveUsers()));
        
        this.writeConversation(analysed, configuration.getOutputFilePath());

        System.out.println("Conversation exported from '" + configuration.getInputFilePath() + "' to '" + configuration.getOutputFilePath()+ "'.");
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws IOException Thrown when the input file cannot be read from.
     */
    private Conversation readConversation(String inputFilePath) throws IOException {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

        	ArrayList<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                String[] msg = Arrays.copyOfRange(split, 2, split.length);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], String.join(" ",msg)));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File at " + inputFilePath + " not found.");
        } catch (IOException e) {
            throw new IOException("Unable to read from file at " + inputFilePath);
        }
    }
    
    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws IOException Thrown when the output file cannot be written to.
     */
    private void writeConversation(Conversation conversation, String outputFilePath) throws IOException {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
    	
        try (OutputStream os = new FileOutputStream(outputFilePath, false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
        } catch (IOException e) {
            throw new IOException("Unable to write to file at " + outputFilePath);
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
