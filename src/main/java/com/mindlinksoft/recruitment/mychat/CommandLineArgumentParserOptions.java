package com.mindlinksoft.recruitment.mychat;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Represents the options used by the command line parser
 * @author emab
 *
 */
public class CommandLineArgumentParserOptions {
	public static final String USER_FILTER = "u";
	public static final String KEYWORD_FILTER = "k";
	public static final String BLACKLIST_WORDS = "b";

	private static CommandLineArgumentParserOptions instance = null;

	private final Options options;

	/**
	 * Building the optional arguments for the command line
	 */
	private CommandLineArgumentParserOptions() {
		options = new Options();

		/**
		 * Option to filter messages by username
		 */
    	options.addOption(Option.builder(USER_FILTER)
	        .numberOfArgs(1)
	        .desc("Show messages by this user")
	        .build());
    	/**
    	 * Option to filter messages by a keyword
    	 */
    	options.addOption(Option.builder(KEYWORD_FILTER)
    			.desc("Show messages containing this keyword")
    			.numberOfArgs(1)
    			.build());
    	/**
    	 * Option to hide certain words
    	 */
    	options.addOption(Option.builder(BLACKLIST_WORDS)
    			.desc("Hide these words")
    			.hasArgs()
    			.valueSeparator(',')
    			.build());
	}

	/**
	 * 
	 * @return instance
	 */
	protected static CommandLineArgumentParserOptions getInstance() {
		if (instance == null) {
			instance = new CommandLineArgumentParserOptions();
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
