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
    	ConversationExporterConfiguration config = null;
    	
    	//at least two arguments required:
    	try{
    		//reference is initialized with input/output paths:
    		config = new ConversationExporterConfiguration(arguments[0],
    														arguments[1]);    		
    	}
    	catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
    		throw new IllegalArgumentException("Too few arguments provided");
    	} 
    	
    	//if any other input has been provided:
//    	if(arguments.length > 2)
    	for(int i = 2; i<arguments.length;) {
    		
    		if(looksLikeOption(arguments[i]) && hasNext(i,arguments.length)) {
    			//put "-option" as "-o" -> "nextArg" in config
    			config.put(arguments[i].substring(0, 2), arguments[i+1]);
    			i += 2;//consumed option value
    		} 
    		else 
    			++i;//consume only one argument

    	}   			

    	
    	return config;
    }
    
    /**
     * Decides whether the argument option is syntactically recognized
     * */
    private static boolean looksLikeOption(String argument) {
    	
    	return argument.startsWith("-");
    }
    
    /**
     * Checks to see if the option is followed by another argument, which will
     * be consumed as the option's value
     * */
    private static boolean hasNext(int index, int length) {
    	return index + 1 < length;
    }
}
