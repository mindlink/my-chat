package com.mindlinksoft.recruitment.mychat;

import java.nio.file.Paths;
import java.util.List;
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

    	ConversationExporterConfiguration config = null;
//		config = new ConversationExporterConfiguration(arguments[0], arguments[1]);

    		for(int i = 2; i<arguments.length;) {
    			if(looksLikeOption(arguments[i])) {
    				
    				char optionInitial = arguments[i].charAt(1);
    				
    				
    				if(Options.needsManyValues(optionInitial) && 
    						hasNext(i, arguments.length) &&
    						arguments[i+1].startsWith("'")) {
    					//TODO make procedure?
    					String[] values = parseValueList(arguments, i, "'", "\\s+");
//    					if(Options.isFilter(optionInitial))
    						config.addFilter(ConversationFilterFactory.createFilter(optionInitial, values));
//    					else
//    						config.setMultivaluedOption(arguments[i], values);

//    					LOGGER.log(Level.FINE, "Parsing list optional parameter.");
//    					//move pointer to next and begin constructing list
//    					++i;
//    					String listed = arguments[i].substring(1);
//
//    					//keep fetching next argument until EITHER end of CLI 
//    					//input OR an arg is found that ends by single quote char
//    					while(i<arguments.length) {
//    						++i;
//    						if(arguments[i].endsWith("'")) {
//    							//cut trailing single quote
//    							listed += " " + arguments[i].substring(0, 
//    									arguments[i].length() - 1);
//    							break;
//    						}
//    						//    					else
//    						listed += " " + arguments[i];
//    					}
//    					config.put(optionInitial, listed);

    				} else if (Options.needsSingleValue(optionInitial) && 
    						hasNext(i, arguments.length)) {
    					
    					String value = arguments[i+1];
//    					if(Options.isFilter(optionInitial))
    						config.addFilter(ConversationFilterFactory.createFilter(optionInitial, value));
//    					else
//    						config.setOption(arguments[i], arguments[i+1]);
    					
//    					LOGGER.log(Level.FINE, 
//    							"Parsing valued optional parameter.");
//    					config.put(arguments[i].charAt(1), arguments[i+1]);
//    					i += 2;//consumed option value

    				} else if (Options.needsNoValue(optionInitial)) {
    					LOGGER.log(Level.FINE, "Parsing flag optional parameter.");
    					config.setFlag(arguments[i]);

    				}

    			} else 
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
    
//    private static boolean needsMany(char value) {
//    	return false;
//    	
//    }
//    
//    private static boolean needsOne(char value) {
//    	return false;
//    	
//    }
//    
//    private static boolean needsNone(char value) {
//    	return false;
//    	
//    }
    
    private static String[] parseValueList(String[] arguments, 
    										int index, 
    										String delimitatorRegex, 
    										String separatorRegex) {
    	return null;
    	
    }
}
