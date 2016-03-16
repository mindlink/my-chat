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
 	
    	this.options = new Options();
    	options.addOption("help", "print this message");
    	options.addOption("i", true, "input file path");
    	options.addOption("o", true, "output file path");
    	options.addOption("u", true, "only export messages from this user");
    	options.addOption("k", true, "only export messages with this keyword");
    	
	}
	
    /**
     * Parses the given {@code configuration} into an {@link ConversationExporterConfiguration} object.
     * 
     * @param configuration The configuration options.
     * @return An exporter configuration object representing the configuration supplied.
     * @throws IllegalArgumentException When there is a problem parsing the {@code configuration}.
     */
    public ConversationExporterConfiguration parseConfiguration(String[] configuration) throws IllegalArgumentException {

    	try {
    		CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse(options, configuration);
            
            // If the input or output are not specified then cannot create valid configuration.
        	if (!line.hasOption("i") || !line.hasOption("o")) {
        		return null;
        	}

        	return new ConversationExporterConfiguration(
        			line.getOptionValue("i"),
        			line.getOptionValue("o"),
        			line.getOptionValue("u"),
        			line.getOptionValue("k"));
        }
        catch(ParseException e) {
        	throw new IllegalArgumentException("There was a problem parsing the configuration supplied", e);
        }
    }
    
    /**
     * Print help information informing the user how to configure the {@link ConversationExporter}.
     */
    public void printHelp() {
    	HelpFormatter formatter = new HelpFormatter();
    	formatter.printHelp("Configuration Exporter", options);
    }
}
