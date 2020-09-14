package com.mindlinksoft.recruitment.mychat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
    	
    	String func[] = null;
    	
    	/* functions have under 10 letters, argument *hopefully* has under 58 letters 
    	   (limit set to welsh train station with longest placename in europe) */
    	String funcWithArgRegex = "[A-z]{1,10}: [A-z]{1,58}";
    	
        /**
         * Gets the argument for the predicate used by the functions with args
         * (the username/keyword to filter by or the word to redact)
         */
        String userArg = null;
        String kwArg = null;
        String redactArg = null;
        
        /**
         * boolean flags for the bonus functions
         */
    	boolean user = false;
    	boolean keyword = false;
    	boolean redact = false;
    	boolean redactPhoneCard = false;
    	boolean obfuscate = false;
    	boolean activity = false;
    	
    	// only process function arguments if they exist to be processed
    	if (arguments.length > 2) {
    		
    		// iterate over every argument beyond index 1
    		for (int i = 2; i < arguments.length; i++) {
    	    	Matcher argMatcher = Pattern.compile(funcWithArgRegex).matcher(arguments[i]);

    	    	// functions with arguments, matching the regex
    	    	if (argMatcher.matches()) {
    	    		func = arguments[i].split(": ");
    	    		
    	    		if (func[0].equalsIgnoreCase("User")) {
    	    			user = true;
    	    			userArg = func[1];
    	    		}
    	    		else if (func[0].equalsIgnoreCase("Keyword")) {
    	    			keyword = true;
    	    			kwArg = func[1];
    	    		} 
    	    		else if (func[0].equalsIgnoreCase("Blacklist")) {
    	    			redact = true;
    	    			redactArg = func[1];
    	    		}  
    	    		// non-existing or improperly entered functions
    	    		else {
    	    			System.out.println("Error: Command must be: \"User\", \"Keyword\", or \"Redact\"");
    	    		}
    	    	}
    	    	// functions without arguments
    	    	else {
    	    		char[] noArgFlags = arguments[i].toCharArray();
    	    		// 1,2,3 index as index 0 is the leading f for flags
    	    		if (noArgFlags[1] == '1') {
    	    			redactPhoneCard = true;
    	    		}
    	    		if (noArgFlags[2] == '1') {
    	    			obfuscate = true;
    	    		}
    	    		if (noArgFlags[3] == '1') {
    	    			activity = true;
    	    		}
    	    	}
    		}
    	}

        return new ConversationExporterConfiguration(arguments[0], arguments[1], 
        		user, userArg, keyword, kwArg, redact, redactArg, 
        		redactPhoneCard, obfuscate, activity);
    }
}
