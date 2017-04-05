package com.mindlinksoft.recruitment.mychat;

import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

import com.mindlinksoft.recruitment.mychat.conversation.ConversationFormatterFactory;
import com.mindlinksoft.recruitment.mychat.conversation.IConversationFormatter;
import com.mindlinksoft.recruitment.mychat.conversation.exporter.ConversationExporterConfiguration;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    
	private final CommandLineParser parser = new DefaultParser();  
	
	/**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
    	try {
			//Parse command line arguments.
			CommandLine line = parser.parse(CommandLineOptions.getInstance().getOptions(), arguments);
			
			//Load properties file.
			Properties properties = new Properties();
			if (line.hasOption(CommandLineOptions.PROPERTIES_FILE_PATH)) {
				properties = PropertiesUtil.loadProperties(line.getOptionValue(CommandLineOptions.PROPERTIES_FILE_PATH));
			}
			
			// Create the conversation formatter
			IConversationFormatter formatter = ConversationFormatterFactory.createConversationFormatter(line.getOptions(), properties);
			
    		return new ConversationExporterConfiguration(line.getArgs()[0], line.getArgs()[1], formatter);
    	} catch (ArrayIndexOutOfBoundsException e) {
    		throw new IllegalArgumentException("Invalid number of arguments.", e);
    	} catch (ParseException | IllegalArgumentException e) {
			throw new IllegalArgumentException("Failed to parse arguments.", e);
		}
    }
    
}
