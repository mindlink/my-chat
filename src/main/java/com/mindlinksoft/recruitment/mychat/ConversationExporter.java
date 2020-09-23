package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
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
		ConversationExporterConfiguration configuration = new CommandLineArgumentParser()
				.parseCommandLineArguments(args);
		exporter.exportConversation(configuration);
	}

	/**
	 * Exports the conversation at {@code inputFilePath} as JSON to
	 * {@code outputFilePath}.
	 * 
	 * @param inputFilePath  The input file path.
	 * @param outputFilePath The output file path.
	 * @throws Exception Thrown when something bad happens.
	 */
	public void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
		String inputFilePath = configuration.getInputFilePath();
		String outputFilePath = configuration.getOutputFilePath();
		Conversation conversation = this.readConversation(configuration);
		this.writeConversation(conversation, outputFilePath);

		// TODO: Add more logging... - Maybe a separate logging class
		System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
	}

	/**
	 * Helper method to write the given {@code conversation} as JSON to the given
	 * {@code outputFilePath}.
	 * 
	 * @param conversation   The conversation to write.
	 * @param outputFilePath The file path where the conversation should be written.
	 * @throws Exception Thrown when writing to the output file fails.
	 */
	private void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
		// TODO: Do we need both to be resources, or will buffered writer close the
		// stream?
		try (OutputStream os = new FileOutputStream(outputFilePath, true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

			GsonMaker gsonMaker = new GsonMaker();
			Gson g = gsonMaker.createGson();
			bw.write(g.toJson(conversation));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The JSON output file's folder was not found.");
		} catch (IOException e) {
			throw new Exception("The program was unable to write the conversation data to the JSON output file.");
		}
	}

	/**
	 * Represents a helper to read a conversation from the given
	 * {@code inputFilePath}.
	 * 
	 * @param inputFilePath The path to the input file.
	 * @return The {@link Conversation} representing by the input file.
	 * @throws Exception Thrown when reading from the input file fails.
	 */
	private Conversation readConversation(ConversationExporterConfiguration configuration) throws Exception {
		String inputFilePath = configuration.getInputFilePath();
		try (InputStream is = new FileInputStream(inputFilePath);
				BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

			List<Message> messages = new ArrayList<Message>();

			String conversationName = r.readLine();
			String line;

			while ((line = r.readLine()) != null) {
				String[] split = line.split(" ");
				Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(split[0]));
				String senderId = split[1];
				String content = getFullMessageContent(split);
				messages.add(new Message(timestamp, senderId, content));
			}
			MessageFilters messageFilter = new MessageFilters();
			messages = messageFilter.filterMessages(messages, configuration);
			return new Conversation(conversationName, messages);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The input text file was not found.");
		} catch (IOException e) {
			throw new Exception("The program was unable to read the conversation data from the input text file.");
		}
	}

	/**
	 * Groups all the words of a message's content into one string.
	 * 
	 * @param split - An array containing a single line from the input file split by
	 *              spacing.
	 * @return fullMessageContent - The message content of a single message.
	 */
	private String getFullMessageContent(String[] split) {
		String fullMessageContent = "";
		// The message content starts at the third element.
		for (int messageIndex = 2; messageIndex < split.length; messageIndex++) {
			fullMessageContent += split[messageIndex] + " ";
		}
		fullMessageContent = fullMessageContent.substring(0, fullMessageContent.length() - 1); // removes the space at
																								// the end of the
																								// string.
		return fullMessageContent;
	}
}
