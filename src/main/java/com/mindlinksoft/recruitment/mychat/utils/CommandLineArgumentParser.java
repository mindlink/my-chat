package com.mindlinksoft.recruitment.mychat.utils;

import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.features.ChatFeature;
import com.mindlinksoft.recruitment.mychat.features.UserActivityFeature;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	
	private static final boolean debug = false;
	
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public static ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws IllegalArgumentException
    {
    	String inputFilePath = null;
    	String outputFilePath = null;
    	ArrayList<ChatFeature> features = new ArrayList<ChatFeature>();
    	
    	if(debug) System.out.println("Parsing arguments...");
    	//Iterate through all arguments
    	for(String arg : arguments)
    	{
    		if(arg.charAt(0) == '-')
    		{
    			//Parse feature and add it to list of features
    			ChatFeature feature = FeatureParser.parse(arg);
    			if(debug) System.out.println(feature.getClass().getName() + "being added");
    			features.add(feature);
    		}
    		else
    		{
    			//Assume any argument without '-' is file paths
    			//Strict ordering: 1. Input file, 2. Output file
    			if(inputFilePath == null)
    			{
    				inputFilePath = arg;
    				if(debug) System.out.println("Input file path: " + inputFilePath);
    			}
    			else if(outputFilePath == null)
    			{
    				outputFilePath = arg;
    				if(debug) System.out.println("Output file path: " + outputFilePath);
    			}
    			else
    			{
    				//Incorrect extra argument
    				throw new IllegalArgumentException("parseCommandLineArguments: Invalid argument - '" + arg + "'");
    			}
    		}
    	}
    	
    	//Throw exception if input/output file paths not provided
    	if(inputFilePath == null)
    	{
    		throw new IllegalArgumentException("parseCommandLineArguments: No valid input file provided");
    	}
    	else if(outputFilePath == null)
    	{
    		throw new IllegalArgumentException("parseCommandLineArguments: No valid output file provided");
    	}
    	
    	features.add(new UserActivityFeature()); // Keep track of user activity
    	
        return new ConversationExporterConfiguration(inputFilePath, outputFilePath, features);
    }
}
