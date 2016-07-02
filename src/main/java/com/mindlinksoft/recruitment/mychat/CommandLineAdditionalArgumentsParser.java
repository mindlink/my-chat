package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses the command line parameters provided in addition to the essential ones.
 * Creates new filters or throws exceptions when user input is unacceptable.*/
class CommandLineAdditionalArgumentsParser {
	
	/**represents the delimitator for many valued parameters: many valued 
	 * parameters must be input as a list starting and ending with the string
	 * here specified.*/
	private final static String MANY_VALUED_DELIMITATOR = "'";
	
	private static String[] args;
	private static int index;
	
	/**
	 * @return a modified configuration where filters have been added according
	 * to instructions specified in the String[] parameter.
	 * @param config the basic configuration object already existing
	 * @param arguments the CLI arguments array*/
	static CLIConfiguration parseAdditionalParameters(
    		CLIConfiguration config, String[] arguments) 
    				throws UnrecognizedCLIOptionException, 
    				TooFewParamtersException, MalformedValueListException {
		
		//set up fields for visibility across methods in this class:
    	args = arguments;
    	index = 2;
    	
    	//loop through arguments provided:
    	while(index<args.length) {
    		//select action for each string under parsing:
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
    		index++; //move to next string in array
    	}   		

    	return config;
    }
    
	/**
	 * @return a conversation filter that only takes a single option parameter.
	 * Which concrete filter class is constructed depends on the option being
	 * parsed, and the action of {@link ConversationFilterFactory} given that
	 * option. Options are defined inside the {@link Options} class.
	 * 
	 * @param option the string representing the recognized single valued additional
	 * parameter option provided in the input string array*/
    private static ConversationFilter parseSingleValueOption(String option)
    		throws UnrecognizedCLIOptionException, TooFewParamtersException {
    	
    	//guard
    	if(index + 1 < args.length)
			return ConversationFilterFactory.createFilter(option, args[++index]);
    	else
    		throw new TooFewParamtersException();
    }
    	
    /**
     * @return a conversation filter that takes a list of parameters.
	 * Which concrete filter class is constructed depends on the option being
	 * parsed, and the action of {@link ConversationFilterFactory} given that
	 * option. Options are defined inside the {@link Options} class.
	 * 
	 * @param option the string representing the recognized single valued additional
	 * parameter option provided in the input string array*/
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
