package main.java.com.mindlinksoft.recruitment.mychat;

import main.java.com.mindlinksoft.recruitment.mychat.configuration.ConversationExporterConfiguration;
import main.java.com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import main.java.com.mindlinksoft.recruitment.mychat.conversation.serializers.ConversationSerializer;
import main.java.com.mindlinksoft.recruitment.mychat.conversation.serializers.JsonConversationSerializer;
import main.java.com.mindlinksoft.recruitment.mychat.exceptions.EmptyFileException;
import main.java.com.mindlinksoft.recruitment.mychat.exceptions.FileAlreadyExistsExc;
import main.java.com.mindlinksoft.recruitment.mychat.message.Message;
import main.java.com.mindlinksoft.recruitment.mychat.message.filters.IMessageFilter;
import main.java.com.mindlinksoft.recruitment.mychat.message.processors.IDataCollector;
import main.java.com.mindlinksoft.recruitment.mychat.message.processors.IMessageProcessor;
import main.java.com.mindlinksoft.recruitment.mychat.message.processors.ProcessorsCollection;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.com.mindlinksoft.recruitment.mychat.writters.FileWriter;
import main.java.com.mindlinksoft.recruitment.mychat.writters.Writer;

/**
 * Represents a conversation exporter that can read a conversation and write it
 * out in JSON.
 */
public class ConversationExporter {
	private static final Logger LOGGER = Logger.getLogger(ConversationExporter.class.getName());
	
	private final String inputFilePath;
	private final String outputFilePath;
	private final IMessageFilter filter;
	private final ProcessorsCollection processors;

	private final ConversationSerializer serializer;
	private final Writer writter;

	/**
	 * The application entry point.
	 * 
	 * @param args The command line arguments.
	 * @throws Exception
	 * 
	 */
	public static void main(String[] args) throws Exception {
		
		ConversationExporterConfiguration configuration = 
				new CommandLineArgumentParser().parseCommandLineArguments(args);

		ConversationExporter exporter;
		try {
			exporter = new ConversationExporter(configuration);
		} catch (FileAlreadyExistsExc e) {
			LOGGER.log(Level.SEVERE, "Cannot proceed with export");
			return;
		}

		exporter.export();
	}

	public ConversationExporter(ConversationExporterConfiguration config) 
			throws FileAlreadyExistsExc {
		
		this.inputFilePath = config.getInputFilePath();
		this.outputFilePath = config.getOutputFilePath();
		this.filter = config.getMessageFilter();
		this.processors = config.getEnabledProcessors();

		// consider IoC for the below
		this.serializer = new JsonConversationSerializer();
		try{
			this.writter = new FileWriter(this.outputFilePath);
		} catch (FileAlreadyExistsExc e) {
			String err = String.format(
					"Error when initializing the program: %s.", e.getMessage());
			
			LOGGER.log(Level.SEVERE, err);
			throw e;
		}
	}

	public void export() {
		try {
			Conversation conversation = this.loadConversation();
			this.writeConversation(conversation);
			
		} catch (Exception e) {
			String err = String.format(
					"Error when exporting the conversation: %s.", e.getMessage());
			
			LOGGER.log(Level.SEVERE, err);
			return;
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
	private void writeConversation(Conversation conversation) {
		String fileContent;
		try {
			fileContent = this.serializer.serialize(conversation);
		} catch (Exception e){
			LOGGER.log(Level.SEVERE, e.getMessage());
			return;
		}
		
		boolean success = this.writter.write(fileContent);
		if (success) {
			LOGGER.log(Level.FINE, "Conversation successfully exported");
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
	private Conversation loadConversation() 
			throws FileNotFoundException, IOException, EmptyFileException, Exception {

		try (InputStream is = new FileInputStream(this.inputFilePath);
				BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

			List<Message> messages = new ArrayList<Message>();

			String line;
			if ((line = r.readLine()) == null)
				throw new EmptyFileException("Input file is empty");
			
			String conversationName = line;
			
			while ((line = r.readLine()) != null) {
				Pattern pattern = Pattern.compile("([0-9]{10}) ([^\\s]+) (.*)");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					Instant parsedInstant = Instant.ofEpochSecond(Long
							.parseUnsignedLong(matcher.group(1)));
					
					Message message = new Message(parsedInstant,
							matcher.group(2), matcher.group(3));
					
					if (this.filter == null || this.filter.validate(message)) {
						for (IMessageProcessor proc : 
							this.processors.getAllProcessors()) {
							
							proc.process(message);
						}
						messages.add(message);
					}
				}
			}

			Map<String, Map<String, Integer>> extraData = 
					new LinkedHashMap<String, Map<String, Integer>>();
			
			for (IDataCollector<Map<String, Integer>> collector : this.processors
					.getStringToIntCollectors()) {
				extraData.put(collector.getTitle(), collector.extractData());
			}

			Conversation conversation = extraData.isEmpty() ? 
					new Conversation(conversationName, messages) : 
						new Conversation(conversationName, messages, extraData);

			return conversation;
			
		} catch (FileNotFoundException e) {
			String err = String.format(
					"Could not find conversation file to load. Requested path: %s", inputFilePath);
			
			throw new FileNotFoundException(err);
		} catch (IOException e) {
			String err = String.format(
					"Failed to read from file: %s", inputFilePath);
			
			throw new IOException(err);
		} catch (Exception e) {
			String err = String.format(
					"Failed to load the conversation from file: %s", inputFilePath);
			
			throw new Exception(err);
		}
	}

}
