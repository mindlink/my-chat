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

		// exporter.exportConversation(configuration.inputFilePath,
		// configuration.outputFilePath);
		exporter.exportConversation(configuration);
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

		System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);

	}

	/**
	 * 
	 * @param configuration
	 * @throws Exception
	 */
	public void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
		Conversation conversation = this.readConversation(configuration.inputFilePath);

		this.writeConversation(this.filterConversation(conversation, configuration), configuration.outputFilePath);

		System.out.println(
				"Conversation exported from '" + configuration.inputFilePath + "' to '" + configuration.outputFilePath);
		System.out.println("Conversation contained " + conversation.messages.size() + " messages");
		if (configuration.filterUser) {
			System.out.println("Conversation filtered for messages sent by user '" + configuration.username + "'");
		}
		if (configuration.filterKeyword) {
			System.out.println("Conversation filtered for messages containing keyword '" + configuration.keyword + "'");
		}
		if (configuration.filterBlacklist) {
			System.out.println("'" + configuration.blacklist + "' \\*redacted\\* from conversation");
		}
		if (configuration.filterNumbers) {
			System.out.println("Credit card and phone numbers \\*redacted\\* from conversation");
		}
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

		try (OutputStream os = new FileOutputStream(outputFilePath, true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
			try {
				GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

				Gson g = gsonBuilder.create();

				bw.write(g.toJson(conversation).toString());
			} finally {
				bw.close();
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The file '" + outputFilePath + "' was not found.");
		} catch (IOException e) {
			throw new IOException("Error occured writing conversation to file '" + outputFilePath + "'");
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
			try {
				List<Message> messages = new ArrayList<Message>();

				String conversationName = r.readLine();
				String line;

				while ((line = r.readLine()) != null) {
					String[] split = line.split(" ");

					for (int i = 3; i < split.length; i++) {
						split[2] = split[2] + " " + split[i];
					}

					messages.add(
							new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
				}

				return new Conversation(conversationName, messages);
			} finally {
				r.close();
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The file '" + inputFilePath + "' was not found.");
		} catch (IOException e) {
			throw new IOException("Error occured reading conversation from file '" + inputFilePath + "'");
		}
	}

	/**
	 * Filters a conversation
	 * @param conversation The conversation to be filtered
	 * @param configuration The configuration of the { @link ConversationExporter }
	 * @return The filtered { @link Conversation }
	 */
	public Conversation filterConversation(Conversation conversation, ConversationExporterConfiguration configuration) {

		/**
		 * Filters for messages sent by given user
		 */
		if (configuration.filterUser) {
			List<Message> filteredMessages = new ArrayList<Message>();
			for (Message message : conversation.messages) {
				if (message.senderId.equals(configuration.username)) {
					filteredMessages.add(message);
				}
			}
			conversation.messages = filteredMessages;
		}

		/**
		 * Filters for messages containing given keyword
		 */
		if (configuration.filterKeyword) {
			List<Message> filteredMessages = new ArrayList<Message>();
			for (Message message : conversation.messages) {				
				if (message.content.matches(".*\\b" + configuration.keyword + "\\b.*")) {
					filteredMessages.add(message);
				}				
			}
			conversation.messages = filteredMessages;
		}

		/**
		 * Replaces blacklisted words in message content with \*redacted\*
		 */
		if (configuration.filterBlacklist) {
			for (Message message : conversation.messages) {
				String[] split = message.content.split("\\b" + configuration.blacklist + "\\b");
				String m = split[0];
				for (int i = 1; i < split.length; i++) {
					m = m + "\\*redacted\\*" + split[i];
				}
				message.content = m;
			}
		}

		/**
		 * Replaces phone numbers and credit card numbers with \*redacted\*
		 */
		if (configuration.filterNumbers) {
			for (Message message : conversation.messages) {
				String regex = "\\b(([0-9]{11})|([0-9]{16}))\\b";
				String[] split = message.content.split(regex);
				String m = split[0];
				for (int i = 1; i < split.length; i++) {
					m = m + "\\*redacted\\*" + split[i];
				}
				message.content = m;
			}
		}

		return conversation;
	}

	class InstantSerializer implements JsonSerializer<Instant> {
		@Override
		public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(instant.getEpochSecond());
		}
	}
}
