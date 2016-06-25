package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

	/**Private gson instance used to serialize Java types into JSON
	 * appropriately*/
	private static Gson gson = null;

	/**
	 * Default Constructor*/
	public ConversationExporter() {
		init();
	}

	/**
	 * Mandatory instance construction instance-invariant delegate method, 
	 * initializes static Gson variable used to serialize Java types into JSON*/
	private static void init() {
		if(gson == null) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Instant.class, 
													new InstantSerializer());

			gson = gsonBuilder.create();
		}
	}

	/**
	 * The application entry point.
	 * @param args The command line arguments.
	 * @throws IOException TODO do not throw from main
	 */
	public static void main(String[] args) throws IOException {
		ConversationExporter exporter = new ConversationExporter();
		ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

		exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath);
	}

	/**
	 * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @throws Exception Thrown when something bad happens.
	 */
	public void exportConversation(String inputFilePath, String outputFilePath) throws IOException {
		
		Conversation conversation = this.readConversation(inputFilePath);
		this.writeConversation(conversation, outputFilePath);

		// TODO: Add more logging...
		System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
	}

	/**
	 * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
	 * @param conversation The conversation to write.
	 * @param outputFilePath The file path where the conversation should be written.
	 * @throws IOException 
	 */
	public void writeConversation(Conversation conversation, String outputFilePath) throws IOException {
		BufferedWriter w = null;
		try {
			w = Files.newBufferedWriter(Paths.get(outputFilePath));

			w.write(gson.toJson(conversation));
		} catch (FileNotFoundException e) {
			// TODO: Maybe include more information?
			// NT: like what?
			throw new IllegalArgumentException(e.getMessage());
		} finally {
			w.close();
		}

	}

	/**
	 * Returns Conversation object from provided {@code inputFilePath}.
	 * @param inputFilePath The path to the input file.
	 * @return The {@link Conversation} representing by the input file.
	 * @throws IOException Thrown when input file IO error occurs.
	 */
	public Conversation readConversation(String inputFilePath) throws IOException {
		BufferedReader r = null;
		try {
			r = Files.newBufferedReader(Paths.get(inputFilePath));

			List<Message> messages = new ArrayList<Message>();

			String conversationName = r.readLine();
			String line;

			while ((line = r.readLine()) != null) {
				String[] split = line.split(" ", 3);
				String messageContent = line.substring(line.indexOf(' ', 11) + 1);
				messages.add(new Message(
						Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])),
						split[1], 
						messageContent));
			}

			return new Conversation(conversationName, messages);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The file was not found.");
		} finally {
			r.close();
		}
	}

	/**
	 * Static inner class used to provide custom serialization into JSON for 
	 * Instant Java class*/
	static private class InstantSerializer implements JsonSerializer<Instant> {
		@Override
		public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(instant.getEpochSecond());
		}
	}
}
