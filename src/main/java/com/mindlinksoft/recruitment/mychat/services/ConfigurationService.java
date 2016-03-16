package com.mindlinksoft.recruitment.mychat.services;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mindlinksoft.recruitment.mychat.models.ConfigurationOptions;
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
    	options.addOption(ConfigurationOptions.HELP.getValue(), "print this message");
    	options.addOption(ConfigurationOptions.INPUT.getValue(), true, "input file path");
    	options.addOption(ConfigurationOptions.OUTPUT.getValue(), true, "output file path");
    	options.addOption(ConfigurationOptions.USER.getValue(), true, "only export messages from this user");
    	options.addOption(ConfigurationOptions.KEYWORD.getValue(), true, "only export messages with this keyword");
    	options.addOption(ConfigurationOptions.BLACKLIST.getValue(), true, "a comma separated list of words or phrases to obfuscate.");
    	
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
            
            // Make sure there is an input and output path.
        	if (!line.hasOption(ConfigurationOptions.INPUT.getValue()) || !line.hasOption(ConfigurationOptions.OUTPUT.getValue())) {
        		return null;
        	}
        	
        	// Split the blacklist if there is one defined.
        	List<String> blacklist = null;
        	String blacklistString = line.getOptionValue(ConfigurationOptions.BLACKLIST.getValue());
        	
        	if (blacklistString != null) {
        		blacklist = Arrays.asList(blacklistString.split("\\s*,\\s*"));
        	}

        	// The 'line' will return null if a value doesn't exist.
        	return new ConversationExporterConfiguration(
        			line.getOptionValue(ConfigurationOptions.INPUT.getValue()),
        			line.getOptionValue(ConfigurationOptions.OUTPUT.getValue()),
        			line.getOptionValue(ConfigurationOptions.USER.getValue()),
        			line.getOptionValue(ConfigurationOptions.KEYWORD.getValue()),
        			blacklist);
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
