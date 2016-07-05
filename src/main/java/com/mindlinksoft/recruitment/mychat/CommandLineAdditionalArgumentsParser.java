package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses the command line parameters provided in addition to the essential ones.
 * Responsible for attempting to parse additional options.
 * Throws exceptions when user input is unacceptable.<p>
 * Because this class is responsible for the parsing, the creation of filters 
 * (and therefore explicit dependencies on concrete filter classes) is deferred
 * to {@link ConversationFilterFactory}*/
class CommandLineAdditionalArgumentsParser {
	
	private final static Logger LOGGER = Logger.getLogger("com.mindlinksoft.recruitment.mychat");
	
	/**represents the delimitator for many valued parameters: many valued 
	 * parameters must be input as a list starting and ending with the string
	 * here specified.*/
	private final static String MANY_VALUED_DELIMITATOR = ":";
	private final static int EXPECTED_NO_OF_MANY_VALUES = 25;
	
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
    		
    		LOGGER.log(Level.INFO, "Attemping to parse additional parameter: " + args[index]);
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
    		case Options.FLAG_REPORT:
    		case Options.FLAG_REPORT_ABBREVIATED:
    			config.addFilter(parseNoValueOption(Options.FLAG_REPORT));
    			
    			break;
    		case Options.FLAG_OBFUSCATE_NAMES:
    		case Options.FLAG_OBFUSCATE_NAMES_ABBREVIATED:
    			config.addFilter(parseNoValueOption(Options.FLAG_OBFUSCATE_NAMES));
    			
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
	 * parameter option provided in the input string array
	 * @throws UnrecognizedCLIOptionException
	 * @throws TooFewParametersException*/
    private static ConversationFilter parseSingleValueOption(String option)
    		throws TooFewParamtersException, UnrecognizedCLIOptionException {
    	
    	//guard
    	if(index + 1 < args.length)
			return ConversationFilterFactory.createFilter(option, args[++index]);
    	else
    		throw new TooFewParamtersException("Single value option '" + option 
    				+ "' must be followed by exactly one value.");
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
    	List<String> manyValued = new ArrayList<String>(EXPECTED_NO_OF_MANY_VALUES);
    	
    	parseManyGuard(manyValued);
		
		//main loop
		while(index + 1 < args.length) {
			++index;
			
			//end condition
			if(parseManyEnd(manyValued))
				break;
			else
				manyValued.add(args[index]);	
		}
		
		return ConversationFilterFactory.createFilter(
				option, manyValued.toArray(new String[0]));
    	
    }
    
    /**
     * Guards many valued parser method logic from attempting to parse an
     * argument that does not begin with the MANY_VALUED_DELIMITATOR string
     * @param manyValued the list of string to which parsed values are being 
     * added*/
    private static void parseManyGuard(List<String> manyValued) 
    		throws MalformedValueListException {
    	
    	//guard
    	if(index + 1 < args.length &&
    			args[++index].startsWith(MANY_VALUED_DELIMITATOR)) {
    		
			if(args[index].compareTo(MANY_VALUED_DELIMITATOR) != 0)
				manyValued.add(args[index].substring(MANY_VALUED_DELIMITATOR.length()));
			//else ignore input
    	} else 
    		throw new MalformedValueListException("Value list not properly "
    				+ "formatted.\n"
    				+ "Lists of values must be opened and closed by \" " 
    				+ MANY_VALUED_DELIMITATOR + " \"");
    }
    
    /**
     * Checks for the exit condition from a list of many values.
     * @return true if the end of the list has been reached. Will also take care
     * to parse the last value in this case and add it to the parameter List
     * @param manyValued the list of string to which parsed values are being 
     * added
     * */
    private static boolean parseManyEnd(List<String> manyValued) {
    	if(args[index].endsWith(MANY_VALUED_DELIMITATOR)) {
			
    		//cut off trailing \' character
    		if(args[index].compareTo(MANY_VALUED_DELIMITATOR) != 0)
    			manyValued.add(args[index].substring(0, 
    					args[index].length() - MANY_VALUED_DELIMITATOR.length()));
    		//else ignore
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * Parses flags that need no option value.
     * @param option the option to parse.*/
    private static ConversationFilter parseNoValueOption(String option) 
    		throws UnrecognizedCLIOptionException {
    	return ConversationFilterFactory.createFilter(option);
    }
}
