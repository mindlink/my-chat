package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.features.BlacklistFeature;
import com.mindlinksoft.recruitment.mychat.features.ChatFeature;
import com.mindlinksoft.recruitment.mychat.features.HideNumbersFeature;
import com.mindlinksoft.recruitment.mychat.features.KeywordFilterFeature;
import com.mindlinksoft.recruitment.mychat.features.ObfuscateUserFeature;
import com.mindlinksoft.recruitment.mychat.features.UserFilterFeature;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	
	private boolean debug = true;
	
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws IllegalArgumentException
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
    			ChatFeature feature = getFeature(arg.charAt(1));
    			
    			//Ensure correct feature was identified
    			if(feature == null)
    			{
    				throw new IllegalArgumentException("parseCommandLineArguments: Invalid flag - '" + arg + "'");
    			}
    			
    			if(debug) System.out.println(feature.getClass().getName() + "being added");
    			
    			//Add argument if it is required
    			if(feature.argumentRequired())
    			{
    				//Length should be longer than 3 if argument is required
    				if(arg.length() <= 3)
    				{
    					throw new IllegalArgumentException("parseCommandLineArguments: No argument provided for flag - '" + arg + "'");
    				}
    				else if(arg.charAt(2) != '=')
    				{
    					//Argument to flag must be provided using a '='
    					throw new IllegalArgumentException("parseCommandLineArguments: Arguments not separated from flag using '=' - '" + arg + "'");
    				}
    				
    				feature.setArgument(arg.substring(3));
    			}
    			
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
    	
        return new ConversationExporterConfiguration(inputFilePath, outputFilePath, features);
    }
    
    /**
     * Function to determine which feature is being included
     * @param flag char representing command line flag to implement feature
     * @return {@link ChatFeature} to implement
     */
    private ChatFeature getFeature(char flag)
    {
    	switch(flag)
    	{
    		case 'u':
    			return new UserFilterFeature();
    		case 'k':
    			return new KeywordFilterFeature();
    		case 'b':
    			return new BlacklistFeature();
    		case 'o':
    			return new ObfuscateUserFeature();
    		case 'h':
    			return new HideNumbersFeature();
    	}
    	return null; //flag not recognised
    }
}
