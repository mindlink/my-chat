package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

class CommandLineAdditionalArgumentsParser {
	
	private final static String MANY_VALUED_DELIMITATOR = "'";
	private final static String BLANKS = "\\s+";
	
	private static String[] args;
	private static int index;
	
	static CLIConfiguration parseAdditionalParameters(
    		CLIConfiguration config, String[] arguments) 
    				throws UnrecognizedCLIOptionException, 
    				TooFewParamtersException, MalformedValueListException {
		
    	args = arguments;
    	index = 2;
    	
    	while(index<args.length) {
    		switch(args[index]) {
    		case Options.FILTER_USERNAME:
    		case Options.FILTER_USERNAME_ABBREVIATED:
    			config.addFilter(
    					parseSingleValueOption(Options.FILTER_USERNAME));

    			break;
    		case Options.FILTER_KEYWORD:
    		case Options.FILTER_KEYWORD_ABBREVIATED:
    			config.addFilter(
    					parseSingleValueOption(Options.FILTER_KEYWORD));

    			break;
    		case Options.FILTER_BLACKLIST:
    		case Options.FILTER_BLACKLIST_ABBREVIATED:
    			config.addFilter(parseMultiValueOption(Options.FILTER_BLACKLIST));
    			
    			break;
    		default:
    			throw new UnrecognizedCLIOptionException("Option '" + 
    					args[index] + "' not recognized.");
    				
    		}
    		index++;
    	}   		

    	return config;
    }
    
    private static ConversationFilter parseSingleValueOption(String option)
    		throws UnrecognizedCLIOptionException, TooFewParamtersException {
    	
    	//guard
    	if(index + 1 < args.length)
			return ConversationFilterFactory.createFilter(option, args[++index]);
    	else
    		throw new TooFewParamtersException();
    }
    	
    
    private static ConversationFilter parseMultiValueOption(String option)
    				throws UnrecognizedCLIOptionException, 
    				MalformedValueListException {
    	
    	//create temporary list
    	List<String> manyValued = new ArrayList<String>(25);
    	
    	//guard
		if(index + 1 < args.length &&
			args[++index].startsWith(MANY_VALUED_DELIMITATOR)) {
			
			manyValued.add(args[index].substring(1));
		} else
			throw new MalformedValueListException();
		
		//main loop
		while(index + 1 < args.length) {
			++index;
			
			//end condition
			if(args[index].endsWith(MANY_VALUED_DELIMITATOR)) {
				manyValued.add(args[index].substring(0, 
						args[index].length() - 1));
				break;
			}
			
			else
				manyValued.add(args[index]);	
		}
		
		return ConversationFilterFactory.createFilter(
				Options.FILTER_BLACKLIST, manyValued.toArray(new String[0]));
    	
    }
}
