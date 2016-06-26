package com.mindlinksoft.recruitment.mychat;

import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	
	private final static Logger LOGGER = Logger.getLogger("com.mindlinksoft.recruitment.mychat");
	
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line 
     * arguments.
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
    		IllegalArgumentException ex =  new IllegalArgumentException(
    											"Too few arguments provided");
    		LOGGER.log(Level.SEVERE, "Error: failed to parse CLI command", ex);
    		throw ex;
    	} 
    	
    	//if any other input has been provided:
//    	if(arguments.length > 2)
    	for(int i = 2; i<arguments.length;) {
    		
//    		if(looksLikeOption(arguments[i]) && hasNext(i,arguments.length)) {
//    			//put "-option" as "-o" -> "nextArg" in config
//    			config.put(arguments[i].charAt(1), arguments[i+1]);
//    			i += 2;//consumed option value
//    		} 
//    		else 
//    			++i;//consume only one argument
    		
    		if(looksLikeOption(arguments[i])) {
    			char optionInitial = arguments[i].charAt(1);
    			if(Filterer.mayBeList(optionInitial) && 
    					hasNext(i, arguments.length) &&
    					arguments[i+1].startsWith("'")) {
    				LOGGER.log(Level.FINE, "Parsing list optional parameter.");
    				//move pointer to next and begin constructing list
    				++i;
    				String listed = arguments[i].substring(1);
    				
    				//keep fetching next argument until EITHER end of CLI 
    				//input OR an arg is found that ends by single quote char
    				while(i<arguments.length) {
    					++i;
    					if(arguments[i].endsWith("'")) {
    						//cut trailing single quote
    						listed += " " + arguments[i].substring(0, 
    								arguments[i].length() - 1);
    						break;
    					}
//    					else
    					listed += " " + arguments[i];
    				}
    				config.put(optionInitial, listed);
    				
    			} else if (Filterer.needsValue(optionInitial) && 
    						hasNext(i, arguments.length)) {
    				LOGGER.log(Level.FINE, 
    						"Parsing valued optional parameter.");
    				config.put(arguments[i].charAt(1), arguments[i+1]);
        			i += 2;//consumed option value
        			
    			} else if (Filterer.isFlag(optionInitial)) {
    				LOGGER.log(Level.FINE, "Parsing flag optional parameter.");
    				config.put(arguments[i].charAt(1), "");
    			
    			}
    			
    		}
    		++i; //if it does not look like an option, simply ignore it
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
