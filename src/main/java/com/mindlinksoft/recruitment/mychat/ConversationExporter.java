package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.ParseException;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {
	
	

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws IOException Thrown when something bad happens.
     * @throws ParseException 
     */
    public static void main(String[] args) throws IOException, ParseException {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath());
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws IOException Thrown when the file cannot be exported due to read/write problems or a bad filepath.
     */
    public void exportConversation(String inputFilePath, String outputFilePath) throws IOException, IllegalArgumentException{
        Conversation conversation = this.readConversation(inputFilePath);

        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws IOException The file path where the conversation should be written.
     * @throws IllegalArgumentException if the filepath is incorrect (there is no file at the given filepath
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws IOException, IllegalArgumentException {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
            

            Gson g = gsonBuilder.create();
            String json = g.toJson(conversation);

            bw.write(json);
            bw.close();
            os.close();
            
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file could not be written at the given filepath (Check the directories exist).");
        } catch (IOException e) {
            throw new IOException("Problem writing conversation to file: " + e.getLocalizedMessage());
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws IOException when there is a problem reading from the file
     * @throws IllegalArgumentException if the filepath is incorrect (there is no file at the given filepath)
     */
    public Conversation readConversation(String inputFilePath) throws IllegalArgumentException, IOException {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");  
                
                String message = String.join(" ", Arrays.copyOfRange(split, 2, split.length));
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], message));
            }

            is.close();
            r.close();
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found at the given filepath (Check the filepath is correct).");
        } catch (IOException e) {
            throw new IOException("Problem reading conversation from file: " + e.getLocalizedMessage());
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
