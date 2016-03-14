package com.mindlinksoft.recruitment.mychat.services;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mindlinksoft.recruitment.mychat.models.ConversationExporterConfiguration;

/**
 * Service to understand the configuration the user supplied.
 */
public final class ConfigurationService {
	
	private final Options options;
	
	/**
	 * Initializes a new instance of the {@link ConfgurationService} class.
	 */
	public ConfigurationService() {
		// Create the different configuration options  	
    	this.options = new Options();
    	options.addOption("help", "print this message");
    	options.addOption("i", true, "input file path");
    	options.addOption("o", true, "output file path");
    	options.addOption("u", true, "export only this user's messages");
	}
	
    /**
     * Parses the given {@code configuration} into the exporter configuration.
     * 
     * @param configuration The configuration options.
     * @return An exporter configuration object representing the configuration supplied.
     * @throws IllegalArgumentException When there is a problem parsing the {@code configuration}.
     */
    public ConversationExporterConfiguration parseConfiguration(String[] configuration) throws IllegalArgumentException {

    	CommandLineParser parser = new DefaultParser();
    	try {
            // Parse the configuration to retrieve the options values
            CommandLine line = parser.parse(options, configuration);
            
            // Fail fast if the input or output are not specified.
        	if (!line.hasOption("i") || !line.hasOption("o")) {
        		return null;
        	}

        	// Create the export configuration object
        	return new ConversationExporterConfiguration(
        			line.getOptionValue("i"),
        			line.getOptionValue("o"),
        			line.getOptionValue("u"));
        }
        catch( ParseException e) {
        	throw new IllegalArgumentException("There was a problem parsing the configuration supplied", e);
        }
    }
    
    /**
     * Print help information informing the user how to use the {@link ConversationExporter}.
     */
    public void printHelp() {
    	HelpFormatter formatter = new HelpFormatter();
    	formatter.printHelp("Configuration Exporter", options);
    }
}
