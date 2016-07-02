package com.mindlinksoft.recruitment.mychat;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Command line application entry point. Exposes a main method.
 * */
public class MainCLI {
	
	final static Logger LOGGER = Logger.getLogger("mychat");
	private static CLIConfiguration config;
	private static ConversationReader reader;
	private static ConversationWriter writer;
	
	/**
	 * The CLI application entry point.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		LoggerConfigurator.configureLogger();

		try {	
			
			config = CommandLineArgumentParser.parseCommandLineArguments(args);
			reader = new ConversationReader(new FileReader(config.getInputFilePath()));
			writer = new ConversationWriter(new FileWriter(config.getOutputFilePath()));
			
			Conversation conversation = reader.readConversation();
			

		} catch (IOException e) {
			LOGGER.log(Level.FINE, e.getStackTrace().toString());
			LOGGER.log(Level.SEVERE, "An error occurred while reading to or "
					+ "writing from file:" + e.getMessage());

		} catch(IllegalArgumentException e) {
			LOGGER.log(Level.INFO, "Usage: input_file_path output_file_path \n"
					+ "[-u userid]\n"
					+ "[-k content_keyword]\n"
					+ "[-b 'words to blacklist']\n");

			LOGGER.log(Level.FINE, e.getStackTrace().toString());

		} catch(InvalidConfigurationException e) {
			LOGGER.log(Level.SEVERE, "Exporter not set up properly:" +
					e.getMessage());
		} catch (MalformedOptionalCLIParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
