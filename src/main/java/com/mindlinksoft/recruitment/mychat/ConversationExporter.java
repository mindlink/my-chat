package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

	/**Private gson instance used to serialize Java types into JSON
	 * appropriately*/
	private static Gson gson = null;
	private static Logger LOGGER = Logger.getLogger("com.mindlinksoft.recruitment.mychat");
	private ConversationExporterConfiguration config;
	
	/**
	 * Default Constructor*/
	public ConversationExporter() {
		init();
		this.config = new ConversationExporterConfiguration();
		LOGGER.log(Level.WARNING, "Exporter instance created, but no config was "
				+ "provided");
	}
	
	/**
	 * Constructor taking a configuration object.
	 * */
	public ConversationExporter(ConversationExporterConfiguration config) {
		init();
		this.config = config;
		LOGGER.log(Level.FINE, "Exporter instance created with config");
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
			LOGGER.log(Level.FINE, "Created new Gson instance to serialize Java "
					+ "objects data into JSON.");
		}
		
		LOGGER.log(Level.FINER, "Gson instance returned.");
	}

	/**
	 * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @throws Exception Thrown when something bad happens.
	 */
	public void exportConversation(String inputFilePath, String outputFilePath) 
			throws IOException, IllegalArgumentException {
		
		Conversation conversation = this.readConversation(inputFilePath);
		this.applyFilters(conversation);
		this.writeConversation(conversation, outputFilePath);

		LOGGER.log(Level.INFO, "Conversation exported from '" + inputFilePath + 
				"' to '" + outputFilePath + "'");
	}
	
	/**
	 * Attempts to export a conversation based on the file paths stored in the
	 * instance configuration. If none can be found, throws a configuration
	 * exception.*/
	public void exportConversation() throws IOException, 
											InvalidConfigurationException, 
											IllegalArgumentException{
		if(config.get('i') == null || config.get('o') == null)
			throw new InvalidConfigurationException("Export not configured, "
					+ "try specifying the filepaths to export to and from "
					+ "as parameters instead.");
		LOGGER.log(Level.FINE, "Exporting with filepaths in exporter config");
		exportConversation(config.get('i'), config.get('o'));
	}
	
	/**
	 * Applies selected modifier to conversation model. The parameter 
	 * conversation model is modified as a result.
	 * @param conversation The conversation passed in as a parameter
	 * */
	public void applyFilters(Conversation conversation) {
		LOGGER.log(Level.INFO, "Applying filters specified in exporter config "
				+ "...");
		Filterer filterer = new Filterer(conversation);
		
		for(char option : Filterer.set) {
			
			String value = config.get(option);
			if(value != null)
				filterer.apply(option, value);

		}
	}

	/**
	 * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
	 * @param conversation The conversation to write.
	 * @param outputFilePath The file path where the conversation should be written.
	 * @throws IOException 
	 */
	public void writeConversation(Conversation conversation, 
			String outputFilePath) throws IOException, IllegalArgumentException {
		LOGGER.log(Level.FINE, "Writing data contents into '" + 
				outputFilePath + "'...");
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
	public Conversation readConversation(String inputFilePath) 
			throws IOException, IllegalArgumentException {
		LOGGER.log(Level.FINE, "Reading data contents from '" + 
				inputFilePath + "'...");
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
	 * Instant Java class
	 * */
	static private class InstantSerializer implements JsonSerializer<Instant> {
		@Override
		public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(instant.getEpochSecond());
		}
	}
}
