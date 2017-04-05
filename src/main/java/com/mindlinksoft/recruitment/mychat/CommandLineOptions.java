package com.mindlinksoft.recruitment.mychat;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;


/**
 * Singleton that holds definitions of command line options.
 *
 */
public class CommandLineOptions {

	public static final String USER_FILTER = "u";
	public static final String KEYWORD_FILTER = "k";
	public static final String BLACKLIST_WORDS = "b";
	public static final String BLACKLIST_NUMBERS = "n";
	public static final String USE_ALIASES = "a";
	public static final String PROPERTIES_FILE_PATH = "p";
	
	private static CommandLineOptions instance = null;
	
	private final Options options;
	
	private CommandLineOptions() {
		options = new Options();
		
    	options.addOption(USER_FILTER, true, "filter messages by user");
    	options.addOption(KEYWORD_FILTER, true, "filter message by keyword");
    	options.addOption(BLACKLIST_NUMBERS, false, "redact credit card and phone numbers");
    	options.addOption(PROPERTIES_FILE_PATH, true, "properties file path");
    	options.addOption(USE_ALIASES, false, "obfuscate userIDs using the aliases defined in the properties.");
    	options.addOption(Option.builder(BLACKLIST_WORDS)
    			.desc("blacklisted keywords")
    			.hasArgs()
    			.valueSeparator(',')
    			.build());
	}
	
	protected static CommandLineOptions getInstance() {
		if (instance == null) {
			instance = new CommandLineOptions();
		}
		return instance;
	}
	
	/**
	 * Gets all available command line {@link Options}
	 * @return Command line {@link Options}
	 */
	public Options getOptions() {
		return options;
	}
}
