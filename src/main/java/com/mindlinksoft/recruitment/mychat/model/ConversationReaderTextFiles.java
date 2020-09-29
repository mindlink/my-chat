package com.mindlinksoft.recruitment.mychat.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mindlinksoft.recruitment.mychat.filters.MessageFilterClient;

public class ConversationReaderTextFiles implements ConversationReader {

	private static final Logger LOGGER = Logger.getLogger(ConversationReaderTextFiles.class.getName());

	/**
	 * Represents a helper to read a conversation from the given
	 * {@code inputFilePath}.
	 * 
	 * @param inputFilePath The path to the input file.
	 * @return The {@link Conversation} representing by the input file.
	 * @throws Exception Thrown when reading from the input file fails.
	 */
	@Override
	public Conversation readConversation(ConversationExporterConfiguration configuration) throws Exception {
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
			MessageFilterClient messageFilterClient = new MessageFilterClient();
			List<Message> filteredMessages = messageFilterClient.applyFilters(messages, configuration);
			return new Conversation(conversationName, filteredMessages);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "The input text file was not found.", e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "The program was unable to read the conversation data from the input text file.",
					e);
		}
		return null;
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
