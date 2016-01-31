package com.mindlinksoft.recruitment.mychat;

import javax.naming.directory.InvalidAttributesException;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	/**
	 * Parses the given {@code arguments} into the exporter configuration.
	 * 
	 * @param arguments
	 *            The command line arguments.
	 * @return The exporter configuration representing the command line
	 *         arguments.
	 */
	public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws Exception {

		if (arguments.length < 2) {
			throw new InvalidAttributesException("Input and output filenames required");
		}
		ConversationExporterConfiguration configuration = new ConversationExporterConfiguration(arguments[0],
				arguments[1]);

		// If > 2 arguments
		// Then do other stuff
		// -u flag indicates a username is to be filtered for
		// -k flag indicates a keyword is to be filtered for
		// -b flag indicates a word is to be blacklisted

		for (int i = 2; i < arguments.length; i ++) {
			switch (arguments[i]) {
			case "-u":
				if (arguments.length > i + 1) {
					configuration.filterUser = true;
					configuration.username = arguments[++i];
				} else {
					System.out.println("-u no username given");
				}
				break;
			case "-k":
				if (arguments.length > i + 1) {
					configuration.filterKeyword = true;
					configuration.keyword = arguments[++i];
				} else {
					System.out.println("-k no keyword given");
				}
				break;
			case "-b":				
				if (arguments.length > i + 1) {
					configuration.filterBlacklist = true;
					configuration.blacklist = arguments[++i];
				} else {
					System.out.println("-b no blacklist word given");
				}
				break;
			case "-n":
				configuration.filterNumbers = true;
				break;
			}
		}

		return configuration;
	}
}
