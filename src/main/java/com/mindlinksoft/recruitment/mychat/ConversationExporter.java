package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.MalformedJsonException;

/**
 * Represents a conversation exporter that can read a conversation and write it
 * out in JSON.
 */
public class ConversationExporter {

	/**
	 * The application entry point.
	 * 
	 * @param args
	 *            The command line arguments.
	 * @throws Exception
	 *             Thrown when something bad happens.
	 */
	public static void main(String[] args) throws Exception {
		ConversationExporter exporter = new ConversationExporter();
		ConversationExporterConfiguration configuration = new CommandLineArgumentParser()
				.parseCommandLineArguments(args);

		exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath);
	}

	/**
	 * Exports the conversation at {@code inputFilePath} as JSON to
	 * {@code outputFilePath}.
	 * 
	 * @param inputFilePath
	 *            The input file path.
	 * @param outputFilePath
	 *            The output file path.
	 * @throws Exception
	 *             Thrown when something bad happens.
	 */
	public void exportConversation(String inputFilePath, String outputFilePath) throws Exception {
		Conversation conversation = this.readConversation(inputFilePath);

		this.writeConversation(conversation, outputFilePath);

		// System.out.println("Conversation: "+ conversation.toString());

		// TODO: Add more logging...
		System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
	}

	/**
	 * Helper method to write the given {@code conversation} as JSON to the
	 * given {@code outputFilePath}.
	 * 
	 * @param conversation
	 *            The conversation to write.
	 * @param outputFilePath
	 *            The file path where the conversation should be written.
	 * @throws Exception
	 *             Thrown when something bad happens.
	 */
	public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
		// TODO: Do we need both to be resources, or will buffered writer close
		// the stream?
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
			// TODO: Maybe reuse this? Make it more testable...
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
			Gson g = gsonBuilder.create();
			String jsonElement = g.toJson(conversation);

			// System.out.println("Writing JSON: " + jsonElement);
			bw.write(jsonElement);
			bw.close();

		} catch (FileNotFoundException e) {
			// // TODO: Maybe include more information?
			throw new IllegalArgumentException("The file was not found : " + e.getMessage());
		} catch (MalformedJsonException e) {
			throw new MalformedJsonException("JSON Malformed:  " + e.getMessage());
		} catch (IOException e) {
			throw new Exception("IO Exception: " + e.getMessage());
		} catch (NullPointerException e) { 
			//OR stacktrace
			e.printStackTrace();
		}
	}

	/**
	 * Represents a helper to read a conversation from the given
	 * {@code inputFilePath}.
	 * 
	 * @param inputFilePath
	 *            The path to the input file.
	 * @return The {@link Conversation} representing by the input file.
	 * @throws Exception
	 *             Thrown when something bad happens.
	 */
	public Conversation readConversation(String inputFilePath) throws Exception {
		try (InputStream is = new FileInputStream(inputFilePath);
				BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

			List<Message> messages = new ArrayList<Message>();

			String conversationName = r.readLine();
			String line;

			while ((line = r.readLine()) != null) {
				String[] split = line.split(" ", 3);

				Message msg = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]);
				// System.out.println("Read Message form FILE : " + msg);
				messages.add(msg);
			}

			return new Conversation(conversationName, messages);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The file was not found. line 131 : " + e.getMessage());
		} catch (IOException e) {
			throw new Exception("Something went wrong line 133 : " + e.getMessage());
		}
	}

	class InstantSerializer implements JsonSerializer<Instant> {
		@Override
		public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(instant.getEpochSecond());
		}
	}
}
