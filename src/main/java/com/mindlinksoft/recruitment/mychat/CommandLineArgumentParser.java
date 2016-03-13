package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws InvalidArgumentsException {
    	try {
	    	String inputFile = arguments[0];
	    	String outputFile = arguments[1];
	    	String username = null;
	    	String keyword = null;
	    	String blacklist = null;
	    	boolean hideCC = false;
	    	boolean hideId = false;
	    	boolean includeReport = false;
	    	
	    	for (int i = 2; i < arguments.length; i++) {
	    		String[] splitedTask = arguments[i].split(AppConstant.PARAMETER_SEPARATOR);
	    		if (splitedTask[0].equals(AppConstant.USERNAME)) {
	    			username = splitedTask[1];
	    			continue;
	    		}
	    		if (splitedTask[0].equals(AppConstant.KEYWORD)) {
	    			keyword = splitedTask[1];
	    			continue;
	    		}
	    		if (splitedTask[0].equals(AppConstant.BLACKLIST)) {
	    			blacklist = splitedTask[1];
	    			continue;
	    		}
	    		if (splitedTask[0].equals(AppConstant.HIDE_CC)) {
	    			hideCC = true;
	    			continue;
	    		}
	    		if (splitedTask[0].equals(AppConstant.HIDE_ID)) {
	    			hideId = true;
	    			continue;
	    		}
	    		if (splitedTask[0].equals(AppConstant.INCLUDE_REPORT)) {
	    			includeReport = true;
	    			continue;
	    		}
	    	}
	    	return new ConversationExporterConfiguration(inputFile, outputFile, username, keyword,
	    			blacklist, hideCC, hideId, includeReport);
	    	
    	} catch (ArrayIndexOutOfBoundsException e) {
    		throw new InvalidArgumentsException(AppConstant.EXCEPTION_INDEX_OUT_OF_RANGE);
    	}
    }     
}
