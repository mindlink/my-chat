package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.Filters.*;


/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws IOException {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new CommandLineArgumentParser().parseArguments(args);

        exporter.exportConversation(config.inputFilePath, config.outputFilePath, config.option);
//        String[] sds = {"w", "3", "4"};
//        String[] ss = Arrays.copyOfRange(sds, 1, 1+2);
//        for (String s : ss) {
//        	System.out.println(s);
//        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String input, String output, String[] option) throws IOException {
    	Conversation conversation = this.readConversation(input);
    	
    	for (int i = 0; i < option.length; i++) {
    		if (option[i].contains("-")) {
    			String[] arguments = {"", ""};
				switch (option[i]) {
				case "-user":
					arguments = Arrays.copyOfRange(option, i, i+2);
					Filter uf = new UserFilter(arguments);
					conversation = uf.filterMessages(conversation);
					System.out.println("Messages from '" + option[1] + "' exported from '" + input + "' to '" + output + "'");
					i++;
					break;
				case "-key":
					arguments = Arrays.copyOfRange(option, i, i+2);
					Filter kw = new KeywordFilter(arguments);
					conversation = kw.filterMessages(conversation);
					System.out.println("Messages including '" + option[1] + "' exported from '" + input + "' to '" + output + "'");
					i++;
					break;
				case "-hidewords":
					arguments = Arrays.copyOfRange(option, i, i+2);
					Filter bl = new BlacklistFilter(arguments);
					conversation = bl.filterMessages(conversation);
					System.out.println("Blacklisted words filtered. Conversation exported from '" + input + "' to '" + output + "'");
					i++;
					break;
				case "-hidenum":
					arguments[0] = "-hidenum";
					Filter nf = new NumberFilter(arguments);
					conversation = nf.filterMessages(conversation);
					System.out.println("Card and phone numbers redacted. Conversation exported from '" + input + "' to '" + output + "'");
					break;
				case "-obf":
					arguments[0] = "-obf";
					Filter obf = new ObfuscateIDFilter(arguments);
					conversation = obf.filterMessages(conversation);
					System.out.println("Sender Ids obfuscated. Conversation exported from '" + input + "' to '" + output + "'");
					break;
				}
    		}
    		else {
				System.out.println("Conversation exported from '" + input + "' to '" + output + "'");
				i++;
    		}
    	}
    	this.writeConversation(conversation, output);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String output) throws IOException {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(output, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            String jsonConvo = InstantSerializer.createJsonSerialized(conversation);
            bw.write(jsonConvo);
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new FileNotFoundException("The file '" + output + "' was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new IOException("BufferedWriter failed to write file");
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String input) throws IOException {
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
            throw new IOException("BufferedReader failed to read file");
        }
    }

}
