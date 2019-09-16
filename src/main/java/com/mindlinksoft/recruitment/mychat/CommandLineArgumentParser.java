package com.mindlinksoft.recruitment.mychat;

import java.io.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public static ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
    	
    	//Throw exception if an input and output file are not given
    	if (arguments.length < 2) {
    		throw new IllegalArgumentException("ConversationExporter must be run as: java ConversationExporter 'input.txt' 'output.json' 'optional arguements...'");
    	}
    	
    	//Throw and exception if the input file argument is not of type ".txt"
    	String input = arguments[0];
    	if (!fileTypeIsValid(input, ".txt")) {
    		throw new IllegalArgumentException("First command-line argument must be an input file of type .txt");
    	}
    	
    	//Throw an exception if the output file argument is not of type ".json"
    	String output = arguments[1];
    	if (!fileTypeIsValid(output, ".json")) {
    		throw new IllegalArgumentException("Second command-line argument must be an output file of type .json");
    	}
    	
    	//return configuration
    	if (arguments.length > 2) {
    		String[] optionalArgumentsArray = Arrays.copyOfRange(arguments, 2, arguments.length);
    		
    		return new ConversationExporterConfiguration(input, output, optionalArgumentsArray);
    	} else {
    		return new ConversationExporterConfiguration(input, output);
    	}
    }
 
    public static boolean fileTypeIsValid(String fileName, String fileType) {
    	if (fileName.length() <= fileType.length()) { return false; } 
		
    	String currFileType = fileName.substring(fileName.length() - fileType.length());

    	if (!currFileType.equals(fileType)) { return false; }
		
    	return true;
    }
}
