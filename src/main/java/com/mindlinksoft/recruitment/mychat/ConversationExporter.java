package com.mindlinksoft.recruitment.mychat;


import java.io.*;

import java.time.Instant;
import java.util.ArrayList;
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
        ConversationExporterConfiguration config = new CommandLineArgumentParser().parseArguments(args);

        exporter.exportConversation(config.inputFilePath, config.outputFilePath, config.option);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String input, String output, String[] option) throws Exception {
    	Conversation conversation = this.readConversation(input);
    	
     	switch (option[0]) {
     	case "user":
     		UserFilter uf = new UserFilter(option);
     		conversation = uf.filterMessages(conversation);
     	case "key":
     		
     	case "hide":
     	}

        this.writeConversation(conversation, output);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + input + "' to '" + output);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String output) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(output, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            String jsonConvo = InstantSerializer.createJsonSerialized(conversation);
            bw.write(jsonConvo);
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new FileNotFoundException("The file '" + output + "'was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new IOException("Something went wrong");
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String input) throws Exception {
        try(InputStream is = new FileInputStream(input);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3); // Splits each string to 3 substrings

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The file '" + input + "' was not found.");
        } catch (IOException e) {
            throw new IOException("Something went wrong");
        }
    }

}
