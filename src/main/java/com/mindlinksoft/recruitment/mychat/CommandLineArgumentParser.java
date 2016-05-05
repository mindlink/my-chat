package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     * @throws IllegalArgumentException If {@code arguments} is invalid (extends {@link RuntimeException}).
     */
    public static ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws IllegalArgumentException {
    	int argumentCount = arguments.length;
    	
    	if(argumentCount < 2) {
    		throw new IllegalArgumentException(Const.INSUFFICIENT_ARGUMENTS);
    	}
    	
    	String inputFilePath = arguments[0];
    	String outputFilePath = arguments[1];
    	List<Filter> filters = new ArrayList<Filter>();
    	boolean addReport = false;
    	
    	for(int i=2; i<argumentCount ; ++i) {    		
    		switch(arguments[i]) {
    		case Const.USER_FILTER_KEY:
    			filters.add(new UserFilter(arguments[++i]));
    			break;
    		case Const.WORD_FILTER_KEY:
    			filters.add(new WordFilter(arguments[++i]));
    			break;
    		case Const.REDACT_FILTER_KEY:
    			filters.add(new RedactFilter(arguments[++i]));
    			break;
    		case Const.SECURE_FILTER_KEY:
    			filters.add(new RedactFilter(Const.CARD_REGEX));
    			filters.add(new RedactFilter(Const.PHONE_REGEX));
    			break;
    		case Const.REPORT_KEY:
    			addReport = true;
    			break;
    		case Const.ANONYMOUS_ID_KEY:
    			filters.add(new ObfuscateUserFilter());
    			break;
    		default:
    			throw new IllegalArgumentException(String.format(Const.BAD_ARGUMENTS, arguments[i]));
    		}
    	}
    	
        return new ConversationExporterConfiguration(inputFilePath, outputFilePath, filters, addReport);
    }
}
