package com.mindlinksoft.recruitment.mychat;

import java.nio.file.Paths;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	
	/**
	 * Enum defining the possible valid optional arguments
	 * */
	private static enum OPTIONS {
		U,
		K,
		B
	}
	
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public static ConversationExporterConfiguration 
    							parseCommandLineArguments(String[] arguments) {
    	
    	ConversationExporterConfiguration result = null;
    	
    	if(arguments.length <= 2) {
    		
    		result = new ConversationExporterConfiguration(arguments[0],
    														arguments[1]);
    	} else {
    		for(int i = 2; i<arguments.length; i++) {
//    			if(arguments[i].startsWith("-"))
    				
    				
    		}
    			
    	}
    	
    	return result;
    }
    
    /**
     * Decides whether the option is syntactically recognized
     * */
    public static int parseOption(char option) {
    	return 0;
    }
}
