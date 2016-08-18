package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws Exception {
    	
    	if (arguments.length < 2 || arguments.length > 100)
    	{
    		throw new ArgumentsNumberException();
    	}
    	ArrayList<String> blackList = new ArrayList<String>();
    	blackList.add(""); 
    	if (arguments.length == 4)
    		return new ConversationExporterConfiguration(arguments[0], arguments[1], arguments[2], arguments[3], blackList);
    	if (arguments.length == 3) 
    		return new ConversationExporterConfiguration(arguments[0], arguments[1], arguments[2], "",blackList);
    	if  (arguments.length == 2)
    		return new ConversationExporterConfiguration(arguments[0], arguments[1], "", "",blackList);
    	else
    		blackList.remove(0);
    		for (int i = 4; i < arguments.length; i ++)
    			blackList.add(arguments[i]);
    		return new ConversationExporterConfiguration(arguments[0], arguments[1], arguments[2], arguments[3], blackList);
    }
}
