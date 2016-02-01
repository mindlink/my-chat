package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	//possible parameters being passed
	@Parameter
	private List<String> parameters = new ArrayList<>();
	
	@Parameter(names = "-input", description = "Path to input file", required = true)
	private String input;
	
	@Parameter(names = "-output", description = "Path to output file", required = true)
	private String output;
	
	@Parameter(names = "-senderId", description = "Only writes messages sent by this sender ID to JSON file")
	private String senderId;
	
	@Parameter(names = "-keyword", description = "Only writes messages containing this keyword to JSON file")
	private String keyword;
	
	@Parameter(names = "-blacklist", description = "Redacts blacklisted words from JSON file", variableArity = true)
	private List<String> blacklist = new ArrayList<>();
	
	
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
    	
    	//create JCommander object and parse parameters
    	CommandLineArgumentParser cmp = new CommandLineArgumentParser();
    	new JCommander(cmp, arguments);

    		return new ConversationExporterConfiguration(cmp.input, cmp.output, cmp.senderId, cmp.keyword, cmp.blacklist);


    }
}
