package main.java.com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.com.mindlinksoft.recruitment.mychat.configuration.ConversationExporterConfiguration;
import main.java.com.mindlinksoft.recruitment.mychat.exceptions.InvalidArgumentListException;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {

	private static final Logger LOGGER = Logger.getLogger(CommandLineArgumentParser.class.getName() );
	
    public static final String FLAG_INDICATOR = "-";
    public static final String USER_STATS_FLAG_LONG = "--report-stats";
    public static final String USER_STATS_FLAG = "-r";
    public static final String HIDE_NUMBERS_FLAG_LONG = "--hide-sensitive";
    public static final String HIDE_NUMBERS_FLAG = "-h";
    public static final String OBFUSCATE_USERS_FLAG_LONG = "--obfuscate-users";
    public static final String OBFUSCATE_USERS_FLAG = "-f";
    public static final String BLACKLIST_FLAG_LONG = "--blacklist";
    public static final String BLACKLIST_FLAG = "-b";
    public static final String KEYWORD_FLAG_LONG = "--keyword";
    public static final String KEYWORD_FLAG = "-k";
    public static final String USER_FLAG_LONG = "--user";
    public static final String USER_FLAG = "-u";
    public static final String OUTPUT_FLAG_LONG = "--output";
    public static final String OUTPUT_FLAG = "-o";
    public static final String INPUT_FLAG_LONG = "--input";
    public static final String INPUT_FLAG = "-i";

	/**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
	 * @throws Exception 
     */
    public ConversationExporterConfiguration parseCommandLineArguments(
    		String[] arguments) throws Exception {
    	
    	try{
    		return tryParseCommandLineArguments(arguments);
		} catch (InvalidArgumentListException e) {
			String err = String.format(
					"Error when parsing arguments: %s. Please refer to the documentation.", e.getMessage());
			
			LOGGER.log(Level.SEVERE, err);
			throw e;
		} catch (Exception e) {
			String err = String.format(
					"Error when parsing arguments: %s. Please refer to the documentation.", e.getMessage());
			
			LOGGER.log(Level.SEVERE, err);
			throw e;
		}
    }
	
	private ConversationExporterConfiguration tryParseCommandLineArguments(
			String[] arguments) throws InvalidArgumentListException {
		
		boolean obfuscateUsers = false, hideSensitiveInfo = false, reportUserStats = false;
		List<String> blacklist = new ArrayList<String>();
		String input = null, output = null, user = null, keyword = null;
		
		if (arguments.length < 2) {
			throw new InvalidArgumentListException("Number of arguments must be at least 2");
		}
			
		int i = 0;
		String item = null, nextItem = null;
		while(i < arguments.length){
			item = arguments[i];
			
			switch(item){
				case INPUT_FLAG: case INPUT_FLAG_LONG:
					if (i < arguments.length - 1) nextItem = arguments[++i];
					if (nextItem == null || nextItem.startsWith(FLAG_INDICATOR)) 
						throw new InvalidArgumentListException("--input flag must be followed by a value");
					
					input = nextItem;
					break;
				case OUTPUT_FLAG: case OUTPUT_FLAG_LONG:
					if (i < arguments.length - 1) nextItem = arguments[++i];
					if (nextItem == null || nextItem.startsWith(FLAG_INDICATOR)) 
						throw new InvalidArgumentListException("--output flag must be followed by a value");
					
					output = nextItem;
					break;
				case USER_FLAG: case USER_FLAG_LONG:
					if (i < arguments.length - 1) nextItem = arguments[++i];
					if (nextItem == null || nextItem.startsWith(FLAG_INDICATOR)) 
						throw new InvalidArgumentListException("--user flag must be followed by a value");
					if (keyword != null) 
						throw new InvalidArgumentListException(
								"Messages can be filtered either by user id or by keyword, not both");
					
					user = nextItem;
					break;
				case KEYWORD_FLAG: case KEYWORD_FLAG_LONG:
					if (i < arguments.length - 1) nextItem = arguments[++i];
					if (nextItem == null || nextItem.startsWith(FLAG_INDICATOR)) 
						throw new InvalidArgumentListException("--keyword flag must be followed by a value");
					if (user != null) 
						throw new InvalidArgumentListException(
								"Messages can be filtered either by user id or by keyword, not both");
					
					keyword = nextItem;
					break;
				case BLACKLIST_FLAG: case BLACKLIST_FLAG_LONG:
					if (i < arguments.length - 1) nextItem = arguments[++i];
					if (nextItem == null || nextItem.startsWith(FLAG_INDICATOR)) 
						throw new InvalidArgumentListException(
								"--blacklist flag must be followed by at least one value");
					
					blacklist.add(nextItem);
					while (i < arguments.length - 1){
						String nextItemPeek = arguments[i+1];
						if (nextItemPeek.startsWith(FLAG_INDICATOR)){
							break;
						} else {
							nextItem = arguments[++i];
							blacklist.add(nextItem);
						}
					}
					break;
				case OBFUSCATE_USERS_FLAG: case OBFUSCATE_USERS_FLAG_LONG: 
					obfuscateUsers = true;
					break;
				case HIDE_NUMBERS_FLAG: case HIDE_NUMBERS_FLAG_LONG: 
					hideSensitiveInfo = true;
					break;
				case USER_STATS_FLAG: case USER_STATS_FLAG_LONG: 
					reportUserStats = true;
					break;
				default: 
					if (input == null) {
						input = item;
					} else if (output == null) {
						output = item;
					} else {
						throw new InvalidArgumentListException(
								"Input and output paths already provided, other arguments must be preceded by an appropriate flag");
					}
			}
			
			i++;
		}
		
		if (input == null || output == null)
			throw new InvalidArgumentListException("Please provide both an input path and an output path");
		
		ConversationExporterConfiguration config = new ConversationExporterConfiguration(input, output);
		config.setUserFilter(user);
		config.setKeywordFilter(keyword);
		config.setBlacklist(blacklist);
		config.setObfuscateUsers(obfuscateUsers);
		config.setHideSensitiveInfo(hideSensitiveInfo);
		config.setReportUserStats(reportUserStats);

        return config;
    }
}
