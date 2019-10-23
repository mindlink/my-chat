package com.mindlinksoft.recruitment.mychat;

import org.apache.commons.cli.*;

/**
 * Represents a helper to parse command line arguments and options
 * @author emab
 *
 */
public final class CommandLineArgumentParser {
	
	private final CommandLineParser parser = new DefaultParser();
	
	/**
	 * This method generates the configuration that will be used by the exporter to filter the conversation
	 * @param args The command line arguments
	 * @return ConversationExporterConfiguration the configurations to be used by the exporter
	 */
	public ConversationExporterConfiguration generateExportOptions(String[] args) {
		try {
			/**
			 * Create the command line instance with the options from the {@link CommandLineArgumentParserOptions} class
			 */
			CommandLine line = parser.parse(CommandLineArgumentParserOptions.getInstance().getOptions(), args);
			
			/**
			 * We check to see if there are any options
			 */
			if (line.getOptions().length > 0) {	
				ConversationFilter filter = new ConversationFilter(
						line.getOptionValue("u"),
						line.getOptionValue("k"),
						line.getOptionValues("b"));
				
				/**
				 * And if there are, we apply a filter
				 */
	    		return new ConversationExporterConfiguration(line.getArgs()[0], line.getArgs()[1], filter); 
				}
			else {
				
				/**
				 * And if there is not, we just export the chat
				 */
	    		return new ConversationExporterConfiguration(line.getArgs()[0], line.getArgs()[1]); 
			}

		/**
		 * Handle any errors regarding arguments
		 */
		} catch (ArrayIndexOutOfBoundsException e) {
    		throw new IllegalArgumentException("Invalid number of arguments.", e);
    	} catch (ParseException | IllegalArgumentException e) {
			throw new IllegalArgumentException("Failed to parse arguments.", e);
		}
	}
}
