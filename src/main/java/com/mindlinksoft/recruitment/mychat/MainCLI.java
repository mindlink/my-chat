package com.mindlinksoft.recruitment.mychat;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Command line application entry point. Exposes a main method.
 * */
public class MainCLI {
	
	final static Logger LOGGER = Logger.getLogger("com.mindlinksoft.recruitment.mychat");
	private static final String USAGE = "Usage: <input_file_path> <output_file_path> \n"
			+ "[-u -username <the_username>	]\n"
			+ "[-k -keyword <the_keyword>	]\n"
			+ "[-b -blacklist '<token1> <token2> <token3> ...'	]\n"
			+ "[-o -obfuscate	]\n"
			+ "[-r -report	]\n";
	private static CLIConfiguration config;
	private static ConversationReader reader;
	private static ConversationWriter writer;
	
	
	/**
	 * The CLI application entry point.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		//configure logger
		LoggerConfigurator.configureLogger();

		//main body:
		try {	
			
			config = CommandLineArgumentParser.parseCommandLineArguments(args);
			reader = new ConversationReader(new FileReader(config.getInputFilePath()));
			writer = new ConversationWriter(new FileWriter(config.getOutputFilePath()));
			
			LOGGER.log(Level.INFO, "Reading input from '" + config.getInputFilePath() + "'");
			Conversation conversation = reader.readConversation();
			reader.close();
			
			LOGGER.log(Level.INFO, "Applying filters ...");
			ConversationFilterApplier.applyFilters(config.getFilters(), conversation);
			
			LOGGER.log(Level.INFO, "Writing output to '" + config.getOutputFilePath() + "'");
			writer.writeConversation(conversation);
			writer.close();
			
			LOGGER.log(Level.INFO, "Export successful.");
		} catch (IOException e) {
			LOGGER.log(Level.FINE, e.getStackTrace().toString());
			LOGGER.log(Level.SEVERE, "An error occurred while reading from or "
					+ "writing to file:" + e.getMessage());
			
		} catch(InvalidConfigurationException e) {
			LOGGER.log(Level.SEVERE, "Exporter not set up properly: " +
					e.getMessage());
			LOGGER.log(Level.INFO, USAGE);
			
		} catch (MalformedOptionalCLIParameterException e) {
			LOGGER.log(Level.SEVERE, "Unrecognized CLI parameter: " +
					e.getMessage());
			LOGGER.log(Level.INFO, USAGE);
		}
	}
	
}
