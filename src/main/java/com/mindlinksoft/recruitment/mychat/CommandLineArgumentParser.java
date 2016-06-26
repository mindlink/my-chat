package com.mindlinksoft.recruitment.mychat;

import java.nio.file.Paths;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public static ConversationExporterConfiguration 
    							parseCommandLineArguments(String[] arguments) {
    	//reference to return:
    	ConversationExporterConfiguration result = null;
    	
    	//at least two arguments required:
    	try{
    		//reference is initialized with input/output paths:
    		result = new ConversationExporterConfiguration(arguments[0],
    														arguments[1]);    		
    	}
    	catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
    		throw new IllegalArgumentException("Too few arguments provided");
    	} 
    	
    	//if any other input has been provided:
    	if(arguments.length > 2){
    		for(int i = 2; i<arguments.length; i++) {
//    			if(arguments[i].startsWith("-"))
//    				parseOption(arguments[i].charAt(1));
    				
    		}
    			
    	}
    	
    	return result;
    }
    
    /**
     * Decides whether the argument option is syntactically recognized
     * */
    public static boolean parseOption(String argument) {
    	return false;
    }
}
