package com.mindlinksoft.recruitment.mychat;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main command line arguments parser.
 */
public final class CommandLineArgumentParser {
	
	private final static Logger LOGGER = Logger.getLogger("com.mindlinksoft.recruitment.mychat");
	
	
    /**
     * Parses the given {@code arguments} into the CLI application configuration.
     * @param args The command line arguments.
     * @return The exporter configuration representing the command line 
     * arguments.
     * @throws InvalidConfigurationException
     * @throws MalformedValueListException 
     */
    public static CLIConfiguration parseCommandLineArguments(String[] args) 
    		throws InvalidConfigurationException, 
    		MalformedOptionalCLIParameterException {
    	
    	if(null == args || args.length < 2)
    		throw new InvalidConfigurationException("Invalid arguments provided:"
    				+ " at least and input and output file parameters must be"
    				+ "provided from the command line.");
    	
    	//parse input and output file strings
    	CLIConfiguration config = new CLIConfiguration(args[0], args[1]);
    	config = CommandLineAdditionalArgumentsParser.parseAdditionalParameters(config, args);

    	return config;
    }
    
    

}
