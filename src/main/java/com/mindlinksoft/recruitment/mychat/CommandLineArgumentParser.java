package com.mindlinksoft.recruitment.mychat;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	
	private final static Logger LOGGER = Logger.getLogger("com.mindlinksoft.recruitment.mychat");
	private final static String MANY_VALUED_DELIMITATOR = "'";
	private final static String BLANKS = "\\s+";
	
	
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param args The command line arguments.
     * @return The exporter configuration representing the command line 
     * arguments.
     * @throws InvalidConfigurationException 
     * @throws UnrecognizedCLIOptionException 
     * @throws TooFewParamtersException 
     * @throws MalformedValueListException 
     */
    public static CLIConfiguration parseCommandLineArguments(String[] args) 
    		throws InvalidConfigurationException, 
    		MalformedOptionalCLIParameterException {
    	
    	if(args.length < 2)
    		throw new InvalidConfigurationException();
    	
    	//parse input and output file strings
    	CLIConfiguration config = new CLIConfiguration(args[0], args[1]);

    	config = parseAdditionalParameters(config, args);

    	return config;
    }
    
    private static CLIConfiguration parseAdditionalParameters(
    		CLIConfiguration config, String[] args) 
    				throws UnrecognizedCLIOptionException, 
    				TooFewParamtersException, MalformedValueListException {
    	
    	for(int index = 2; index<args.length;) {
    		switch(args[index]) {
    		case Options.FILTER_USERNAME:
    		case Options.FILTER_USERNAME_ABBREVIATED:
    			config.addFilter(
    					parseSingleValueOption(Options.FILTER_USERNAME,
    											args, ++index));

    			break;
    		case Options.FILTER_KEYWORD:
    		case Options.FILTER_KEYWORD_ABBREVIATED:
    			config.addFilter(
    					parseSingleValueOption(Options.FILTER_KEYWORD,
    											args, ++index));

    			break;
    		case Options.FILTER_BLACKLIST:
    		case Options.FILTER_BLACKLIST_ABBREVIATED:
    			config.addFilter(parseMultiValueOption(Options.FILTER_BLACKLIST,
    													args, ++index));
    			
    			break;
    		default:
    			throw new UnrecognizedCLIOptionException("Option '" + 
    					args[index] + "' not recognized.");
    				
    		}
    		index++;
    	}   		

    	return config;
    }
    
    private static ConversationFilter parseSingleValueOption(
    		String option, String[] args, int index)
    		throws UnrecognizedCLIOptionException, TooFewParamtersException {
    	if(index < args.length)
			return ConversationFilterFactory.createFilter(option, args[index]);
    	else
    		throw new TooFewParamtersException();
    }
    	
    
    private static ConversationFilter parseMultiValueOption(
    		String option, String[] args, Integer index) 
    				throws UnrecognizedCLIOptionException, 
    				MalformedValueListException {
    	
    	//create temporary list
    	List<String> manyValued = new ArrayList<String>(25);
    	
    	//guard
		if(index < args.length &&
			args[index].startsWith(MANY_VALUED_DELIMITATOR)) {
			
			manyValued.add(args[index].substring(1));
		} else
			throw new MalformedValueListException();//throw exception
		
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
//    private static CLIConfiguration parseOption(CLIConfiguration config, 
//    		String[] args, char optionInitial, int index) 
//    				throws UnrecognizedCLIOptionException {
//    	
//    	//many valued option
//		if(Options.needsManyValues(optionInitial) && 
//				hasNext(index, args)) {
//
//			String[] values = parseValueList(args, index, 
//					MANY_VALUED_DELIMITATOR, BLANKS);
//			config.addFilter(ConversationFilterFactory.createFilter(
//					optionInitial, values));
//			
//		//single valued option
//		} else if (Options.needsSingleValue(optionInitial) && 
//				hasNext(index, args)) {
//
//			String value = args[index+1];
//			config.addFilter(ConversationFilterFactory.createFilter(
//					optionInitial, value));
//
//		//no value option
//		} else if (Options.needsNoValue(optionInitial)) {
//
//			config.setFlag(args[index]);
//
//		}
//    	
//    	return config;
//    }
//
//    /**
//     * Decides whether the argument option is syntactically recognized
//     * */
//    private static boolean looksLikeOption(String argument) {
//    	return argument.startsWith("-");
//    }
//    
//    /**
//     * Checks to see if the option is followed by another argument, which will
//     * be consumed as the option's value
//     * */
//    private static boolean hasNext(int index, String[] args) {
//    	return index + 1 < args.length;
//    	
//    }
//    
//    private static String[] parseValueList(String[] arguments, 
//    									int index, 
//    									String delimitatorRegex, 
//    									String separatorRegex)
//    									throws UnrecognizedCLIOptionException {
//    	
//    	if(!arguments[index].startsWith(MANY_VALUED_DELIMITATOR)) 
//    		throw new UnrecognizedCLIOptionException("The option parameter list "
//    				+ "does not start with a (" + MANY_VALUED_DELIMITATOR + ") "
//    						+ "character: " + arguments[index]);
//    	
//    	//move pointer to next and begin constructing list
//		++index;
//		String listed = arguments[index].substring(1);
//
//		//keep fetching next argument until EITHER end of CLI 
//		//input OR an arg is found that ends by single quote char
//		while(index<arguments.length) {
//			++index;
//			if(arguments[index].endsWith(MANY_VALUED_DELIMITATOR)) {
//				
//				//cut trailing single quote
//				listed += " " + arguments[index].substring(0, 
//												arguments[index].length() - 1);
//				break;
//			}
////    		else
//			listed += " " + arguments[index];
//		}
//
//    	return listed.split(" ");
//    	
//    }
}
