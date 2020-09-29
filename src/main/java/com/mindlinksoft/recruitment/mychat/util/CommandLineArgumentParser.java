package com.mindlinksoft.recruitment.mychat.util;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	private ConversationExporterConfiguration conversationExporterConfiguration;

	/**
	 * Parses the given {@code arguments} into the exporter configuration.
	 * 
	 * @param arguments The command line arguments.
	 * @return The exporter configuration representing the command line arguments.
	 */
	public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
		this.conversationExporterConfiguration = new ConversationExporterConfiguration(arguments[0], arguments[1]);
		parseOptionalArguments(arguments);
		return this.conversationExporterConfiguration;
	}

	/**
	 * Parses the optional {@code arguments} used into the exporter configuration.
	 * 
	 * @param arguments The command line arguments.
	 */
	private void parseOptionalArguments(String[] arguments) {
		final String userFilterOption = "-u";
		final String keywordFilterOption = "-k";
		final String blackListOption = "-b";
		final String hideDetailsOption = "-h";
		final String obfuscateUsersOption = "-o";
		final String userActivityOption = "-a";
		int numOfArguments = arguments.length;

		for (int argumentNum = 2; argumentNum < numOfArguments; argumentNum++) {
			String argument = arguments[argumentNum];
			switch (argument) {
			case userFilterOption:
				if (argumentNum < numOfArguments - 1) {
					String user = arguments[argumentNum + 1];
					conversationExporterConfiguration.setUserFilter(user);
				}
				break;
			case keywordFilterOption:
				if (argumentNum < numOfArguments - 1) {
					String keyword = arguments[argumentNum + 1];
					conversationExporterConfiguration.setKeywordFilter(keyword);
				}
				break;
			case blackListOption:
				if (argumentNum < numOfArguments - 1) {
					String blacklist = arguments[argumentNum + 1];
					conversationExporterConfiguration.setBlacklist(blacklist);
				}
				break;
			case hideDetailsOption:
				conversationExporterConfiguration.setHidePersonalDeatils(true);
				break;
			case obfuscateUsersOption:
				conversationExporterConfiguration.setObfuscateUsers(true);
				break;
			case userActivityOption:
				conversationExporterConfiguration.setUserActivity(true);
				break;
			}
		}
	}
}
